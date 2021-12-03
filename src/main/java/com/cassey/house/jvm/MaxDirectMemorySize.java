package com.cassey.house.jvm;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * ByteBuffer有两种:
 * heap ByteBuffer -> -XX:Xmx
 * 1.一种是heap ByteBuffer,该类对象分配在JVM的堆内存里面，直接由Java虚拟机负责垃圾回收，
 * direct ByteBuffer -> -XX:MaxDirectMemorySize
 * 2.一种是direct ByteBuffer是通过jni在虚拟机外内存中分配的。
 * 通过jmap无法查看该快内存的使用情况。只能通过top来看它的内存使用情况。
 * JVM堆内存大小可以通过-Xmx来设置，同样的direct ByteBuffer可以通过
 * -XX:MaxDirectMemorySize来设置，此参数的含义是当Direct ByteBuffer分配的堆外内存到达指定大小后，
 * 即触发Full GC。注意该值是有上限的，默认是64M，最大为sun.misc.VM.maxDirectMemory()，
 * 在程序中中可以获得-XX:MaxDirectMemorySize的设置的值
 *
 * @author chunyang.zhao
 */
public class MaxDirectMemorySize {
    @SuppressWarnings("restriction")
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        System.out.println("maxMemoryValue:" + sun.misc.VM.maxDirectMemory());


        System.out.println("================================");

        ByteBuffer buffer = ByteBuffer.allocateDirect(0);

        Class<?> c = Class.forName("java.nio.Bits");
        Field maxMemory = c.getDeclaredField("maxMemory");
        maxMemory.setAccessible(true);
        synchronized (c) {
            Long maxMemoryValue = (Long) maxMemory.get(c);
            System.out.println("maxMemoryValue:" + maxMemoryValue);
        }
    }
}
