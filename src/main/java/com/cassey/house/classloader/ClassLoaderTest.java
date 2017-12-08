package com.cassey.house.classloader;

import java.net.URL;

/**
 * bootstrap classloader
 * AppClassLoader
 * ExtClassLoader
 * 
 * bootstrap classloader －引导（也称为原始）类加载器，
 * 它负责加载Java的核心类。在Sun的JVM中，
在执行java的命令中使用-Xbootclasspath选项或使用 - D选项
指定sun.boot.class.path系统属性值可以指定附加的类。
这个加载器的是非常特殊的，
它实际上不是 java.lang.ClassLoader的子类，
而是由JVM自身实现的。
可以通过执行以下代码来获得bootstrap classloader加载了那些核心类库
 * @author chunyang.zhao
 *
 */
public class ClassLoaderTest {

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		
		System.out.println("Class loader:" + ClassLoaderTest.class.getClassLoader().toString());
		System.out.println("Class loader parent:" + ClassLoaderTest.class.getClassLoader().getParent().toString());
		 
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(int i=0;i<urls.length;i++){
			System.out.println(urls[i].toExternalForm());
		}
	}

}
