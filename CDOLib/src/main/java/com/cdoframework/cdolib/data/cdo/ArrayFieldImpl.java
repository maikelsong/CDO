/**
 * www.cdoforum.com 2007版权所有
 *
 * $Header: /CVSData/Frank/CVSROOT/CDOForum/CDOLib/Source/com/cdoframework/cdolib/data/cdo/ArrayField.java,v 1.2 2008/03/12 10:30:57 Frank Exp $
 *
 *
 */

package com.cdoframework.cdolib.data.cdo;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.cdoframework.cdolib.base.DataType;

/**
 * @author Frank
 */
public abstract class ArrayFieldImpl extends ValueFieldImpl implements ArrayField
{

	//内部类,所有内部类在此声明----------------------------------------------------------------------------------

	//静态对象,所有static在此声明并初始化------------------------------------------------------------------------

	//内部对象,所有在本类中创建并使用的对象在此声明--------------------------------------------------------------

	//属性对象,所有在本类中创建，并允许外部访问的对象在此声明并提供get/set方法-----------------------------------

	//引用对象,所有在外部创建并传入使用的对象在此声明并提供set方法-----------------------------------------------

	//内部方法,所有仅在本类或派生类中使用的函数在此定义为protected方法-------------------------------------------

	//公共方法,所有可提供外部使用的函数在此定义为public方法------------------------------------------------------
	
	//接口实现,所有实现接口函数的实现在此定义--------------------------------------------------------------------

	//事件处理,所有重载派生类的事件类方法(一般为on...ed)在此定义-------------------------------------------------

	//事件定义,所有在本类中定义并调用，由派生类实现或重载的事件类方法(一般为on...ed)在此定义---------------------

	//构造函数,所有构造函数在此定义------------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -4217710643711672017L;	
	
	public ArrayFieldImpl()
	{

		//请在此加入初始化代码,内部对象和属性对象负责创建或赋初值,引用对象初始化为null，初始化完成后在设置各对象之间的关系
		super();
	}


	public ArrayFieldImpl(String strFieldName)
	{

		//请在此加入初始化代码,内部对象和属性对象负责创建或赋初值,引用对象初始化为null，初始化完成后在设置各对象之间的关系
		super(strFieldName);
	}
	
	protected ByteBuffer strArr2Bytes(String[] strsValue,int DataType){
		int[] dataLen=new int[strsValue.length];//每个字符串长度
		byte[][] content=new byte[strsValue.length][];//内容字节
		byte[] value=null;
		int dataTotalLen=0;
		for(int i=0;i<strsValue.length;i++)
		{			
			value=strsValue[i].getBytes(Charset.forName("UTF-8"));//faster in Java 7 & 8,slow in java6
			content[i]=value;           //内容数据 
			dataLen[i]=value.length;  //内容数据的长度
			dataTotalLen=dataTotalLen+value.length;//所有内容数据的长度
		}
		
		int len=1+2+strsValue.length*4+dataTotalLen;//字段类型所占字节+数组个数所占字节+数据长度所占字节+数据所占字节
		ByteBuffer buffer=ByteBuffer.allocate(len);
		buffer.put((byte)DataType);
		buffer.putShort((short)strsValue.length);
		for(int i=0;i<strsValue.length;i++){
			buffer.putInt(dataLen[i]);
			buffer.put(content[i]);
		}
		buffer.flip();	
		return buffer;
	}
}
