package com.cassey.house.algorithm;

/**
 * N的阶乘
 */
public class Factorial {
    public static void main(String[] args) {
        //System.out.println("res:" + factorial(5));
        System.out.println("res:" + factorial2(5));
    }

    /**
     * 递归方式
     * @param n
     * @return
     */
    private static int factorial(int n) {
        if(n == 0 || n == 1) {
            return 1;
        }

        return n * factorial(n - 1);
    }

    /**
     * 循环方式
     * @param n
     * @return
     */
    private static int factorial2(int n) {
        int fac = 1;
        for(int i = 1; i <= n; i++) {
            fac = fac * i;
        }

        return fac;
    }
}
