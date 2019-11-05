package com.cassey.house.algorithm;

/**
 * 素数(质数)
 */
public class PrimeNumber {

    public static void main(String[] args) {
        printPrimeNumber(1000);

        System.out.println(Math.sqrt(9));
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
