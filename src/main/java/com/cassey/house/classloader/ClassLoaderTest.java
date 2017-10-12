package com.cassey.house.classloader;

import java.net.URL;

import com.cassey.house.thread.SleepLockTest;

public class ClassLoaderTest {

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		
		System.out.println("Class loader:" + SleepLockTest.class.getClassLoader().toString());
		System.out.println("Class loader parent:" + SleepLockTest.class.getClassLoader().getParent().toString());
		 
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(int i=0;i<urls.length;i++){
			System.out.println(urls[i].toExternalForm());
		}
	}

}
