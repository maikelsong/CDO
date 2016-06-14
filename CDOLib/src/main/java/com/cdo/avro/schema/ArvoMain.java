package com.cdo.avro.schema;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.ipc.HandshakeRequest;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.cdo.avro.protocol.AvroCDO;
import com.cdoframework.cdolib.data.cdo.BooleanArrayField;
import com.cdoframework.cdolib.data.cdo.CDO;
import com.cdoframework.cdolib.data.cdo.DateArrayField;
import com.cdoframework.cdolib.data.cdo.DateTimeArrayField;
import com.cdoframework.cdolib.data.cdo.IntegerArrayField;
import com.cdoframework.cdolib.data.cdo.LongArrayField;
import com.cdoframework.cdolib.data.cdo.ShortArrayField;
import com.cdoframework.cdolib.data.cdo.TimeArrayField;



public class ArvoMain {
	 private static  String schemaDescription =
	         "{ \n" +
	            " \"namespace\": \"example.avro\", \n" +
	            " \"name\": \"AvroCDO\", \n" +
	            " \"type\": \"record\",\n" +
	            " \"fields\": [\n" +	                     
	                     " {\"name\":\"booleanValue\",\"type\":[\"null\",\"boolean\"]},\n"+
	                     " {\"name\":\"stringValue\",\"type\":[\"null\",\"string\"]},\n"+
	                     " {\"name\":\"timeValue\",\"type\":[\"null\",\"string\"]},\n"+
	                     " {\"name\":\"dateValue\",\"type\":[\"null\",\"string\"]},\n"+
	                     " {\"name\":\"dateTimeValue\",\"type\":[\"null\",\"string\"]},\n"+
	                     " {\"name\":\"byteValue\",\"type\":[\"null\",\"bytes\"]},\n"+
	                     " {\"name\":\"shortValue\",\"type\":[\"null\",\"int\"]},\n"+
	                     " {\"name\":\"integerValue\",\"type\":[\"null\",\"int\"]},\n"+
	                     " {\"name\":\"longValue\",\"type\":[\"null\",\"long\"]},\n"+
	                     " {\"name\":\"floatValue\",\"type\":[\"null\",\"float\"]},\n"+
	                     " {\"name\":\"doubleValue\",\"type\":[\"null\",\"double\"]},\n"+
	                     
	                     "{\"name\":\"booleanArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"boolean\"}]},\n"+
	                     "{\"name\":\"stringArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"string\"}]},\n"+
	                     "{\"name\":\"timeArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"string\"}]},\n"+
	                     "{\"name\":\"dateArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"string\"}]},\n"+
	                     "{\"name\":\"dateTimeArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"string\"}]},\n"+
	                     "{\"name\":\"byteArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"bytes\"}]},\n"+
	                     "{\"name\":\"shortArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},\n"+
	                     "{\"name\":\"integerArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},\n"+
	                     "{\"name\":\"longArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"long\"}]},\n"+
	                     "{\"name\":\"floatArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"float\"}]},\n"+
	                     "{\"name\":\"doubleArrayValue\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"double\"}]}\n"+
	                     
	            "]\n" +
	         "}";

	       private static  String schemaDescriptionExt =
	         " { \n" +
	             " \"namespace\": \"example.avro\", \n" +
	             " \"name\": \"CDO\", \n" +
	             " \"type\": \"record\",\n" +
	             " \"fields\": [\n" +
	                      " {\"name\": \"CDO\", \"type\": example.avro.AvroCDO },\n" +
	                      " {\"name\": \"specialData\", \"type\": \"int\"} ]\n" +
	          "}";

	       
	public static void main(String[] args) {
		try{
//			schemaDescription=readFile(new FileInputStream("E:/CDO/CDO/CDOLib/src/main/avro/CDONameValuePair.avsc"));
//			AvroUtils.parseSchema(schemaDescription);
//			schemaDescriptionExt=readFile(new FileInputStream("E:/CDO/CDO/CDOLib/src/main/avro/ArrayCDONameValuePair.avsc"));
//			Schema extended = AvroUtils.parseSchema(schemaDescriptionExt);
//			System.out.println(extended.toString(true));

		
			testCDO();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static String readFile(InputStream inputStream) throws IOException{
 	  	BufferedReader in=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
  	  	StringBuilder message = new StringBuilder();   
  	    String line = null;  
  	    while ((line = in.readLine()) != null) {   
  	    	message.append(line+"\n");   
  	          } 
  	    return message.toString();
	}
	private static void  testAvro(CDO cdo) throws IOException{
		
		long startTime=System.nanoTime();	
		
		LinkedHashMap<CharSequence,ByteBuffer> fieldMap=new LinkedHashMap<CharSequence,ByteBuffer>();
//		int level=cdo.toAvroFieldMap(fieldMap);
		
		System.out.println("toMap nan s ="+(System.nanoTime()-startTime));
		startTime=System.nanoTime();	
		
		AvroCDO arvoCDO=new  AvroCDO();
		arvoCDO.setFields(fieldMap);
//		arvoCDO.setLevel(level);
		
		System.out.println("new AvroCDO nan s ="+(System.nanoTime()-startTime));
		startTime=System.nanoTime();
		
		ByteArrayOutputStream out=new ByteArrayOutputStream();
	    DatumWriter<AvroCDO> writer=new SpecificDatumWriter<AvroCDO>(AvroCDO.class);    
	    DataFileWriter<AvroCDO> dataFileWriter = new DataFileWriter<AvroCDO>(writer); 		          
	    dataFileWriter.create(arvoCDO.getSchema(),out);
	    
	    System.out.println("create avro write serialize shcema ns==="+(System.nanoTime()-startTime));
	    startTime=System.nanoTime();
	    
	    dataFileWriter.append(arvoCDO);
		dataFileWriter.close();
		out.close();
		
		System.out.println("avro write serialize  data ns==="+(System.nanoTime()-startTime));
		startTime=System.nanoTime();
		
		ByteArrayInputStream in=new ByteArrayInputStream(out.toByteArray());
	    DatumReader<AvroCDO> reader=new SpecificDatumReader<AvroCDO>(AvroCDO.class);	    	   
		DataFileStream<AvroCDO> dataFileReader = new DataFileStream<AvroCDO>(in,reader);
	
		System.out.println("create avro reader deserialize shcema ns==="+(System.nanoTime()-startTime));	  
	    startTime=System.nanoTime();
	    
		arvoCDO= null;
	    while (dataFileReader.hasNext()) {
	    	   arvoCDO = dataFileReader.next();	            
	    }	
	    
		System.out.println("avro  read deserialize  data ns==="+(System.nanoTime()-startTime));
	}
	private static void  testHandshake() throws IOException{
		
		long startTime=System.nanoTime();	
		
		
		HandshakeRequest request=new HandshakeRequest();
//		arvoCDO.setFields(fieldMap);
//		arvoCDO.setLevel(level);
		
		System.out.println("new Handshake nan s ="+(System.nanoTime()-startTime));
		startTime=System.nanoTime();
		
		ByteArrayOutputStream out=new ByteArrayOutputStream();
	    DatumWriter<HandshakeRequest> writer=new SpecificDatumWriter<HandshakeRequest>(HandshakeRequest.class);    
	    DataFileWriter<HandshakeRequest> dataFileWriter = new DataFileWriter<HandshakeRequest>(writer); 		          
	    dataFileWriter.create(request.getSchema(),out);
	    
	    System.out.println("create Handshake write serialize shcema ns==="+(System.nanoTime()-startTime));
	    startTime=System.nanoTime();
	    
	    dataFileWriter.append(request);
		dataFileWriter.close();
		out.close();
		
		System.out.println("avro Handshake serialize  data ns==="+(System.nanoTime()-startTime));
		startTime=System.nanoTime();
		
		ByteArrayInputStream in=new ByteArrayInputStream(out.toByteArray());
	    DatumReader<HandshakeRequest> reader=new SpecificDatumReader<HandshakeRequest>(HandshakeRequest.class);	    	   
		DataFileStream<HandshakeRequest> dataFileReader = new DataFileStream<HandshakeRequest>(in,reader);
	
		System.out.println("create Handshake reader deserialize shcema ns==="+(System.nanoTime()-startTime));	  
	    startTime=System.nanoTime();
	    
	    request= null;
	    while (dataFileReader.hasNext()) {
	    	request = dataFileReader.next();	            
	    }	
	    
		System.out.println("Handshake  read deserialize  data ns==="+(System.nanoTime()-startTime));
	}
	
	private static void testCDO(){
	CDO cdo = new CDO();

		
		cdo.setByteValue("byte", (byte)2);
		cdo.setByteArrayValue("bytes", new byte[]{1,2,3});
		cdo.setBooleanValue("bvalue", true);
		cdo.setBooleanArrayValue("bsValue", new boolean[]{false,true,true,false});
		cdo.setShortValue("short", (short)100);
		cdo.setShortArrayValue("shorts", new short[]{100,200,300});
		cdo.setIntegerValue("int", 300);
		cdo.setIntegerArrayValue("ints", new int[]{400,500,600});
		cdo.setLongValue("long", 7000);
		cdo.setLongArrayValue("longs", new long[]{9000,10000});
		cdo.setFloatValue("float", 3.0f);
		cdo.setFloatArrayValue("floats", new float[]{1.0f,2.0f,3.0f});
		cdo.setDoubleValue("double", 5.0);
		cdo.setDoubleArrayValue("doubles", new double[]{6.0,7.0,8.0});
		cdo.setStringValue("str", "张三");
		cdo.setStringArrayValue("strvalues", new String[]{ "张3", "张4", "张5"});
		cdo.setDateValue("date", "2016-05-01");
		cdo.setDateArrayValue("date1", new String[]{"2012-05-01","2013-05-01","2014-05-01"});
		cdo.setTimeValue("time", "20:00:00");
		cdo.setTimeArrayValue("times", new String[]{"17:00:00","18:00:00","20:00:00"});
		cdo.setDateTimeValue("dateTime", "2012-05-01 20:00:00");
		cdo.setDateTimeArrayValue("dateTimeValues", new String[]{"2012-05-01 20:00:00","2013-05-01 21:00:00","2014-05-01 22:00:00"});
		System.out.println(cdo.getBooleanValue("bvalue"));
		System.out.println(cdo.getBooleanValue("bvalue"));
		CDO cdo2=new CDO();
		cdo.setCDOValue("cdo2", cdo2);
		cdo.setIntegerValue("cdo2.is", 1);
		cdo.setIntegerValue("ints[0]", 200);
		System.out.println(cdo.toXMLWithIndent());
		for(int i=0;i<5;i++){
			System.out.println(cdo.getBooleanValue("bvalue"));
			System.out.println(((BooleanArrayField)cdo.getField("bsValue")).getValue());
			System.out.println(((BooleanArrayField)cdo.getField("bsValue")).getValue());
		    ((BooleanArrayField)cdo.getField("bsValue")).setValueAt(3, true);
			System.out.println(((BooleanArrayField)cdo.getField("bsValue")).getValue());
			
			System.out.println(((DateArrayField)cdo.getField("date1")).getValueAt(1));
			System.out.println(((DateArrayField)cdo.getField("date1")).getValueAt(1));
			System.out.println(((DateArrayField)cdo.getField("date1")).getValue()[2]);
			System.out.println(((DateArrayField)cdo.getField("date1")).getValue()[2]);
			System.out.println(((DateArrayField)cdo.getField("date1")).getLength());
			System.out.println(((DateArrayField)cdo.getField("date1")).getLength());	
			
			System.out.println(((TimeArrayField)cdo.getField("times")).getValueAt(1));
			System.out.println(((TimeArrayField)cdo.getField("times")).getValueAt(1));
			System.out.println(((TimeArrayField)cdo.getField("times")).getValue()[0]);
			System.out.println(((TimeArrayField)cdo.getField("times")).getValue()[0]);
			System.out.println(((TimeArrayField)cdo.getField("times")).getLength());
			System.out.println(((TimeArrayField)cdo.getField("times")).getLength());
			
			System.out.println(((DateTimeArrayField)cdo.getField("dateTimeValues")).getValueAt(1));
			System.out.println(((DateTimeArrayField)cdo.getField("dateTimeValues")).getValueAt(1));
			System.out.println(((DateTimeArrayField)cdo.getField("dateTimeValues")).getValue()[0]);
			System.out.println(((DateTimeArrayField)cdo.getField("dateTimeValues")).getValue()[0]);
			System.out.println(((DateTimeArrayField)cdo.getField("dateTimeValues")).getLength());
			System.out.println(((DateTimeArrayField)cdo.getField("dateTimeValues")).getLength());
			
			System.out.println(((ShortArrayField)cdo.getField("shorts")).getValueAt(1));
			System.out.println(((ShortArrayField)cdo.getField("shorts")).getValueAt(1));
			System.out.println(((ShortArrayField)cdo.getField("shorts")).getLength());
			System.out.println(((ShortArrayField)cdo.getField("shorts")).getLength());			
			System.out.println(((ShortArrayField)cdo.getField("shorts")).getValue()[0]);
			System.out.println(((ShortArrayField)cdo.getField("shorts")).getValue()[0]);

			System.out.println(((IntegerArrayField)cdo.getField("ints")).getValueAt(1));
			System.out.println(((IntegerArrayField)cdo.getField("ints")).getValueAt(1));
			System.out.println(((IntegerArrayField)cdo.getField("ints")).getLength());
			System.out.println(((IntegerArrayField)cdo.getField("ints")).getLength());			
			System.out.println(((IntegerArrayField)cdo.getField("ints")).getValue()[0]);
			System.out.println(((IntegerArrayField)cdo.getField("ints")).getValue()[0]);
			
			System.out.println(((LongArrayField)cdo.getField("longs")).getValueAt(1));
			System.out.println(((LongArrayField)cdo.getField("longs")).getValueAt(1));
			System.out.println(((LongArrayField)cdo.getField("longs")).getLength());
			System.out.println(((LongArrayField)cdo.getField("longs")).getLength());			
			System.out.println(((LongArrayField)cdo.getField("longs")).getValue()[0]);
			System.out.println(((LongArrayField)cdo.getField("longs")).getValue()[0]);
			
			
		}
		
		
	}
}
