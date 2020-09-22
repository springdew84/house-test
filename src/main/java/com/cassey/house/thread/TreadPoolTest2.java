package com.cassey.house.thread;

import java.util.concurrent.*;

public class TreadPoolTest2 {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(40);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                10, 3,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 40; i++) {
            MyThreadTest2 thread = new MyThreadTest2(i);
            executor.submit(thread);
        }


        executor.shutdownNow();
    }
}
