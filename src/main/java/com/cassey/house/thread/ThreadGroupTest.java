package com.cassey.house.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程组
 *
 * 可以把线程归属到某一个线程组中，线程组中可以有线程对象，
 * 也可以有线程组，组中还可以用线程。
 * 这样的组织结构有点类似于树的形式。
 * 线程组的作用是，可以批量的管理线程或者线程组对象，有效地对线程或线程组对象进行组织。
 * 类SimpleDateFormat主要负责日期的转换与格式化，
 * 但在多线程的环境中，使用此类容易造成数据转换及处理的不准确，
 * 因为SimpleDateFromat类并不是线程安全的
 */
public class ThreadGroupTest {
    private static final Logger log = LoggerFactory.getLogger(ThreadGroupTest.class);

    public static void main(String[] args) {

        log.info("main函数所在的线程组，名字为:{}", Thread.currentThread().getThreadGroup().getName());
        log.info("main线程组的父线程组名字为:{}", Thread.currentThread().getThreadGroup().getParent().getName());
        log.info("main线程组的父线程组的父线程组的名字为:{}",
                Thread.currentThread().getThreadGroup().getParent().getParent());
    }
}
