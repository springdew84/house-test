package com.cassey.house.classloader;

import java.net.URL;

/**
 * AppClassLoader-应用类加载器
 *    又称为系统类加载器,负责在JVM启动时,加载来自在命令java中的classpath或者java.class.path系统属性或者CLASSPATH操作系统属性所指定的JAR类包和类路径
 *
 * ExtClassLoader-扩展类加载器
 * 主要负责加载Java的扩展类库,默认加载JAVA_HOME/jre/lib/ext/目录下的所有jar包或者
 * 由java.ext.dirs系统属性指定的jar包.
 * 放入这个目录下的jar包对AppClassLoader加载器都是可见的(因为ExtClassLoader是AppClassLoader的父加载器,并且Java类加载器采用了委托机制).
 *
 * bootstrap classloader-启动类加载器
 * 它负责加载Java的核心类。在Sun的JVM中，
在执行java的命令中使用-Xbootclasspath选项或使用 - D选项
指定sun.boot.class.path系统属性值可以指定附加的类。
这个加载器的是非常特殊的，它实际上不是 java.lang.ClassLoader的子类，而是由JVM自身实现的。
可以通过执行以下代码来获得bootstrap classloader加载了那些核心类库
 * @author chunyang.zhao
 *
 */
public class ClassLoaderTest {

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		//同样效果：System.out.println("Class loader:" + ClassLoader.getSystemClassLoader());
		System.out.println("Class loader:" + ClassLoaderTest.class.getClassLoader().toString());//sun.misc.Launcher$AppClassLoader@18b4aac2
		System.out.println("AppClassLoader path:" + System.getProperty("java.class.path"));

		System.out.println("Class loader parent:" + ClassLoaderTest.class.getClassLoader().getParent());//sun.misc.Launcher$ExtClassLoader@2f2c9b19
		System.out.println("ExtClassLoader path:" + System.getProperty("java.ext.dirs"));

		System.out.println("Class loader parent parent:" + ClassLoaderTest.class.getClassLoader().getParent().getParent());//null

		//BootstrapClassLoader是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库，如：rt.jar、resources.jar、charsets.jar等，
		//可通过如下程序获得该类加载器从哪些地方加载了相关的jar或class文件
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(int i=0;i<urls.length;i++){
			System.out.println(urls[i].toExternalForm());
			//输出
/*			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/resources.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/rt.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/sunrsasign.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/jsse.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/jce.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/charsets.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/lib/jfr.jar
			file:/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/classes*/
		}

		//String类在rt.jar包中
		ClassLoader classLoader = String.class.getClassLoader();
		//输出null 可知由于BootstrapClassLoader对Java不可见,所以返回了null,
		// 我们也可以通过某一个类的加载器是否为null来作为判断该类是不是使用BootstrapClassLoader进行加载的依据.
		// 另外上面提到ExtClassLoader的父加载器返回的是null,那是否说明ExtClassLoader的父加载器是BootstrapClassLoader?
		System.out.println("String class loader: " + classLoader);

		//Bootstrap ClassLoader是由C/C++编写的，它本身是虚拟机的一部分，所以它并不是一个JAVA类，
		// 也就是无法在java代码中获取它的引用，JVM启动时通过Bootstrap类加载器加载rt.jar等核心jar包中的class文件，
		// 之前的int.class,String.class都是由它加载。然后呢，我们前面已经分析了，
		// JVM初始化sun.misc.Launcher并创建Extension ClassLoader和AppClassLoader实例。
		// 并将ExtClassLoader设置为AppClassLoader的父加载器。
		// Bootstrap没有父加载器，但是它却可以作用一个ClassLoader的父加载器。
		// 比如ExtClassLoader。这也可以解释之前通过ExtClassLoader的getParent方法获取为Null的现象
	}

}
