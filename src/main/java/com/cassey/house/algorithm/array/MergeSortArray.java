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

        while (index < merge.length && arr1Index < arr1.length && arr2Index < arr2.length) {
            if (arr1[arr1Index] < arr2[arr2Index]) {
                merge[index++] = arr1[arr1Index++];
            } else {
                merge[index++] = arr2[arr2Index++];
            }
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

    /**
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * <p>
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * <p>
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     *
     *
     * 解题思路：
     * 使用双指针，从后往前遍历。首先定义三个指针，用num1的最后一个元素i与num2的最后一个元素j比较，在用一个指针指向混合数组的末尾 k；
     * 如果num1[i] > num2[j],把num1的数放到混合数组的末尾,同时 i 和 k 要自减1；
     * 如果num1[i] < num2[j],把num2的数放到混合数组的末尾,同时 j 和 k 要自减1 ；
     * 循环结束后还会有可能i或j大于0，如果j大于0，继续循环，将num2的数拷贝进num1；
     * 如果i大于0，就不用管了，因为混合数组本身就是num1中。
     */
    private static void merge3(int[] nums1, int m, int[] nums2, int n) {
        int index1 = nums1.length - 1;
        int index2 = nums2.length - 1;
        int index = m + n - 1;

        while (index1 >= 0 && index2 >= 0) {
            nums1[index--] = nums1[index1] > nums2[index2] ? nums1[index1--] : nums2[index2--];
        }

        System.arraycopy(nums2, 0, nums1, 0, index2 + 1);
    }
}


