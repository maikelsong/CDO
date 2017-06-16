package com.cdoframework.cdolib.util;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.stream.Collectors;
//import com.cdoframework.cdolib.base.DateTime;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cdoframework.cdolib.base.Utility;
import com.cdoframework.cdolib.data.cdo.CDO;

public class JsonUtil {
//	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
//	private static DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);

	//---json中 value 数据类型 都转换成String,将json转换成CDO --/
	final static byte JSON2CDO_String=1;
	//==使用class定义的value 数据类型 [即在class中标识key类型即可],将jsong转换成CDO ==/	
	final static byte JSON2CDO_Class=2;

	
	final static String Class_Byte="BYTE";
	final static String Class_Short="SHORT";
	final static String Class_Int="INT";
	final static String Class_Integer="INTEGER";
	final static String Class_Long="LONG";
	final static String Class_Float="FLOAT";
	final static String Class_Double="DOUBLE";
	
	final static String Class_Boolean="BOOLEAN";
	final static String Class_String="STRING";
	final static String Class_Time="TIME";
	final static String Class_Date="DATE";
	final static String Class_DateTime="DATETIME";
	

	/**
	 * json中 value 数据类型 都转换成String,将json转换成CDO
	 * 
	 * @param strJSON  json中的数据用字符串处理
	 * @return CDO 
	 * @throws JSONException
	 * @throws Exception
	 */
	public static CDO json2CDO(String strJSON) throws JSONException{		
		return json2CDO(strJSON, JSON2CDO_String,null);
	}
	/**
	 * 使用class定义的value 数据类型 [即在class中标识key名称 的类型即可],将jsong转换成CDO
	 * @param strJSON
	 * @param cls 使用class定义的value 数据类型 [即在class中标识key类型即可],将jsong转换成CDO	
	 * @return
	 * @throws JSONException
	 */
	public static CDO json2CDO(String strJSON,Class cls) throws JSONException{		
		return json2CDO(strJSON,JSON2CDO_Class,cls);
	}

	/**
	 * 
	 * @param strJSON
	 * @param json2CDOType
	 * @param cls
	 * @return
	 * @throws JSONException
	 */
	private static CDO json2CDO(String strJSON,byte json2CDOType,Class cls) throws JSONException {
		if(strJSON==null)
			return null;
		CDO cdoRequest = new CDO();		
		JSONObject jsonObj = String2Json(strJSON); // 转换成jsonObjects
		setCDOFromJson(jsonObj, cdoRequest,json2CDOType,cls);		
		return cdoRequest;
	}
	

	/**
	 * json中 value 数据类型 都转换成String,json数组转换为CDO数组
	 * 
	 * @param jsonArray格式  [{...},..]
	 * @return CDO[]
	 * @throws Exception
	 */
	public static CDO[] jsonArray2CDOArray(String jsonArray) throws Exception {
		if (jsonArray == null || jsonArray.trim().length() <= 0 || jsonArray.equalsIgnoreCase("[]")) {
			return new CDO[0];
		}
		if(!jsonArray.startsWith("[")|| !jsonArray.endsWith("]")){
			throw new JSONException(jsonArray+" is not jsonArray");
		} 
		String tempList = "{\"cdosTempList\":" + jsonArray + "}";
		CDO tempCDO = JsonUtil.json2CDO(tempList);
		return tempCDO.getCDOArrayValue("cdosTempList");
	}
	
	/**
	 * 
	 * 使用class定义的value 数据类型 [即在class中标识key名称 的类型即可],json数组转换为CDO数组
	 * @param jsonArray jsonArray格式  [{...},..]
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static CDO[] jsonArray2CDOArray(String jsonArray,Class cls) throws Exception {
		if (jsonArray == null || jsonArray.trim().length() <= 0 || jsonArray.equalsIgnoreCase("[]")) {
			return new CDO[0];
		}
		if(!jsonArray.startsWith("[")|| !jsonArray.endsWith("]")){
			throw new JSONException(jsonArray+" is not jsonArray");
		} 
		String tempList = "{\"cdosTempList\":" + jsonArray + "}";
		CDO tempCDO = JsonUtil.json2CDO(tempList,cls);
		return tempCDO.getCDOArrayValue("cdosTempList");
	}
	
	/**
	 * CDO数组转换成json 数组
	 * @param cdos
	 * @return
	 */
	public static String cdoArray2JsonArray(CDO[] cdos) {
		if (cdos == null || cdos.length == 0) {
			return null;
		}
		StringBuffer buf = new StringBuffer(80);
		buf.append("[");
		for (int i = 0; i < cdos.length; i++) {
			if (i > 0) {
				buf.append(",");
			}
			buf.append(cdos[i].toJSON());
		}
		buf.append("]");
		return buf.toString();
	}
	/**
	 * 
	 * @param strJSON
	 * @return
	 * @throws JSONException
	 */
	private static JSONObject String2Json(String strJSON) throws JSONException {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(strJSON); // 偶数个的这里会转换成功
		} catch (org.json.JSONException jex) {
			// 特殊处理,替换1个反斜杠为2个,2个以上的不匹配
			String newStr = strJSON.replaceAll("\\\\{1}", "\\\\\\\\"); 
			jsonObj = new JSONObject(newStr);
		}
		return jsonObj;
	}
	/**
	 * 根据 Json数据格式，得到一个具有构造一个等级结构
	 * 
	 * @throws JSONException
	 */
	private static void setCDOFromJson(JSONObject jsonObj,CDO cdoRequest,byte json2CDOType,Class cls) throws JSONException {
		try {
			String key = "";
			Object obj = null;
			CDO subCDO=null;
			for (Iterator it = jsonObj.keys(); it.hasNext();) {
				key = (String) it.next();
				obj = jsonObj.get(key);
				if (obj instanceof JSONObject) {
					subCDO = new CDO();
					cdoRequest.setCDOValue(key, subCDO);
					setCDOFromJson((JSONObject) obj, subCDO,json2CDOType,cls);
				} else if (obj instanceof JSONArray) {
					//数组 里的数据 1为普通数据类型,2 json对象类型,3 不支持数组嵌套数组 ,混合数据
					JSONArray jsonArr=(JSONArray) obj;
					if(jsonArr.length()==0){	
						setEmptyArray(cdoRequest, key, json2CDOType, cls);
						return;
					}					
				   setCDOListFromJson((JSONArray) obj,cdoRequest,key,json2CDOType,cls);
				} else {
					//设置普通数据类型
					setCommonField(cdoRequest, key, obj, json2CDOType, cls);
				}
			}
		} catch (Exception e) {
			throw new JSONException(e);
		}
	}
	
	
	private static void setCDOListFromJson(JSONArray jsonObj,CDO cdoParent,String key,byte json2CDOType,Class cls) throws Exception {
		Object obj = null;
		List<String> commonList=null;
		List<CDO> cdoList=null;
		for (int i = 0; i < jsonObj.length(); i++) {
			obj = jsonObj.get(i);
			if (obj instanceof JSONObject) {
				if(cdoList==null){
					cdoList=new ArrayList<CDO>();
					cdoParent.setCDOListValue(key, cdoList);	
				}				
				CDO subCDO = new CDO();
				cdoList.add(subCDO);
				setCDOFromJson((JSONObject) obj, subCDO,json2CDOType,cls);
			} else if (obj instanceof JSONArray) {
				throw new JSONException("unsupport json Array nesting, [[]] is unsupported");				
			} else {
				if(commonList==null)
					commonList=new ArrayList<String>();
				commonList.add(obj==null?"":obj.toString());
			}
		}
		//设置普通类型数组
		if(commonList!=null){
			setCommontArray(cdoParent, key,commonList, json2CDOType, cls);
		}

	}
	
	private static void setCommonField(CDO cdoRequest,String key,Object values,byte json2CDOType,Class cls) throws JSONException{
		try{
		switch (json2CDOType) {
			case JSON2CDO_Class:
				{
					String type=cls.getDeclaredField(key).getType().getSimpleName().toUpperCase();
					type=type.indexOf("[")>0?type.substring(0,type.indexOf("[")):type;
					switch(type){
						case Class_Byte:
							 cdoRequest.setByteValue(key,Utility.parseByteValue(values));
							 break;
						case Class_Short:
							 cdoRequest.setShortValue(key, Utility.parseShortValue(values));
							 break;
						case Class_Int:
							  cdoRequest.setIntegerValue(key, Utility.parseIntegerValue(values));
							  break;
						case Class_Integer:
							  cdoRequest.setIntegerValue(key, Utility.parseIntegerValue(values));
							  break;
						case Class_Long:
							 cdoRequest.setLongValue(key, Utility.parseLongValue(values));
							 break;
						case Class_Float:
							cdoRequest.setFloatValue(key,  Utility.parseFloatValue(values));
							break;
						case Class_Double:	
							cdoRequest.setDoubleValue(key, Utility.parseDoubleValue(values));
							break;
						case Class_Boolean:	
							cdoRequest.setBooleanValue(key,Utility.parseBooleanValue(values));
							break;
						case Class_String:
							cdoRequest.setStringValue(key,Utility.parseStingValue(values));
							break;
						case Class_Time:
							cdoRequest.setTimeValue(key, Utility.parseTimeValue(values));
							break;
						case Class_Date:
							cdoRequest.setDateValue(key, Utility.parseDateValue(values));
							break;
						case Class_DateTime:	
							cdoRequest.setDateTimeValue(key,Utility.parseDateTimeValue(values));
							break;
						default:
							throw new JSONException("unsupported json to cdo type,Json Key=["+key+"] Json value["+values+"] cast to "+cls.getDeclaredField(key).getType().getName());							
					}
				}
				break;
			case JSON2CDO_String:
				cdoRequest.setStringValue(key, Utility.parseStingValue(values));
				break;
			default:
				throw new JSONException("unsupported json to cdo type,json2CDOType="+json2CDOType);
		 }
		}catch(Exception ex){
			throw new JSONException(ex);
		}

	}
	private static void setEmptyArray(CDO cdoRequest,String key,byte json2CDOType,Class cls) throws JSONException{
		try{
		switch (json2CDOType) {
			case JSON2CDO_Class:
				{
					String type=cls.getDeclaredField(key).getType().getSimpleName().toUpperCase();
					type=type.indexOf("[")>0?type.substring(0,type.indexOf("[")):type;
					switch(type){
						case Class_Byte:
							 cdoRequest.setByteArrayValue(key, new byte[0]);
							 break;
						case Class_Short:
							 cdoRequest.setShortArrayValue(key, new short[0]);
							 break;
						case Class_Int:
							  cdoRequest.setIntegerArrayValue(key, new int[0]);
							  break;
						case Class_Integer:
							  cdoRequest.setIntegerArrayValue(key, new int[0]);
							  break;
						case Class_Long:
							 cdoRequest.setLongArrayValue(key, new long[0]);
							 break;
						case Class_Float:
							cdoRequest.setFloatArrayValue(key, new float[0]);
							break;
						case Class_Double:	
							cdoRequest.setDoubleArrayValue(key, new double[0]);
							break;
						case Class_Boolean:	
							cdoRequest.setBooleanArrayValue(key, new boolean[0]);
							break;
						case Class_String:
							cdoRequest.setStringArrayValue(key, new String[0]);
							break;
						case Class_Time:
							cdoRequest.setTimeArrayValue(key, new String[0]);
							break;
						case Class_Date:
							cdoRequest.setDateArrayValue(key, new String[0]);
							break;
						case Class_DateTime:	
							cdoRequest.setDateTimeArrayValue(key, new String[0]);
							break;
						default:
							throw new JSONException("unsupported json to cdo type="+cls.getDeclaredField(key).getType().getName());							
					}
				}
				break;
			default:
				cdoRequest.setStringArrayValue(key, new String[0]);
				break;
		 }
		}catch(Exception ex){
			throw new JSONException(ex);
		}

	}
	/**
	 * 设置常规类型数组
	 * @param cdoRequest
	 * @param key
	 * @param values
	 * @param json2CDOType
	 * @param cls
	 * @throws JSONException
	 */
	private static void setCommontArray(CDO cdoRequest,String key,List<String> commonList,byte json2CDOType,Class cls) throws JSONException{
		String[] values=commonList.toArray(new String[commonList.size()]);
		try{
		switch (json2CDOType) {
			case JSON2CDO_Class:
				{
					String type=cls.getDeclaredField(key).getType().getSimpleName().toUpperCase();
					type=type.indexOf("[")>0?type.substring(0,type.indexOf("[")):type;
					switch(type){
						case Class_Byte:
							 cdoRequest.setByteArrayValue(key,Utility.parseByteArrayValue(values));
							 break;
						case Class_Short:
							 cdoRequest.setShortArrayValue(key, Utility.parseShortArrayValue(values));
							 break;
						case Class_Int:
							  cdoRequest.setIntegerArrayValue(key, Utility.parseIntegerArrayValue(values));
							  break;
						case Class_Integer:
							  cdoRequest.setIntegerArrayValue(key, Utility.parseIntegerArrayValue(values));
							  break;
						case Class_Long:
							 cdoRequest.setLongArrayValue(key, Utility.parseLongArrayValue(values));
							 break;
						case Class_Float:
							cdoRequest.setFloatArrayValue(key,  Utility.parseFloatArrayValue(values));
							break;
						case Class_Double:	
							cdoRequest.setDoubleArrayValue(key, Utility.parseDoubleArrayValue(values));
							break;
						case Class_Boolean:	
							cdoRequest.setBooleanArrayValue(key,Utility.parseBooleanArrayValue(values));
							break;
						case Class_String:
							cdoRequest.setStringArrayValue(key, Utility.parseStringArrayValue(values));
							break;
						case Class_Time:
							cdoRequest.setTimeArrayValue(key, values);
							break;
						case Class_Date:
							cdoRequest.setDateArrayValue(key, values);
							break;
						case Class_DateTime:	
							cdoRequest.setDateTimeArrayValue(key,values);
							break;
						default:
							throw new JSONException("unsupported json to cdo type,Json Key=["+key+"] Json value["+commonList+"] cast to "+cls.getDeclaredField(key).getType().getName());							
					}
				}
				break;
			case JSON2CDO_String:
				cdoRequest.setStringArrayValue(key, values);
				break;
			default:
				throw new JSONException("unsupported json to cdo type,json2CDOType="+json2CDOType);
		 }
		}catch(Exception ex){
			throw new JSONException(ex);
		}
	}
}
