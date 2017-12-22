package com.cassey.house.thread;

/**
 * http://www.cnblogs.com/skywang12345/p/java_threads_category.html
 * 
 * @author chunyang.zhao
 *
 */
class MyThread2 extends Thread{  
    public MyThread2(String name) {
        super(name);
    }

    public void run(){
        for (int i=0; i<500; i++) {
            System.out.println(Thread.currentThread().getName()
                    +"("+Thread.currentThread().getPriority()+ ")"
                    +", loop "+i);
        }
    }
}; 

public class PriorityTest {  
    public static void main(String[] args) {  

        System.out.println(Thread.currentThread().getName()
                +"("+Thread.currentThread().getPriority()+ ")");

        Thread t1=new MyThread2("t1");    // 新建t1
        Thread t2=new MyThread2("t2");    // 新建t2
        t1.setPriority(1);                // 设置t1的优先级为1
        t2.setPriority(10);                // 设置t2的优先级为10
        t1.start();                        // 启动t1
        t2.start();                        // 启动t2
    }  
}