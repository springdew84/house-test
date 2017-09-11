package com.kedacom.house.thread;

class MyThread1 extends Thread{  
 public MyThread1(String name) {
     super(name);
 }

 public void run(){
     try {
         for (int i=0; i<5; i++) {
             Thread.sleep(3);
             System.out.println(this.getName() +"(isDaemon="+this.isDaemon()+ ")" +", loop "+i);
         }
     } catch (InterruptedException e) {
     }
 } 
}; 

class MyDaemon extends Thread{  
 public MyDaemon(String name) {
     super(name);
 }

 public void run(){
     try {
         for (int i=0; i<100; i++) {
             Thread.sleep(1);
             System.out.println(this.getName() +"(isDaemon="+this.isDaemon()+ ")" +", loop "+i);
         }
     } catch (InterruptedException e) {
     }
 } 
}

/**
 * main为用户线程，t1也为用户线程
 *  t2是守护线程
 *  在“主线程main”和“子线程t1”(它们都是用户线程)执行完毕
 *  只剩t2这个守护线程的时候，JVM自动退出
 * @author chunyang.zhao
 *
 */
public class DaemonTest {  
 public static void main(String[] args) {  

     System.out.println(Thread.currentThread().getName()
             +"(isDaemon="+Thread.currentThread().isDaemon()+ ")");

     Thread t1=new MyThread1("t1");    // 新建t1
     Thread t2=new MyDaemon("t2");    // 新建t2
     t2.setDaemon(true);                // 设置t2为守护线程
     t1.start();                        // 启动t1
     t2.start();                        // 启动t2
 }  
}
