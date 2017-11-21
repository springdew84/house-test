package com.cassey.house.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

public class ExceptionStackTraceTest {
	
	/**
	 * 使用commons.lang包中的ExceptionUtils工具类
	 * @param args
	 */
	public static void main(String[] args){
		try{
			int x = 1;
			int y = 0;
			//int z = x/y;
		} catch(Exception e){
			String trace = ExceptionUtils.getFullStackTrace(e);
			System.out.println(trace);
			
			e.printStackTrace();
		}
	}
}
