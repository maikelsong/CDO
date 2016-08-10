package com.cdo.business.client.rpc;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.cdo.google.handle.CDOMessage;
import com.cdo.google.handle.Header;
import com.cdo.google.handle.ProtoProtocol;
import com.cdo.google.protocol.GoogleCDO;
import com.cdo.util.common.UUidGenerator;
import com.cdo.util.constants.Constants;
import com.cdoframework.cdolib.base.DataType;
import com.cdoframework.cdolib.data.cdo.CDO;
import com.cdoframework.cdolib.data.cdo.Field;
import com.cdoframework.cdolib.data.cdo.FileField;
import com.google.protobuf.ByteString;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class RPCClientHandler extends  ChannelInboundHandlerAdapter {
	private static Logger logger=Logger.getLogger(RPCClientHandler.class);

    private volatile Channel channel;
    private ByteString clientId; 
    final CallsLinkedHashMap calls = new CallsLinkedHashMap();
    /** A counter for generating call IDs. */
    static final AtomicInteger callIdCounter = new AtomicInteger();
           
    public GoogleCDO.CDOProto handleTrans(CDO cdoRequest) {    	
    	//CDO请求数据
    	int callId=callIdCounter.getAndIncrement() & Integer.MAX_VALUE;
        final Call call =new Call(callId);    
    	GoogleCDO.CDOProto.Builder proto=cdoRequest.toProtoBuilder();
    	proto.setCallId(callId);
		proto.setClientId(clientId);	
		//构造发送message 类型数据
		Header reqHeader=new Header();
		reqHeader.setType(ProtoProtocol.TYPE_CDO);
		CDOMessage reqMessage=new CDOMessage();
		reqMessage.setHeader(reqHeader);
		reqMessage.setBody(proto.build());
		if(cdoRequest.getSerialFileCount()>0){
			 List<File> files=new ArrayList<File>();
			 Iterator<Map.Entry<String,Field>> it=cdoRequest.entrySet().iterator();
    		 while(it.hasNext()){
    			 Map.Entry<String,Field> entry=it.next();
    			 Field objExt=entry.getValue();
    			 if(objExt.getType().getDataType()==DataType.FILE_TYPE){
    				 File f=((FileField)entry.getValue()).getValue();
    				 if(!f.exists() || !f.isFile()){
    					 //文件不存在直接返回 
    					 CDO cdoOutput=new CDO();
    					 CDO cdoResponse=new CDO();
    					 CDO cdoReturn=new CDO();
    					 cdoReturn.setIntegerValue("nCode",-1);
    					 cdoReturn.setStringValue("strText","file field:"+entry.getKey()+",value:"+f.getName()+" is not file");
    					 cdoReturn.setStringValue("strInfo","file field:"+entry.getKey()+",value:"+f.getName()+" is not file");

    					 cdoOutput.setCDOValue("cdoReturn",cdoReturn);
    					 cdoOutput.setCDOValue("cdoResponse", cdoResponse);	    					 
    					 return cdoOutput.toProtoBuilder().build();
    				 }		 
    				 files.add(f);
    			 }
    		 }
    		 reqMessage.setFiles(files);
		}
		
		
        channel.write(reqMessage); 
        channel.flush();
        calls.put(callId, call);
        boolean interrupted = false;
        synchronized (call) {
          while (!call.done()) {
            try {
              call.wait();                 
            } catch (InterruptedException ie) {
              // save the fact that we were interrupted
              interrupted = true;
            }
          }
          if (interrupted) {
            // set the interrupt flag now that we are done waiting
            Thread.currentThread().interrupt();
          }    
         return call.getRpcResponse();
       }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
        clientId=ByteString.copyFrom(UUidGenerator.ClientId.getClientId());
    }
    
    
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {// ping message when there is no outbound traffic for 10 seconds  see RPCClient,RPCServerInitializer         	
                case WRITER_IDLE:
        			CDOMessage heartBeat=new CDOMessage();
        			Header header=new Header();
        			header.setType(ProtoProtocol.TYPE_HEARTBEAT_REQ);
        			heartBeat.setHeader(header);
        			ctx.writeAndFlush(heartBeat);
                    break;
                default:
                    break;
            }
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {    
    	if(msg instanceof CDOMessage){   
    		CDOMessage cdoMessage=(CDOMessage)msg;
    		if(cdoMessage.getHeader().getType()==ProtoProtocol.TYPE_HEARTBEAT_RES){
    			if(logger.isInfoEnabled())
    				logger.info("client receive server heart msg:"+msg);
    		}else if(cdoMessage.getHeader().getType()==ProtoProtocol.TYPE_CDO){
        		GoogleCDO.CDOProto proto=(GoogleCDO.CDOProto)(cdoMessage.getBody());
    			int callId=proto.getCallId();
    			Call call = calls.get(callId);
    	        calls.remove(callId);
    	        call.setRpcResponse(proto);		    			
    		}
    	}else{
    		ctx.fireChannelRead(msg);
    	}        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error(cause.getMessage(),cause);
    	ctx.fireExceptionCaught(cause);
    }
      
	
}
