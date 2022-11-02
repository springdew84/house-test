package com.cassey.house.leetcode;

/**
 * 最大价值总和
 招行笔试算法题
 * https://blog.csdn.net/nobody_lo/article/details/114487446
 */
public class MaxValueSum {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组 
     * @param values int整型一维数组 
     * @return int整型
     */
    public int getMaxValue(int[] nums, int[] values) {
        // write code here
        int[] valuePerTime = new int[values.length+1];
        valuePerTime[0] = 0;
        int l = 0;
        int r = values.length - 1;
        int i = 1;
        for(int v : values) {
            if(l > r) {
                break;
            }

            if(i >= values.length) {
                valuePerTime[i] = v * nums[l];
                break;
            }

            if(values[i] < values[i-1]) {
                if(nums[l] < nums[r]) {
                    valuePerTime[i] = v * nums[r];
                    r--;
                } else {
                    valuePerTime[i] = v * nums[l];
                    l++;
                }
            } else {
                if(nums[l] > nums[r]) {
                    valuePerTime[i] = v * nums[r];
                    r--;
                } else {
                    valuePerTime[i] = v * nums[l];
                    l++;
                }
            }
            i++;
        }

        int maxValue = 0;
        for(int v : valuePerTime) {
            maxValue += v;
        }
        return maxValue;
    }

    /**
     * 修正&改进
     * @param nums
     * @param values
     * @return
     */
    public int getMaxValue2(int[] nums, int[] values) {
        int[] valuePerTime = new int[values.length+1];
        valuePerTime[0] = 0;
        int l = 0, r = values.length - 1, i = 1;

        for(int v : values) {
            if(l > r) {
                return valuePerTime[nums.length];
            }

            if(nums[l] > nums[r]) {
                valuePerTime[i] = valuePerTime[i - 1] + v * nums[l++];
            } else {
                valuePerTime[i] = valuePerTime[i - 1] + v * nums[r--];
            }
            i++;
        }

        return valuePerTime[nums.length];
    }

    public static void main(String[] args) {
        int[] nums = {1,100};
        int[] values = {2,1};
        int res = new MaxValueSum().getMaxValue(nums, values);
        System.out.println("res:" + res);
    }
}