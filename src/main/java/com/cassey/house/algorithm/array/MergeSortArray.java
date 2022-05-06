package com.cassey.house.algorithm.array;

import java.util.Arrays;

/**
 * 合并两个有序（升序）数组，使合并后的数组任然有序
 */
public class MergeSortArray {
    public static void main(String[] args) {
        int[] arr2 = {1, 4, 6, 8, 10};
        int[] arr1 = {2, 3, 7, 9, 15};

        //int[] merge = merge1(arr1, arr2);
        int[] merge = merge2(arr2, arr1);
        System.out.println(Arrays.toString(merge));
    }

    /**
     * 版本1 面试题目
     *
     * @param arr1
     * @param arr2
     * @return
     */
    private static int[] merge1(int[] arr1, int[] arr2) {
        int[] merge = new int[arr1.length + arr2.length];
        int index = 0;
        int arr1Index = 0;
        int arr2Index = 0;

        while (index < merge.length) {
            if (arr1Index >= arr1.length) {
                merge[index] = arr2[arr2Index];
                arr2Index++;
                index++;
                continue;
            }

            if (arr2Index >= arr2.length) {
                merge[index] = arr1[arr1Index];
                arr1Index++;
                index++;
                continue;
            }

            if (arr1[arr1Index] < arr2[arr2Index]) {
                merge[index] = arr1[arr1Index];
                arr1Index++;
            } else {
                merge[index] = arr2[arr2Index];
                arr2Index++;
            }
            index++;
        }

        return merge;
    }

    /**
     * 版本2 新想法
     *
     * @param arr1
     * @param arr2
     * @return
     */
    private static int[] merge2(int[] arr1, int[] arr2) {
        int[] merge = new int[arr1.length + arr2.length];
        int index = 0;
        int arr1Index = 0;
        int arr2Index = 0;

        while (index < merge.length
                && arr1Index < arr1.length
                && arr2Index < arr2.length) {
            if (arr1[arr1Index] < arr2[arr2Index]) {
                merge[index] = arr1[arr1Index];
                arr1Index++;
            } else {
                merge[index] = arr2[arr2Index];
                arr2Index++;
            }
            index++;
        }

        //arr1有剩余
        if (arr1Index < arr1.length) {
            while (index < merge.length) {
                merge[index++] = arr1[arr1Index++];
            }
        } else if (arr2Index < arr2.length) {//arr2有剩余
            while (index < merge.length) {
                merge[index++] = arr2[arr2Index++];
            }
        }

        return merge;
    }
}


