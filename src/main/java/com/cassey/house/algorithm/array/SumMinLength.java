package com.cassey.house.algorithm.array;

/**
 * 20220506 字节1
 * Title
 * 最小长度子数组
 * <p>
 * Question description
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组，并返回其长度。如果不存在符合条件的子数组，返回0
 * <p>
 * 输入：s = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3]
 * 是该条件下的长度最小的子数组
 */
public class SumMinLength {
    public static void main(String[] args) {
        int s = 7;
        int[] nums = {2, 3, 1, 2, 4, 3};
        System.out.println(getSumMinLength(nums, s));
    }

    private static int getSumMinLength(int[] nums, int s) {
        int n = nums.length;
        int left = 0;
        int right;
        int res = n + 1;

        if(n == 0) {
            return 0;
        }

        while (left < n) {
            int sum = nums[left];
            right = left + 1;
            if(sum >= 7) {
                res = 1;
                break;
            }

            while (right < n) {
                sum += nums[right];
                if(sum >= s) {
                    res = Math.min(res, right - left + 1);
                    break;
                }
            }
        }

        return res > n ? 0 : res;
    }

    /**
     * 新想法
     * @param nums
     * @param s
     * @return
     */
    private static int getSumMinLength2(int[] nums, int s) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //res初始化为nums.length + 1
        int res = nums.length + 1;

        for (int left = 0; left < nums.length; left++) {
            int sum = 0;
            for (int right = left; right < nums.length; right++) {
                sum += nums[right];
                if (sum >= s) {
                    res = Math.min(res, right - left + 1);
                    //找到符号>=s的就可以结束right的循环
                    break;
                }
            }
        }

        //当res的值为初始化的nums.length + 1值时，说明for循环中没有给res赋值，不存在符合条件的子数组
        return res == nums.length + 1 ? 0 : res;
    }
}
