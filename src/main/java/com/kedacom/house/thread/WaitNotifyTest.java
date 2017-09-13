package com.kedacom.house.thread;

class MyThread4 extends Thread{  
 public MyThread4(String name) {
     super(name);
 }

 public void run(){
     try {
		 while (true) {
	    	 synchronized(this){
	             System.out.println("->" + Thread.currentThread().getName() +" 11"); 
	             Thread.sleep(1000);
	             System.out.println("->" + Thread.currentThread().getName() +" 12"); 
	             this.notifyAll();
	             System.out.println("->" + Thread.currentThread().getName() +" 13"); 
	    	 } 
         }
     } catch (Exception e) {
    	 e.printStackTrace();
   }
 } 
}

/**
 * 
 * @author chunyang.zhao
 *
 */
public class WaitNotifyTest {  
 public static void main(String[] args) {  
	 try{
		 System.out.println("->" + Thread.currentThread().getName() +" 01");
		 
	     Thread t1=new MyThread4("t1");
	     
	     synchronized(t1){
		     System.out.println("->" + Thread.currentThread().getName() +" 02");
		     t1.start();// 启动t1
		     System.out.println("->" + Thread.currentThread().getName() +" 03");
		     
		     //主线程等待t1通过notify()唤醒 或 notifyAll()唤醒
		     //或超过5000ms延时,然后才被唤醒
		     t1.wait(5*1000);
		     System.out.println("->" + Thread.currentThread().getName() +" 04");
	     }
	 } catch(Exception e){
		 e.printStackTrace();
	 }
     
 }  
}