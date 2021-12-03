package com.cassey.house.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockTest {
    //private static final ReentrantLock lock = new ReentrantLock();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static List<String> list = new ArrayList<>();
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    //lock.lock();
                    lock.writeLock().lock();
                    System.out.println("thread-" + Thread.currentThread().getName() + " get the lock");
                    for(int j = 0; j < 10000; j++) {
                        list.add("thread-" + Thread.currentThread().getName() + ",1 element:" + j);
                        //System.out.println("thread-" + Thread.currentThread().getName() + " 1 add " + j);
                    }

                    for(int j = 0; j < 10000; j++) {
                        list.add("thread-" + Thread.currentThread().getName() + ",2 element:" + j);
                        //System.out.println("thread-" + Thread.currentThread().getName() + " 2 add " + j);
                    }

                    for(int j = 0; j < 10000; j++) {
                        list.add("thread-" + Thread.currentThread().getName() + ",3 element:" + j);
                        //System.out.println("thread-" + Thread.currentThread().getName() + " 3 add " + j);
                    }
                } finally {
                    //lock.unlock();
                    lock.writeLock().unlock();
                }
            });
            thread.setName("thread-" + i);
            thread.start();
        }


        Thread countThread = new Thread(() -> {
            try {
                for(int i = 0; i < 1000; i++) {
                    lock.readLock().lock();
                    try {
                        Thread.sleep(1);
                        System.out.println(i + "->list size:" + list.size());
                    }
                    finally {
                        lock.readLock().unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        countThread.start();

        try {
            //countThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("cost time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
