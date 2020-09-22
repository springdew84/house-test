package com.cassey.house.thread;

import java.util.concurrent.CountDownLatch;

class MyThreadTest2 implements Runnable {
    Integer i;

    public MyThreadTest2( Integer i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2 * 1000);
            //latch.countDown();
            //latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("threadName:" + Thread.currentThread().getName() + ", i:" + i);
    }
}