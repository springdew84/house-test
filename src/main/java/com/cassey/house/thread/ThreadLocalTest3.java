package com.cassey.house.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Thread local原理
 * 只要ThreadLocal没有任何其他强引用，那么经过gc后就会立马被回收
 * @author cassey
 */
public class ThreadLocalTest3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(
                () -> {
                    // 创建一个ThreadLocal对象
                    ThreadLocal<String> threadLocal = new ThreadLocal<>();
                    threadLocal.set("hello world");
                    // 让ThreadLocal对象没有任何强引用
                    threadLocal = null;
                    System.gc();
                    // 获取当前线程
                    Thread currentThread = Thread.currentThread();
                    // 可以在这一行打断点，查看currentThread里面的threadLocals对象
                    // gc后referent属性值为null，说明此时Entry中的key已经被回收了，但是value依然存在。
                    countDownLatch.countDown();
                })
                .start();
        countDownLatch.await();
    }
}
