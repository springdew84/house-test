package com.cassey.house.algorithm;

/**
 * 数字的字符串实现加法
 * 如输入"123"+"34"="157"
 */
public class SumStringNumber {
    public static void main(String[] args) {
        String str1 = "4230";
        String str2 = "496";

        //System.out.println(sum1(str1, str2));
        System.out.println(sum2(str1, str2));
    }

    /**
     * 面试题
     *
     * @param str1
     * @param str2
     * @return
     */
    private static String sum1(String str1, String str2) {
        char[] arr1 = new char[str1.length()];

        int j = 0;
        for (int i = str1.toCharArray().length - 1; i >= 0; i--) {
            arr1[j] = str1.toCharArray()[i];
            j++;
        }

        char[] arr2 = str2.toCharArray();

        j = 0;
        for (int i = str2.toCharArray().length - 1; i >= 0; i--) {
            arr2[j] = str2.toCharArray()[i];
            j++;
        }

        int length = Math.max(arr1.length, arr2.length) + 1;
        int[] sum = new int[length];
        int index = 0;

        while (index < length - 1) {
            if (index > arr1.length - 1 && index <= arr2.length - 1) {
                sum[index] += arr2[index] - 48;
                index++;
                continue;
            }

            if (index > arr2.length - 1 && index <= arr1.length - 1) {
                sum[index] += arr1[index] - 48;
                index++;
                continue;
            }

            int tmp = (arr1[index] - 48) + (arr2[index] - 48);
            if (tmp >= 10) {
                sum[index] += tmp - 10;
                sum[index + 1] = 1;
            } else {
                sum[index] += tmp;
            }
            index++;
        }

        StringBuilder sb = new StringBuilder();

        for (int k = sum.length - 1; k >= 0; k--) {
            if (k == length - 1 && sum[k] == 0) {
                continue;
            }
            sb.append(sum[k]);
        }
        return sb.toString();
    }

    /**
     * 新想法
     *
     * @param str1
     * @param str2
     * @return
     */
    private static String sum2(String str1, String str2) {
        int[] arr1 = new int[str1.length()];
        int[] arr2 = new int[str2.length()];

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = str1.charAt(i) - 48;
        }

        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = str2.charAt(i) - 48;
        }

        int length = Math.max(arr1.length, arr2.length) + 1;
        int[] sum = new int[length];
        int index = 0;
        int indexStr1 = arr1.length - 1;
        int indexStr2 = arr2.length - 1;

        while (index < length - 1 && indexStr1 >= 0 && indexStr2 >= 0) {
            int tmp = arr1[indexStr1--] + arr2[indexStr2--];
            if (tmp >= 10) {
                sum[index] += tmp - 10;
                sum[index + 1] = 1;
            } else {
                sum[index] += tmp;
            }
            index++;
        }

        if (indexStr1 >= 0) {
            while (index < length - 1) {
                sum[index++] += arr1[indexStr1--];
            }
        } else if (indexStr2 >= 0) {
            while (index < length - 1) {
                sum[index++] += arr2[indexStr1--];
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int k = sum.length - 1; k >= 0; k--) {
            if (k == length - 1 && sum[k] == 0) {
                continue;
            }
            sb.append(sum[k]);
        }
        return sb.toString();
    }
}
