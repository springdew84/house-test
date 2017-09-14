package com.kedacom.house.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Thread7 extends Thread {

	CountDownLatch latch;

	/**
	 * 
	 * @param latch
	 */
	public Thread7(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			latch.countDown();
			System.out.println(Thread.currentThread().getName() + " ...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class TreadPoolTest {

	public static void main(String[] args) {
		// 创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(15);

		CountDownLatch latch = new CountDownLatch(5);

		// 创建实现了Runnable的接口对象
		Thread thread1 = new Thread7(latch);
		Thread thread2 = new Thread7(latch);
		Thread thread3 = new Thread7(latch);
		Thread thread4 = new Thread7(latch);
		Thread thread5 = new Thread7(latch);

		// 将线程放入池中进行
		pool.execute(thread1);
		pool.execute(thread2);
		pool.execute(thread3);
		pool.execute(thread4);
		pool.execute(thread5);

		try {
			System.out.println(Thread.currentThread().getName() + " before");
			latch.await();
			System.out.println(Thread.currentThread().getName() + " after");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭线程池
			pool.shutdown();
		}
	}
}
