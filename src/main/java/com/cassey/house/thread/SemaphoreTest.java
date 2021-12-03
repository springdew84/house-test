package com.cassey.house.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，通过协调各个线程以保证合理地使用公共资源。
 *
 * Semaphore通过使用计数器来控制对共享资源的访问。 如果计数器大于0，则允许访问。 如果为0，则拒绝访问。
 *
 * 计数器所计数的是允许访问共享资源的许可。 因此，要访问资源，必须从信号量中授予线程许可。
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired");
                TimeUnit.MILLISECONDS.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }, "mThread1").start();

        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired");
                TimeUnit.MILLISECONDS.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }, "mThread2").start();

        try {
            boolean acquired = semaphore.tryAcquire(200, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + " is " + acquired);
            if(acquired) {
                semaphore.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
