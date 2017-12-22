package com.cassey.house.thread;

/**
 * main为用户线程，t1也为用户线程
 *  t2是守护线程
 *  在“主线程main”和“子线程t1”
 *  (它们都是用户线程)执行完毕
 *  只剩t2这个守护线程的时候，JVM自动退出
在Java中有两类线程：用户线程 (User Thread)、守护线程 (Daemon Thread)。
所谓守护 线程，是指在程序运行的时候在后台提供一种通用服务的线程，
比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。
因此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。
反过来说，只要任何非守护线程还在运行，程序就不会终止。
用户线程和守护线程两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：
如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 
因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。
在使用守护线程时需要注意一下几点：
(1) thread.setDaemon(true)必须在thread.start()之前设置，
否则会跑出一个IllegalThreadStateException异常。
你不能把正在运行的常规线程设置为守护线程。
(2) 在Daemon线程中产生的新线程也是Daemon的。
(3) 守护线程应该永远不去访问固有资源，如文件、数据库，
因为它会在任何时候甚至在一个操作的中间发生中断
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
