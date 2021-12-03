package com.cassey.house.jvm;

import sun.misc.Unsafe;
import sun.plugin.JavaRunTime;

import java.lang.reflect.Field;

public class UnsafeTest {
    //这种方式因为代码会判断类加载器是不是null,如果不是null就会抛SecurityException
    //private Unsafe unsafe = Unsafe.getUnsafe();

    //通常通过反射机制获取unsafe实例
    static Unsafe unsafe;

    static {
        try {
            Class<?> clazz = Unsafe.class;
            Field feild = clazz.getDeclaredField("theUnsafe");
            feild.setAccessible(true);
            unsafe = (Unsafe)feild.get(clazz);

            //unsafe.compareAndSwapInt()
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        try {
            //test.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
