/**

 *
 */

package com.cdoframework.cdolib.servicebus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import com.cdo.business.client.IClient;
import com.cdo.business.rpc.zk.ZKClientManager;
import com.cdo.business.rpc.zk.ZkParameter;
import com.cdo.business.rpc.zk.ZookeeperServer;
import com.cdo.util.exception.ZookeeperException;
import com.cdoframework.cdolib.base.CycleList;
import com.cdoframework.cdolib.base.Return;
import com.cdoframework.cdolib.base.Utility;
import com.cdoframework.cdolib.data.cdo.CDO;
import com.cdoframework.cdolib.database.BigTableEngine;
import com.cdoframework.cdolib.database.IDataEngine;
import com.cdoframework.cdolib.framework.ClusterController;
import com.cdoframework.cdolib.servicebus.xsd.DataGroup;
import com.cdoframework.cdolib.servicebus.xsd.Parameter;
import com.cdoframework.cdolib.servicebus.xsd.ZkConsumer;
import com.cdoframework.cdolib.servicebus.xsd.ZkProducer;

/**
 * 修改
 * @author KenelLiu
 */
public class ServiceBus implements IServiceBus
{

	//静态对象,所有static在此声明并初始化------------------------------------------------------------------------
	private Logger logger = Logger.getLogger(ServiceBus.class);
	//内部对象,所有在本类中创建并使用的对象在此声明--------------------------------------------------------------
	private HashMap<String,IDataEngine> hmDataGroup;
	private ServicePlugin[] plugins;
	private ClusterController clusterController;
	private BigTableEngine btEngine=new BigTableEngine();
	private HashMap<String,Object> hmSharedData;
	private ReentrantReadWriteLock lockSharedData;		
	private HashMap<String,IService> hmService;
	//用于创建客户端
	private ZKClientManager zkClientManager;
	//zkId,ZkParameter<serviceName>, 一个zkId,对应的一组serviceName 服务
	private Map<String, List<ZkParameter>> zkServiceMap=new HashMap<String, List<ZkParameter>>();
	
	

	private HashMap<String,String> hmParameterMap;
	public String getParameterValue(String strParameterName)
	{
		return hmParameterMap.get(strParameterName);
	}
	private EventProcessor eventProcessor;
	public IEventHandler getEventHandler()
	{
		return this.eventProcessor;
	}
	public BigTableEngine getBigTableEngine()
	{
		return this.btEngine;
	}
	//引用对象,所有在外部创建并传入使用的对象在此声明并提供set方法-----------------------------------------------
	private String strDefaultDataGroupId;

	public void setGroupId(String strGroupId)
	{
		this.strDefaultDataGroupId	= strGroupId;
	}
	public String getDefaultDataGroupId()
	{
		return this.strDefaultDataGroupId;
	}
	
	public HashMap<String,IService> getHmService(){
		
		return this.hmService;
	} 
	
	
	// 公共方法,所有可提供外部使用的函数在此定义为public方法------------------------------------------------------
	
	/**
	 * 	
	 * 根据strServiceBusXML 初始化 ServiceBus对象,	
	 * @param strServiceBusXML  
	 * @return
	 */
	public Return init(String strServiceBusXML){
		//将XML转换成对象
		com.cdoframework.cdolib.servicebus.xsd.ServiceBus serviceBus=null;
		try
		{
			serviceBus=com.cdoframework.cdolib.servicebus.xsd.ServiceBus.fromXML(strServiceBusXML);
		}
		catch(Exception e)
		{
			logger.error("When parse serviceBus.xml , caught exception: ",e);
			return Return.valueOf(-1,"Init ServiceBus Failed: "+e.getLocalizedMessage());
		}
		// 初始化插件参数
		int nParameterCount=serviceBus.getParameterCount();
		for(int i=0;i<nParameterCount;i++)
		{
			Parameter para=serviceBus.getParameter(i);
			this.hmParameterMap.put(para.getName(),para.getValue());
		}
		//加载和初始化全局DataGroup
		this.hmDataGroup=new HashMap<String,IDataEngine>(6);
		DataGroup[] dgs=serviceBus.getDataGroup();
		try
		{
			for(int i=0;i<dgs.length;i++){				
				this.hmDataGroup.put(dgs[i].getId(),dgs[i].init());				
			}
		}catch(Exception e){
			this.hmDataGroup.clear();
			this.hmDataGroup=null;
			logger.error("When parse DataGroup , caught exception: ",e);
			return Return.valueOf(-1,"Init ServiceBus Failed: "+e.getLocalizedMessage());
		}						

		//初始化ClusterController
		com.cdoframework.cdolib.servicebus.xsd.ClusterController ccDefine=serviceBus.getClusterController();
		if(ccDefine!=null)
		{
			if(logger.isInfoEnabled()){logger.info("Staring initialize  cluster controller ....................");}
			clusterController	=new ClusterController();
			IDataEngine clDataEngine=hmDataGroup.get(ccDefine.getDataGroupId());
			if(clDataEngine==null)
			{
				return Return.valueOf(-1,"Invalid DataGroupId: "+ccDefine.getDataGroupId());
			}
			
			clusterController.setDataGroup(clDataEngine);
			clusterController.setMaxDeadSecond(ccDefine.getMaxDeadSecond());
			clusterController.setPulseSecond(ccDefine.getPulseSecond());
			if(logger.isInfoEnabled()){logger.info("initialize  cluster controller successfully....................");}
		}
		//初始化事件处理器
		if(logger.isInfoEnabled()){logger.info("starting to init Event handler....................");}
		com.cdoframework.cdolib.servicebus.xsd.EventProcessor eventProcessorDefine = serviceBus.getEventProcessor();
		if(eventProcessorDefine != null)
		{
			eventProcessor			= new EventProcessor();
			eventProcessor.setMaxFreeThreadCount(eventProcessorDefine.getMaxIdelTreadCount());
			eventProcessor.setMaxThreadCount(eventProcessorDefine.getMaxThreadCount());
			eventProcessor.setMinThreadCount(eventProcessorDefine.getMaxIdelTreadCount());
			eventProcessor.setMaxWaitEventCount(eventProcessorDefine.getMaxWaitEventCount());
		}		
		
		//加载插件对象
		if(logger.isInfoEnabled()){logger.info("Staring load  plugins ....................");}
		int nPluginCount=serviceBus.getPluginXMLResourceCount();
		this.plugins=new ServicePlugin[nPluginCount];
		try
		{
			for(int i=0;i<nPluginCount;i++)
			{
				String strXMLResource=serviceBus.getPluginXMLResource(i);
				if(logger.isInfoEnabled()){logger.info("loading "+strXMLResource+"..............................");}
				String strXML=Utility.readTextResource(strXMLResource,"utf-8");
				if(strXML==null)
				{
					throw new Exception("Resource "+strXMLResource+" invalid");
				}
				com.cdoframework.cdolib.servicebus.xsd.ServicePlugin servicePluginDefine=com.cdoframework.cdolib.servicebus.xsd.ServicePlugin.fromXML(strXML);
				this.plugins[i]=new ServicePlugin();

				//初始化插件
				this.plugins[i].setServiceBus(this);				
				this.plugins[i].setPublicDataGroup(hmDataGroup);				
				this.plugins[i].init(i+"",this,servicePluginDefine);
			}
		}
		catch(Exception e)
		{
			plugins	=null;

			logger.error("when parse plugin ,caught Exception: ",e);
			return Return.valueOf(-1,"Init ServiceBus Failed: "+e.getLocalizedMessage());
		}
		if(logger.isDebugEnabled()){logger.debug("load  plugins successfully....................");}
		
		
		//将plugin.xml上服务  根据配置注册到对应的zk服务器上  
		ZkProducer[] zkProducer=serviceBus.getZkProducer();
		initServiceOnZk(zkProducer);
		//创建zk客户端,本机调用其他机器上的服务
		ZkConsumer[] zkConsumers=serviceBus.getZkConsumer();
		initZKClient(zkConsumers);
		
		return Return.OK;
	}
	 	

	public void destroy(){
		
		for(Iterator<Map.Entry<String, IService>> it=hmService.entrySet().iterator();it.hasNext();){
			it.next().getValue().destroy();
		}
	}
	
	/**
	 * 启动 Plugin服务
	 * @return
	 */
	public Return start()
	{
		Return ret=null;

		for(Iterator<Map.Entry<String, IService>> it=hmService.entrySet().iterator();it.hasNext();){
			it.next().getValue().start();
		}
		
		//Start ClusterController
		if(clusterController!=null)
		{
			ret=clusterController.start();
			if(ret.getCode()!=0)
			{
				stop();
			}
		}
		
		if(this.eventProcessor!=null)
		{
			ret = eventProcessor.start();
			if(ret.getCode()!=0)
			{
				logger.fatal(ret.getText());
			}
		}
		return Return.OK;
	}

	/**
	 * 停止Business服务
	 *
	 */
	public void stop()
	{
		//Stop ClusterController
		if(clusterController!=null)
		{
			clusterController.stop();
		}		
		//Stop Plugin
		for(Iterator<Map.Entry<String, IService>> it=hmService.entrySet().iterator();it.hasNext();){
			it.next().getValue().stop();
		}		
		if(eventProcessor != null ) {
			eventProcessor.stop();
		}
	}

	/**
	 * 调用事务处理的方法
	 * ServiceBus会根据Request内的数据和ServiceBus.xml的配置内容，自动查找到对应的插件，调用其对象提供的处理方法
	 * @param cdoRequest 事务请求对象
	 * @param cdoResponse 事务应答对象
	 * @return 事务处理结果
	 */
	public Return handleTrans(CDO cdoRequest,CDO cdoResponse)
	{

		String strServiceName=cdoRequest.exists(ITransService.SERVICENAME_KEY)?cdoRequest.getStringValue(ITransService.SERVICENAME_KEY):null;
		String strTransName=cdoRequest.exists(ITransService.TRANSNAME_KEY)?cdoRequest.getStringValue(ITransService.TRANSNAME_KEY):null;	
		if(strServiceName==null||strTransName==null ||
				strServiceName.length()==0|| strTransName.length()==0){
			logger.error("[strServiceName,strTransName] can not be empty,strServiceName="+strServiceName+",strTransName="+strTransName);
			return Return.valueOf(-1, "[strServiceName,strTransName] can not be empty", "[strServiceName,strTransName] can not be empty");
		}
		//-------开始事务---------------//
		onTransStarted(strServiceName,strTransName,cdoRequest);		
		// 正常处理
		IService service = this.hmService.get(strServiceName);
		if(service==null){
			//service 未找到
			logger.error("can not find service named "+strServiceName+",Trans not supported:"+strServiceName+'.'+strTransName);			
			return Return.valueOf(-1,"can not find service named "+strServiceName);
		}
		Return ret = service.handleTrans(cdoRequest,cdoResponse);
		if(ret==null)
		{// Trans 不支持 或 未找到
			logger.error(" Return is null,maybe not find trans named "+strTransName+",Trans not supported:"+strServiceName+'.'+strTransName);	
			return Return.valueOf(-1, strServiceName+"."+strTransName+" Return is null,maybe not find trans  named");
		}
		onTransFinished(strServiceName,strTransName,cdoRequest,cdoResponse,ret);
		return ret;
	}

	public Object getSharedData(String strId)
	{
		if(strId==null)
		{
			return null;
		}
		
		Object objValue=null;
		
		ReentrantReadWriteLock.ReadLock lock=lockSharedData.readLock();
		lock.lock();
		objValue=hmSharedData.get(strId);
		lock.unlock();
		
		return objValue;
	}

	public void setSharedData(String strId,Object objValue)
	{
		if(strId==null)
		{
			return;
		}
		
		ReentrantReadWriteLock.WriteLock lock=lockSharedData.writeLock();
		lock.lock();
		hmSharedData.put(strId,objValue);
		lock.unlock();
	}
	
	public IService addService(IService service){
		return this.hmService.put(service.getServiceName(),service);
	}
 
	//接口实现,所有实现接口函数的实现在此定义--------------------------------------------------------------------
	/**
	 * 调用事件处理的方法
	 * ServiceBus会根据Request内的数据和ServiceBus.xml的配置内容，自动查找到对应的插件，调用其对象提供的处理方法
	 * @param cdoEvent 事件对象
	 */
	public void handleEvent(CDO cdoEvent)
	{
		for(Iterator<Map.Entry<String, IService>> it=hmService.entrySet().iterator();it.hasNext();){
			it.next().getValue().handleEvent(cdoEvent);
		}
		onEventHandled(cdoEvent);
	}

	public String getParameter(String strName,String strDefaultValue)
	{
		String strValue=hmParameterMap.get(strName);
		if(strValue==null)
		{
			return strDefaultValue;
		}
		return strValue;
	}

	public String getParameter(String strName)
	{
		return hmParameterMap.get(strName);
	}

	//事件处理,所有重载派生类的事件类方法(一般为on...ed)在此定义-------------------------------------------------

	//事件定义,所有在本类中定义并调用，由派生类实现或重载的事件类方法(一般为on...ed)在此定义---------------------
	private void onTransStarted(String strServiceName,String strTransName,CDO cdoRequest){
		
		if(logger.isDebugEnabled()){
			StringBuilder sb = new StringBuilder(50);
			sb.append("Starting handle ").append(" ServceName=").append(strServiceName).append(" transName=").append(strTransName).append("\r\n").append(cdoRequest.toString());
			logger.debug(sb.toString());	
			return; 
		}else if(logger.isInfoEnabled()){
			StringBuilder sb = new StringBuilder(50);
			sb.append("Starting handle ").append(" ServceName=").append(strServiceName).append(" transName=").append(strTransName);
			logger.info(sb.toString());				
		}
	}
	
	private void onTransFinished(String strServiceName,String strTransName,CDO cdoRequest,CDO cdoResponse,Return retResult){
		if(logger.isDebugEnabled()){
			StringBuilder sb = new StringBuilder(50);
			sb.append("End handle ").append(" ServiceName=").append(strServiceName).append(" transName=").append(strTransName).append("\r\n");
			sb.append(" Return code=").append(retResult.getCode()).append(" text=").append(retResult.getText()).append(" info=").append(retResult.getInfo());
			sb.append(" cdoResponse=").append(cdoResponse.toString());
			logger.debug(sb.toString());
			return;
		}else if(logger.isInfoEnabled()){
			StringBuilder sb = new StringBuilder(50);
			sb.append("End handle ").append(" ServiceName=").append(strServiceName).append(" transName=").append(strTransName).append("\r\n");
			sb.append(" Return code=").append(retResult.getCode()).append(" text=").append(retResult.getText()).append(" info=").append(retResult.getInfo());		
			logger.info(sb.toString());			
		}
	}


	public void onEventHandled(CDO cdoEvent)
	{
		try
		{
			if(logger.isDebugEnabled()){logger.debug(":Event handled:\r\n"+cdoEvent.toXML());}
		}
		catch(Exception e)
		{
			
		}
	}

	public void addClusterActiveService(String strActiveServiceId,IActiveService activeService)
	{
		if(clusterController!=null)
		{
			this.clusterController.addService(strActiveServiceId,activeService);
		}		
	}
		

	//构造函数,所有构造函数在此定义------------------------------------------------------------------------------

	public ServiceBus()
	{

		//请在此加入初始化代码,内部对象和属性对象负责创建或赋初值,引用对象初始化为null，初始化完成后在设置各对象之间的关系
		clusterController		=null;
		hmSharedData			=new HashMap<String,Object>();
		lockSharedData			=new ReentrantReadWriteLock();
		hmService 				= new LinkedHashMap<String,IService>(20);
		hmParameterMap			= new HashMap<String,String>(10);	
		btEngine				=new BigTableEngine();//去掉分库分表
		
	}
	/**
	 * 将每个插件的servie 按照zkId 进行合并
	 * @param pluginZkServiceMap
	 */
	public  void addZKService(Map<String, List<ZkParameter>> pluginZkServiceMap){		
		for(Iterator<Map.Entry<String, List<ZkParameter>>> it=pluginZkServiceMap.entrySet().iterator();it.hasNext();){
			Map.Entry<String,  List<ZkParameter>> entry=it.next();
			String  zkId=entry.getKey();
			List<ZkParameter> zkServiceList=null;
			if(zkServiceMap.containsKey(zkId)){				
				zkServiceList=zkServiceMap.get(zkId);
				zkServiceList.addAll(entry.getValue());
			}else{
				zkServiceList=entry.getValue();
			}			
			zkServiceMap.put(zkId, zkServiceList);			
		}
	}
	/**
	 * 将plugin.xml上的服务注册到zk上
	 * @param zkProducer
	 */
	private void initServiceOnZk(ZkProducer[] zkProducer){
		if(zkProducer==null || zkProducer.length==0){	
			//若服务需要注册到zk上,zk没有配置,则报错 退出系统
			checkZkProducer(zkServiceMap);
			return;
		}	
		//插件上的服务 注册到zk上
		List<ZookeeperServer> zkServerList=new ArrayList<ZookeeperServer>();
		for(int i=0;i<zkProducer.length;i++){
			List<ZkParameter> zkList=zkServiceMap.get(zkProducer[i].getId());
			if(zkList==null || zkList.size()==0){
				continue;
			}				
			ZookeeperServer zkServer=new ZookeeperServer(zkProducer[i].getConnect(),zkList,zkProducer[i].getWeight());
			zkServerList.add(zkServer);
			zkServiceMap.remove(zkProducer[i].getId());
		}
		//检查插件上的服务是否 可以全部注册，若不能到找到对应zkId注册,报错退出
		checkZkProducer(zkServiceMap);
		//
		for (ZookeeperServer zookeeperServer : zkServerList) {
			try {
				zookeeperServer.connectZookeeper();
			} catch (ZookeeperException e) {
				logger.error(e.getMessage(),e);
			}
		}				
	}
	
	private void checkZkProducer(Map<String, List<ZkParameter>> zkServiceMap){
		if(zkServiceMap.size()>0){			
			String  zkId="";
			for(Iterator<Map.Entry<String, List<ZkParameter>>> it=zkServiceMap.entrySet().iterator();it.hasNext();){
				  zkId=zkId+it.next().getKey()+",";
			}						
			for(int i=0;i<5;i++){
				logger.warn("zkId ["+zkId.substring(0,zkId.length()-1)+"] on ZkProducer is not defined,please check  ZkProducer element");
			}
		}
	}
	
	private void initZKClient(ZkConsumer[] zkConsumers){
		if(zkConsumers==null || zkConsumers.length==0){	
			//本机没有zk Client 创建			
			return;
		}	
		zkClientManager=ZKClientManager.getInstance();		
		for(int i=0;i<zkConsumers.length;i++){
			try{
				zkClientManager.addConnectZk(zkConsumers[i].getId(), zkConsumers[i].getConnect());
			}catch(Exception ex){				
				logger.error("zkId ["+zkConsumers[i].getId()+"],connect zk server ["+zkConsumers[i].getConnect()+"] failure,please check  zkConsumers element. error message:"+ex.getMessage(), ex);
				System.exit(-1);
			}
		}
		
	}
	
	public HashMap<String,IDataEngine> getHMDataGroup(){		
		return hmDataGroup;		
	}
	@Override
	public IClient getRPCClient(String zkId) {
		if(zkClientManager==null){
			logger.warn(" zkConsumers element is not defined,pelease check serviceBus.xml");
			return null;
		}
		return zkClientManager.getClient(zkId);
	}

}
