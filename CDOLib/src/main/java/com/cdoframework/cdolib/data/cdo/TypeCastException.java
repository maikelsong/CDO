package com.cdoframework.cdolib.data.cdo;

/**
 * @author KenelLiu
 */
public class TypeCastException extends RuntimeException
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
	private static final long serialVersionUID = -8534756017112816379L;
	public TypeCastException()
	{
		super();
	}
	public TypeCastException(String expression)
	{
		super(expression);
	}
	public TypeCastException(String strFieldName,Class clazz)
	{
		super("strFieldName is not type of "+clazz.getName());
	}

}
