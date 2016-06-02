/**
 * www.cdoforum.com 2007版权所有
 *
 * $Header: /CVSData/Frank/CVSROOT/CDOForum/CDOLib/Source/com/cdoframework/cdolib/data/cdo/LongField.java,v 1.4 2008/03/12 10:30:57 Frank Exp $
 *
 * $Log: LongField.java,v $
 * Revision 1.4  2008/03/12 10:30:57  Frank
 *
 */

package com.cdoframework.cdolib.data.cdo;

import java.nio.ByteBuffer;
import java.util.Map;

import com.cdoframework.cdolib.base.DataType;
import com.cdoframework.cdolib.base.ObjectExt;
import com.cdoframework.cdolib.base.Utility;

/**
 * @author Frank
 */
public class LongField extends ValueFieldImpl
{

	//内部类,所有内部类在此声明----------------------------------------------------------------------------------

	//静态对象,所有static在此声明并初始化------------------------------------------------------------------------

	//内部对象,所有在本类中创建并使用的对象在此声明--------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -4369163444075495461L;
	//属性对象,所有在本类中创建，并允许外部访问的对象在此声明并提供get/set方法-----------------------------------
	private long lValue;
	public void setValue(long lValue)
	{
		this.lValue=lValue;
	}
	public long getValue()
	{
		return this.lValue;
	}

	//引用对象,所有在外部创建并传入使用的对象在此声明并提供set方法-----------------------------------------------

	//内部方法,所有仅在本类或派生类中使用的函数在此定义为protected方法-------------------------------------------

	//公共方法,所有可提供外部使用的函数在此定义为public方法------------------------------------------------------
	public void toAvro(String prefixField,Map<String,ByteBuffer> fieldMap){
		int len=1+8;//字段类型所占字节+数据所占字节
		ByteBuffer buffer=ByteBuffer.allocate(len);
		buffer.put((byte)DataType.LONG_TYPE);
		buffer.putLong(lValue);
		buffer.flip();
		
		fieldMap.put(prefixField+this.getName(), buffer);
	}	
	
	public void toXML(StringBuilder strbXML)
	{
		strbXML.append("<LF N=\"").append(this.getName()).append("\"");
		strbXML.append(" V=\"").append(this.lValue).append("\"/>");
	}

	public void toXMLWithIndent(int nIndentSize,StringBuilder strbXML)
	{
		String strIndent=Utility.makeSameCharString('\t',nIndentSize);
		
		strbXML.append(strIndent).append("<LF N=\"").append(this.getName()).append("\"");
		strbXML.append(" V=\"").append(this.lValue).append("\"/>\r\n");
	}

	public Object getObjectValue()
	{
		return new Long(lValue);
	}

	public ObjectExt getObject()
	{
		return new ObjectExt(this.getType(),lValue);
	}

	//接口实现,所有实现接口函数的实现在此定义--------------------------------------------------------------------

	//事件处理,所有重载派生类的事件类方法(一般为on...ed)在此定义-------------------------------------------------

	//事件定义,所有在本类中定义并调用，由派生类实现或重载的事件类方法(一般为on...ed)在此定义---------------------

	//构造函数,所有构造函数在此定义------------------------------------------------------------------------------

	public LongField(String strFieldName)
	{

		//请在此加入初始化代码,内部对象和属性对象负责创建或赋初值,引用对象初始化为null，初始化完成后在设置各对象之间的关系
		super(strFieldName);
		
		setType(ValueField.LONG_TYPE);
		
		this.lValue	=0;
	}

	public LongField(String strFieldName,long lValue)
	{

		//请在此加入初始化代码,内部对象和属性对象负责创建或赋初值,引用对象初始化为null，初始化完成后在设置各对象之间的关系
		super(strFieldName);
		
		setType(ValueField.LONG_TYPE);
		
		this.lValue	=lValue;
	}

	public String toJSON()
	{
		StringBuffer str_JSON=new StringBuffer();
		str_JSON.append("\"").append(this.getName()).append("\"").append(":").append(this.lValue).append(",");		
		return str_JSON.toString();
	}

	public String toJSONString()
	{
		StringBuffer str_JSON=new StringBuffer();
		str_JSON.append("\\\"").append(this.getName()).append("\\\"").append(":").append(this.lValue).append(",");
		return str_JSON.toString();
	}
}
