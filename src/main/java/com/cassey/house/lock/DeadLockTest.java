package com.cassey.house.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 死锁例子
 * 输出：
 * 张三get chopstick 1
 * 李四get chopstick 2
 */
public class DeadLockTest {
    public static void main(String[] args) {
        Chopstick chopstick1 = new Chopstick(1);
        Chopstick chopstick2 = new Chopstick(2);

        //张三先拿筷子1
        new Thread(() -> {
            synchronized (chopstick1) {
                System.out.println(Thread.currentThread().getName() + "get chopstick " + chopstick1.getId());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (chopstick2) {
                    System.out.println(Thread.currentThread().getName() + "get chopstick " + chopstick2.getId());
                    System.out.println(Thread.currentThread().getName() + "开吃了");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "吃饱了");
                }
            }
        }, "张三").start();

        //李四先拿筷子2
        new Thread(() -> {
            synchronized (chopstick2) {
                System.out.println(Thread.currentThread().getName() + "get chopstick " + chopstick2.getId());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (chopstick1) {
                    System.out.println(Thread.currentThread().getName() + "get chopstick " + chopstick1.getId());
                    System.out.println(Thread.currentThread().getName() + "开吃");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "吃饱了");
                }
            }
        }, "李四").start();

        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单只筷子
     */
    static class Chopstick {
        private Integer id;

        public Chopstick(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}

/*************************************以下为线程栈**************/
//jstack 73605
//        2021-12-03 15:28:14
//        Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.172-b11 mixed mode):
//
//        "Attach Listener" #15 daemon prio=9 os_prio=31 tid=0x00007fb766011800 nid=0x4a0b waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "李四" #14 prio=5 os_prio=31 tid=0x00007fb76711a800 nid=0x5a03 waiting for monitor entry [0x00007000111ef000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//        at com.cassey.house.lock.DeadLockTest.lambda$main$1(DeadLockTest.java:43)
//        - waiting to lock <0x000000076af1d460> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        - locked <0x000000076af1d470> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        at com.cassey.house.lock.DeadLockTest$$Lambda$2/1449621165.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//
//        "张三" #13 prio=5 os_prio=31 tid=0x00007fb766010800 nid=0x5803 waiting for monitor entry [0x00007000110ec000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//        at com.cassey.house.lock.DeadLockTest.lambda$main$0(DeadLockTest.java:20)
//        - waiting to lock <0x000000076af1d470> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        - locked <0x000000076af1d460> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        at com.cassey.house.lock.DeadLockTest$$Lambda$1/2137589296.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//
//        "Service Thread" #12 daemon prio=9 os_prio=31 tid=0x00007fb7688c4000 nid=0x5603 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C1 CompilerThread3" #11 daemon prio=9 os_prio=31 tid=0x00007fb766808800 nid=0x5503 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C2 CompilerThread2" #10 daemon prio=9 os_prio=31 tid=0x00007fb76680d800 nid=0x3f03 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C2 CompilerThread1" #9 daemon prio=9 os_prio=31 tid=0x00007fb7688b3000 nid=0x4003 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "C2 CompilerThread0" #8 daemon prio=9 os_prio=31 tid=0x00007fb7698b3800 nid=0x4203 waiting on condition [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "JDWP Command Reader" #7 daemon prio=10 os_prio=31 tid=0x00007fb767808800 nid=0x3b03 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "JDWP Event Helper Thread" #6 daemon prio=10 os_prio=31 tid=0x00007fb769019800 nid=0x3a03 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "JDWP Transport Listener: dt_socket" #5 daemon prio=10 os_prio=31 tid=0x00007fb769019000 nid=0x4407 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007fb76703a800 nid=0x4503 runnable [0x0000000000000000]
//        java.lang.Thread.State: RUNNABLE
//
//        "Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fb769018000 nid=0x3103 in Object.wait() [0x00007000104c5000]
//        java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
//        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
//        - locked <0x000000076ab08ed0> (a java.lang.ref.ReferenceQueue$Lock)
//        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
//        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
//
//        "Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fb769827800 nid=0x4e03 in Object.wait() [0x00007000103c2000]
//        java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
//        at java.lang.Object.wait(Object.java:502)
//        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
//        - locked <0x000000076ab06bf8> (a java.lang.ref.Reference$Lock)
//        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
//
//        "main" #1 prio=5 os_prio=31 tid=0x00007fb769809000 nid=0x1003 waiting on condition [0x000070000f9a4000]
//        java.lang.Thread.State: WAITING (parking)
//        at sun.misc.Unsafe.park(Native Method)
//        - parking to wait for  <0x000000076b2cc678> (a java.util.concurrent.CountDownLatch$Sync)
//        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:997)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1304)
//        at java.util.concurrent.CountDownLatch.await(CountDownLatch.java:231)
//        at com.cassey.house.lock.DeadLockTest.main(DeadLockTest.java:56)
//
//        "VM Thread" os_prio=31 tid=0x00007fb769821000 nid=0x5003 runnable
//
//        "GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007fb76900c800 nid=0x1c07 runnable
//
//        "GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007fb769015800 nid=0x2103 runnable
//
//        "GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007fb769016000 nid=0x1f03 runnable
//
//        "GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007fb769016800 nid=0x2a03 runnable
//
//        "GC task thread#4 (ParallelGC)" os_prio=31 tid=0x00007fb769017800 nid=0x2c03 runnable
//
//        "GC task thread#5 (ParallelGC)" os_prio=31 tid=0x00007fb767009000 nid=0x5303 runnable
//
//        "GC task thread#6 (ParallelGC)" os_prio=31 tid=0x00007fb767009800 nid=0x5203 runnable
//
//        "GC task thread#7 (ParallelGC)" os_prio=31 tid=0x00007fb766809800 nid=0x2f03 runnable
//
//        "VM Periodic Task Thread" os_prio=31 tid=0x00007fb76c02a800 nid=0xa803 waiting on condition
//
//        JNI global references: 2378
//
//
//        Found one Java-level deadlock:
//        =============================
//        "李四":
//        waiting to lock monitor 0x00007fb76982d7f8 (object 0x000000076af1d460, a com.cassey.house.lock.DeadLockTest$Chopstick),
//        which is held by "张三"
//        "张三":
//        waiting to lock monitor 0x00007fb76982d958 (object 0x000000076af1d470, a com.cassey.house.lock.DeadLockTest$Chopstick),
//        which is held by "李四"
//
//        Java stack information for the threads listed above:
//        ===================================================
//        "李四":
//        at com.cassey.house.lock.DeadLockTest.lambda$main$1(DeadLockTest.java:43)
//        - waiting to lock <0x000000076af1d460> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        - locked <0x000000076af1d470> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        at com.cassey.house.lock.DeadLockTest$$Lambda$2/1449621165.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//        "张三":
//        at com.cassey.house.lock.DeadLockTest.lambda$main$0(DeadLockTest.java:20)
//        - waiting to lock <0x000000076af1d470> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        - locked <0x000000076af1d460> (a com.cassey.house.lock.DeadLockTest$Chopstick)
//        at com.cassey.house.lock.DeadLockTest$$Lambda$1/2137589296.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//
//        Found 1 deadlock.
