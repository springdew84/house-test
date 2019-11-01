package com.cassey.house.algorithm.sort.impl;

import com.cassey.house.algorithm.sort.SortService;

public class MergeSortServiceImpl implements SortService {

    @Override
    public void sort(int[] arr) {
        sortDesc("归并排序");

        if (arr == null || arr.length == 1) return;

        sort(arr, 0, arr.length - 1);
    }

    /**
     * 排序逻辑
     *
     * @param arr
     * @param L
     * @param R
     */
    private void sort(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }

        //查找基准数的正确索引
        int mid = L + ((R - L) >> 1);

        //对L数组进行相同操作
        sort(arr, L, mid);
        //对R数组进行相同操作
        sort(arr, mid + 1, R);
        //合并
        merge(arr, L, mid, R);
    }

    /**
     * 合并
     *
     * @param arr
     * @param L
     * @param mid
     * @param R
     * @return
     */
    private void merge(int[] arr, int L, int mid, int R) {
        int[] temp = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = mid + 1;

        //比较左右两部分元素，哪个小，把它填入temp中
        while (p1 <= mid && p2 <= R) {
            temp[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        /*******上面的循环退出后，把剩余的元素依次填入到temp中*******/

        //以下两个while只有一个会执行
        while (p1 <= mid) {
            temp[i++] = arr[p1++];
        }

        while (p2 <= R) {
            temp[i++] = arr[p2++];
        }

        //把最终的排序的结果复制给原数组
        for(i = 0; i < temp.length; i++) {
            arr[L + i] = temp[i];
        }
    }
}
