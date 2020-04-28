package com.cassey.house.thread;

/**
 * Thread local原理
 * @author cassey
 */
public class ThreadLocalTest {

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

    private ThreadLocal<String> mySecret = new ThreadLocal<>();

    protected int next() {
        myCount.set(myCount.get() + 1);
        return myCount.get();
    }

    protected void remove() {
        myCount.remove();
    }

    protected void setSecret(String secret) {
        mySecret.set(secret);
    }

    protected String getSecret() {
        return mySecret.get();
    }

    public static void main(String[] args) {
        ThreadLocalTest test = new ThreadLocalTest();
        MyThread myThread1 = new MyThread(test, "zz1");
        MyThread myThread2 = new MyThread(test, "zz2");
        MyThread myThread3 = new MyThread(test, "zz3");

        myThread1.start();
        myThread2.start();
        myThread3.start();
    }

    private static class MyThread extends Thread {
        private ThreadLocalTest test;

        public MyThread(ThreadLocalTest test, String name) {
            this.test = test;
            this.setName(name);
        }

        @Override
        public void run() {
            Thread thread0 = Thread.currentThread();
            this.test.setSecret("secret-" + thread0.getName());

            for (int i = 0; i < 2; i++) {

                System.out.println(thread0.getName() + " secret:" + test.getSecret() + " myCount:" + test.next());
            }
            test.remove();

            String ss = ";";
        }
    }
}
