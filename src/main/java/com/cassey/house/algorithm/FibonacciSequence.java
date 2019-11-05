package com.cassey.house.algorithm;

/**
 * 斐波那契数列（Fibonacci sequence）的定义：
 * 斐波那契数列指的是这样一个数列 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144,
 * 233，377，610，987，1597，2584，4181，6765，10946，17711，28657，46368........，
 * 这个数列从第3项开始，每一项都等于前两项之和。
 * <p>
 * 斐波那契数列又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入，
 * 故又称为“兔子数列”。在数学上，斐波纳契数列以如下被以递归的方法定义：
 * F(0)=0，
 * F(1)=1,
 * F(n)=F(n-1)+F(n-2)（n>=2，n∈N*）
 */
public class FibonacciSequence {
    public static void main(String[] args) {
        int count = 47;
        printFib1(count);
        printFib2(count);
        printFib3(count);
        printFib4(count);
    }

    /**
     * 数组法
     *
     * @param count
     */
    public static int[] getFibonacciArray(int count) {
        return printFib3(count);
    }

    /**
     * 直接赋值法
     *
     * @param count
     */
    private static void printFib1(int count) {
        //定义第一个加数a，初始值为1；定义第二个加数b，初始值为1；定义两个加数之和为c，初始值为0
        long a = 1;
        long b = 1;
        long c = 0;
        //首先在控制台打印出数列中第一个数和第二个数的值
        System.out.println("--------------------------printFib1---------------------");
        System.out.println("1:" + a);
        System.out.println("2:" + b);

        //建立一个for循环，用于循环输出数列中第三位至第十位的数字
        for (int i = 3; i <= count; i++) {
            //第三个数即为c，a+b等于c的值
            c = a + b;
            //将第一个加数a赋值为数列中的第二个数b的值
            a = b;
            //将第二个加数b赋值为数列中的第三个数c的值
            b = c;
            //在第二次循环打印时，将打印数列中的第四个数为：b + c = b + (a + b)
            System.out.println(i + ":" + c);
        }
    }

    /**
     * 直接赋值法-简化版
     *
     * @param count
     */
    private static void printFib2(int count) {
        long a = 1;
        long b = 1;
        int index = 0;
        int countReal = count / 2 + 2;

        System.out.println("--------------------------printFib2---------------------");

        for (int i = 1; i < countReal; i++) {
            index++;
            if (index > count) break;
            System.out.println(index + ":" + a);

            index++;
            if (index > count) break;
            System.out.println(index + ":" + b);

            a = a + b;
            b = a + b;
        }
    }

    /**
     * 数组实现法
     * @param count
     */
    private static int[] printFib3(int count) {
        int[] arr = new int[count];
        arr[0] = 1;
        arr[1] = 1;
        System.out.println("--------------------------printFib3---------------------");

        for (int i = 0; i < arr.length; i++) {
            if(i > 1) {
                arr[i] = arr[i-2] + arr[i-1];
            }
            //System.out.println((i+1) + ":" + arr[i]);
        }

        return arr;
    }

    /**
     * 递归法实现
     * @param count
     */
    private static void printFib4(int count) {
        System.out.println("--------------------------printFib4--------------------");
        for(int i = 0; i <= count; i++) {
            System.out.println((i+1) + ":" + fib(i));
        }
    }

    /**
     * 递归
     * @param count
     */
    private static long fib(int count) {
        if(count < 2L) {
            return 1L;
        } else {
            return fib(count - 2) + fib(count - 1);
        }
    }
}
