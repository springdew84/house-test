package com.cassey.house.thread;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch线程等待计数器的使用
 * 同步辅助类，在完成一组正在其他线程中执行的操作之前，
 * 它允许一个或多个线程一直等待
 */
public class CountDownLatchTest {

    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    public static void main(String[] args) {

        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);

            // 新建5个任务
            for(int i=0; i<LATCH_SIZE; i++)
                new InnerThread().start();

            System.out.println("main await begin");
            
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            }
        }
    }
}