package com.cdoframework.cdolib.servicebus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdoframework.cdolib.annotation.TransName;
import com.cdoframework.cdolib.base.Return;
import com.cdoframework.cdolib.data.cdo.CDO;
import com.cdoframework.cdolib.database.IDataEngine;
public abstract class TransService implements ITransService
{
	private Logger logger = Logger.getLogger(TransService.class);
	private String strServiceName;
	protected IServiceBus serviceBus=null;
	protected IServicePlugin servicePlugin=null;
	protected IService service = null;
	
	protected Map<String, Method> transMap = new HashMap<String, Method>();

	final public void setServiceBus(IServiceBus serviceBus)
	{
		
		this.serviceBus=serviceBus;
	}

	final public void setServicePlugin(IServicePlugin servicePlugin)
	{
		this.servicePlugin=servicePlugin;
	}

	final public void setService(IService service)
	{
		this.service = service;
	}
	final public IService getService()
	{
		return this.service;
	}
	public Return init()
	{	
		return Return.OK;
	}
	
	@Override
	public void inject(ITransService child) {
		if(child != null)
		{
			Class<?> cls = child.getClass();
			Method[] methods = cls.getMethods();
			TransName transName = null;
			String name = null;
			for(Method method : methods) {
				// 查找所有带@TransName方法
				if(method.isAnnotationPresent(TransName.class)) {
					transName = method.getAnnotation(TransName.class);
					name = transName.name();
					if(name == null || name.equals("")) {
						name = method.getName();
					}
					// 一个服务类禁止出现重名的transName
					if(transMap.put(name, method) != null) {
						logger.error("存在同名的transName："+ name);
						System.exit(-1);
					}
				}
			}
		}
	}
	/**
	 * 设置服务名
	 * @param strServiceName
	 */
	final public void setServiceName(String strServiceName)
	{
		this.strServiceName = strServiceName;
	}
	
	protected Return validate(CDO cdoRequest){
		return Return.OK;
	}
	/**
	 * 取服务名
	 * @return
	 */
	final public String getServiceName()
	{
		return this.strServiceName;
	}

	public void destroy()
	{
	}

	public void handleEvent(CDO cdoEvent)
	{
	}
		

	@Override
	public final Return processTrans(CDO cdoRequest, CDO cdoResponse) {
		String strTransName = cdoRequest.getStringValue(ITransService.TRANSNAME_KEY);
		Method method = null;
		if((method = transMap.get(strTransName)) != null) {
			try {
				return (Return) method.invoke(this, cdoRequest, cdoResponse);
			} catch (IllegalArgumentException e) {
				logger.warn(strTransName+": 参数错误"+ cdoRequest+cdoResponse);
			} catch (IllegalAccessException e) {
				logger.warn(strTransName+": 函数访问错误IllegalAccessException");
			} catch (InvocationTargetException e) {
				logger.warn(strTransName+": 函数调用错误InvocationTargetException");
			}
		} 
		return null;
	}
	
	@Override
	public boolean containsTrans(String strTransName) {
		return transMap.containsKey(strTransName);
	}
	@Override
	public Connection getConnection(String strDataGroupId) throws SQLException{
		IDataEngine dataEngine=this.serviceBus.getHMDataGroup().get(strDataGroupId);
		return dataEngine.getConnection();
	}
	@Override
	public String getDBCharset(String strDataGroupId){
		IDataEngine dataEngine=this.serviceBus.getHMDataGroup().get(strDataGroupId);
		return dataEngine.getCharset();
	}
}
