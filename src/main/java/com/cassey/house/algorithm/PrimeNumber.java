package com.cassey.house.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 素数(质数)
 * 质数又称素数。
 * 指在一个大于1的自然数中，除了1和此整数自身外，没法被其他自然数整除的数。
 * 换句话说，只有两个正因数（1和自己）的自然数即为素数。
 * 比1大但不是素数的数称为合数。
 * 1和0既非素数也非合数。
 * 合数是由若干个质数相乘而得到的。所以，质数是合数的基础，没有质数就没有合数。
 */
public class PrimeNumber {

    public static void main(String[] args) {
        float[] aa = new float[2];
        for(float i : aa) {
            System.out.println(":" + i);
        }

        printPrimeNumber(1000);

        System.out.println(" ");
        System.out.println("Math.sqrt(9)=" + Math.sqrt(9));
    }

    /**
     * 打印 2 - 1000 当中的素数与非素数
     * 素数定义: 在大于1的自然数中，除了1和它本身以外不再有其他因数
     * 常规方式——对正整数n，如果用2到√N（根号）之间的所有整数去除，均无法整除，则n为质数
     *
     * @param num
     */
    private static void printPrimeNumber(int num) {
        boolean[] isPrimes = new boolean[num + 1];

        for (int i = 2; i < isPrimes.length; i++) {
            isPrimes[i] = true;
        }

        for(int i = 3; i <= num; i++) {
            for(int j = 2; j <= Math.sqrt(i); j++) {
                if(i % j == 0) {
                    isPrimes[i] = false;
                    break;
                }
            }
        }

        System.out.print("质数有: ");
        for (int i = 2; i < isPrimes.length; i++) {
            if (isPrimes[i]) {
                System.out.print(i + " ");
            }
        }

        System.out.println(" ");
        System.out.print("非质数有: ");
        for (int i = 2; i < isPrimes.length; i++) {
            if (!isPrimes[i]) {
                System.out.print(i + " ");
            }
        }
    }
}
