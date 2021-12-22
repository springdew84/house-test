package com.cassey.house.leetcode;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums，请你给数组中的每个元素 nums[i] 都加上一个任意数字 x （-k <= x <= k），从而得到一个新数组 result 。
 *
 * 返回数组 result 的最大值和最小值之间可能存在的最小差值。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/smallest-range-i
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 */
public class SmallestRangeI908 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组 
     * @param k int整型
     * @return int整型
     */
    public int getSmallestRange (int[] nums, int k) {
        if(nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        return nums[nums.length - 1] - nums[0] > 2 * k ? nums[nums.length - 1] - nums[0] - 2 * k : 0;
    }

    public static void main(String[] args) {
        int[] nums = {7,6,20};
        int k = 4;
        int res = new SmallestRangeI908().getSmallestRange(nums, k);
        System.out.println("res:" + res);
    }

//    class Solution {
//        public int smallestRangeI(int[] A, int K) {
//            int min = A[0], max = A[0];
//            for (int x: A) {
//                min = Math.min(min, x);
//                max = Math.max(max, x);
//            }
//            return Math.max(0, max - min - 2*K);
//        }
//    }
//
//    作者：LeetCode
//    链接：https://leetcode-cn.com/problems/smallest-range-i/solution/zui-xiao-chai-zhi-i-by-leetcode/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}