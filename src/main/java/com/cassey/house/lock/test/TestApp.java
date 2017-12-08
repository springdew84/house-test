package com.cassey.house.lock.test;

/**
 * 测试入口
 * @author chunyang.zhao
 *
 */
class ThreadA extends Thread {
    private ServiceTest service;

    public ThreadA(ServiceTest service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.seckill();
    }
}

public class TestApp {
    public static void main(String[] args) {
        ServiceTest service = new ServiceTest();
        for (int i = 0; i < 200; i++) {
            ThreadA threadA = new ThreadA(service);
            threadA.start();
        }
    }
}
