package com.cassey.house.thread;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDataFormat类是一个非线程安全的类
 * 通过单实例方法new出来的Thead对象，共享MyThead中的成员变量，
 * 成员变量就涉及线程安全问题
 * 可以在成员变量前边加volatile或是通过ThreadLocal线程变量方式使用
 */
public class SimpleDataFormatTest {

    public static void main(String[] args){
        MyThread01 myThread = new MyThread01();
        //myThread.setSimpleDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        for(int i=0;i<10;i++){
            new Thread(myThread).start();
        }
    }

}

class MyThread01 implements Runnable {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    private final static ThreadLocal<DateFormat> sdfLocal = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @Override
    public void run() {
        while (true){
            try {
                sdf.parse("2017-12-26 18:55:19");
                System.out.println(Thread.currentThread().getName() + " : "
                        + sdf.parse("2017-12-26 18:55:19"));
                //this.join(2000);
            } catch (ParseException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void setSimpleDateFormat(SimpleDateFormat sdf){
        this.sdf = sdf;
    }
}

