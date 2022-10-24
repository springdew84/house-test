package com.cassey.house.thread;

/**
 * 两个线程交替输出1到100的数字
 * 使用synchronized关键字
 */
public class PrintAlternateNum {
    public static void main(String[] args) {
        PrintThread printThread = new PrintThread(1);
        Thread thread1 = new Thread(printThread, "A");
        Thread thread2 = new Thread(printThread, "B");
        thread1.start();
        thread2.start();
    }
}

class PrintThread implements Runnable {
    int index;

    public PrintThread(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        while (index <= 100) {
            synchronized (this) {
                try {
                    System.out.println("thread-" + Thread.currentThread().getName() + ":" + index++);
                    this.notify();
                    if(index <= 100) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
