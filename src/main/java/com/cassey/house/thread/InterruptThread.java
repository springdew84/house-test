package com.cassey.house.thread;

/**
 * 线程运行状态
 * 1、新建状态(New)：新创建了一个线程对象。
2、就绪状态(Runnable)：线程对象创建后，其他线程调用了该对象的start()方法。
该状态的线程位于“可运行线程池”中，变得可运行，只等待获取CPU的使用权。
即在就绪状态的进程除CPU之外，其它的运行所需资源都已全部获得。
3、运行状态(Running)：就绪状态的线程获取了CPU，执行程序代码。
4、阻塞状态(Blocked)：阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。
直到线程进入就绪状态，才有机会转到运行状态。
阻塞的情况分三种：
(1)、等待阻塞：运行的线程执行wait()方法，该线程会释放占用的所有资源，
JVM会把该线程放入“等待池”中。进入这个状态后，是不能自动唤醒的，
必须依靠其他线程调用notify()或notifyAll()方法才能被唤醒，
(2)、同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，
则JVM会把该线程放入“锁池”中。
(3)、其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，
JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、
或者I/O处理完毕时，线程重新转入就绪状态。
5、死亡状态(Dead)：线程执行完了或者因异常退出了run()方法，该线程结束生命周期
 *
 */
class MyThread extends Thread {
 
 public MyThread(String name) {
     super(name);
 }

 @Override
 public void run() {
     try {  
         int i=0;
         while (!isInterrupted()) {
             Thread.sleep(100); // 休眠100ms
             i++;
             System.out.println(Thread.currentThread().getName()+" ("+this.getState()+") loop " + i);  
         }
     } catch (InterruptedException e) {  
         System.out.println(Thread.currentThread().getName() +" ("+this.getState()+") catch InterruptedException.");  
     }
 }
}

public class InterruptThread {

 public static void main(String[] args) {  
     try {  
         Thread t1 = new MyThread("t1");  // 新建“线程t1”
         System.out.println(t1.getName() +" ("+t1.getState()+") is new.");  

         t1.start();                      // 启动“线程t1”
         System.out.println(t1.getName() +" ("+t1.getState()+") is started.");  

         // 主线程休眠300ms，然后主线程给t1发“中断”指令。
         Thread.sleep(300);
         t1.interrupt();
         System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted.");

         // 主线程休眠300ms，然后查看t1的状态。
         Thread.sleep(300);
         System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted now.");
         
     } catch (InterruptedException e) {  
         e.printStackTrace();
     }
 } 
}
