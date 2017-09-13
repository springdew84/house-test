package com.kedacom.house.thread;

/**
 * SleepLockTest.java的源码
 * @author chunyang.zhao
 *
 */
public class SleepLockTest{ 

 private static Object obj = new Object();

 public static void main(String[] args){ 
     ThreadA t1 = new ThreadA("t1"); 
     ThreadA t2 = new ThreadA("t2"); 
     t1.start(); 
     t2.start();
 } 

 static class ThreadA extends Thread{
     public ThreadA(String name){ 
         super(name); 
     } 
     public void run(){ 
         //获取obj对象的同步锁
         synchronized (obj) {
    	 //实例对象
         //synchronized (this) {
             try {
                 for(int i=0; i <10; i++){ 
                     System.out.printf("%s: %d\n", this.getName(), i); 
                     // i能被4整除时，休眠100毫秒
                     if (i%4 == 0)
                         Thread.sleep(100);
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     } 
 } 
}
