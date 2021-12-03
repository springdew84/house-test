package com.cassey.house.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FinalAndVolatileTest {
    //private final List<String> strList = new ArrayList<>();
    private volatile List<String> strList = new ArrayList<>();
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        FinalAndVolatileTest finalAndVolatileTest = new FinalAndVolatileTest();

        new Thread(() -> {
            try {
                Thread.sleep(110);
                finalAndVolatileTest.strList.add("test");
                System.out.println(System.currentTimeMillis() + "-strList size:" + finalAndVolatileTest.strList.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            long start = System.currentTimeMillis();
            while (finalAndVolatileTest.strList.size() == 0) {
                try {
                    //Thread.sleep(100);
                    System.out.println(System.currentTimeMillis() + "-strList size:" + finalAndVolatileTest.strList.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("strList size changed! cost:" + (System.currentTimeMillis() - start));
            countDownLatch.countDown();
        }).start();

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        int i = 0;
//        while (i < 1000000000) {
//            i++;
//        }

        try {
            countDownLatch.await();
            System.out.println("-strList size:" + finalAndVolatileTest.strList.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        try {
            condition.await();
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
