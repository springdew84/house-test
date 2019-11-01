package com.cassey.house.algorithm.sort.impl;

import com.cassey.house.algorithm.sort.SortService;

public class QuickSortServiceImpl implements SortService {

    @Override
    public void sort(int[] arr) {
        sortDesc("快速排序");

        if (arr == null || arr.length == 1) return;

        sort(arr, 0, arr.length - 1);
    }

    /**
     * 排序逻辑
     *
     * @param arr
     * @param low
     * @param high
     */
    private void sort(int[] arr, int low, int high) {
        //当low>=hgit时，说明已经排序好
        if (low < high) {
            //查找基准数的正确索引
            int index = getIndex(arr, low, high);

            //对index之前的数组进行相同操作
            sort(arr, 0, index - 1);
            //对index之后的数组进行相同操作
            sort(arr, index + 1, high);
        }
    }

    /**
     * 查找基准数的正确索引
     *
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private int getIndex(int[] arr, int low, int high) {
        //基准值
        int pivot = arr[low];

        while (low < high) {
            //当队尾元素大于等于基准值时，向前移动high指针
            while (low < high && arr[high] >= pivot) {
                high--;
            }

            //当队尾元素小于基准值，将其值赋值给low
            arr[low] = arr[high];

            //当队首元素小于等于基准值时，向后移动low指针
            while (low < high && arr[low] <= pivot) {
                low++;
            }

            //当队首元素大于基准值时，将其值赋值给high
            arr[high] = arr[low];
        }

        //跳出循环时low和high相等，此时的low或high就是pivot的正确索引位置
        //而此时low位置并不是pivot，所以要将pivot赋值给arr[low]
        arr[low] = pivot;
        return low;
    }
}
