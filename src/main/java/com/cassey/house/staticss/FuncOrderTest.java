package com.cassey.house.staticss;

/**
 * 由此可见，实例化子类的时候，若此类未被加载过，
 * 首先加载是父类的类对象，然后加载子类的类对象，
 * 接着实例化父类，最后实例化子类，若此类被加载过，
 * 不再加载父类和子类的类对象。接下来是加载顺序，
 * 当加载类对象时，首先执行静态属性初始化，然后执行静态块，
 * 当实例化对象时，首先执行实例块，然后执行构造方法，
 * 至于各静态块、实例块之间的执行顺序，是按代码的先后顺序
 * @author chunyang.zhao
 *
 */
class A {
	
	static{
        a = 10;
		//System.out.println("a：" + a);
	}
	static int a = 8;
	
	static {
		System.out.println("A的静态块");
		System.out.println("a：" + a);
	}
	
	private static String staticStr = getStaticStr();

	private String str = getStr();

	{
		System.out.println("A的实例块");
	}

	public A() {
		System.out.println("A的构造方法");
	}

	private static String getStaticStr() {
		System.out.println("A的静态属性初始化");
		return null;
	}

	private String getStr() {
		System.out.println("A的实例属性初始化");
		return null;
	}
}

public class FuncOrderTest extends A {
	
	static {
		System.out.println("B的静态块");
	}
	
	private static String staticStr = getStaticStr();

	private String str = getStr();

	{
		System.out.println("B的实例块");
	}

	public FuncOrderTest() {
		System.out.println("B的构造方法");
	}

	private static String getStaticStr() {
		System.out.println("B的静态属性初始化");
		return null;
	}

	private String getStr() {
		System.out.println("B的实例属性初始化");
		return null;
	}

	public static void main(String[] args) {
		new FuncOrderTest();
		//会有差别
		//new FuncOrderTest();
	}
}