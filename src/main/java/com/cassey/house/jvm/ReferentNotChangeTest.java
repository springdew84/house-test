package com.cassey.house.jvm;

import java.lang.reflect.Field;

public class ReferentNotChangeTest {
    public static void main(String[] args) {
//        String str = new String("abc");
//        ...
//        System.out.println(str);

        //以上代码...位置可以添加N⾏代码，但必须保证s引⽤的指向不变，最终将输出变成abcd
        String str = new String("abc");
        try {
            Field field = str.getClass().getDeclaredField("value");
            field.setAccessible(true);
            try {
                field.set(str, "abcd".toCharArray());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println(str);


    }
}
