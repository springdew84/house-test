package com.cassey.house.thread;

/**
 * Thread local原理
 * @author cassey
 */
public class ThreadLocalTest2 {

    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("1");
        MyThread myThread2 = new MyThread("2");
        MyThread myThread3 = new MyThread("3");

        myThread1.start();
        myThread2.start();
        myThread3.start();
    }

    static class MyThread extends Thread {
        private ThreadLocal<Integer> myCount = new ThreadLocal<Integer>() {

            /**
             * 实现初始化
             * @return
             */
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };

        public MyThread(String name) {
            super(name);
        }

        protected int next() {
            int v = myCount.get() + 1;
            myCount.set(v);
            return myCount.get();
        }

        @Override
        public void run() {

            for (int i = 0; i < 2; i++) {

                System.out.println("ThreadName:" + Thread.currentThread().getName() + ", myCount:" + this.next());
            }

            myCount.remove();

            String ss = ";";
        }
    }
}
