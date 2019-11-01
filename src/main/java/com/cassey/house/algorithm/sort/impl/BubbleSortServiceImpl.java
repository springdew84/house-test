package com.cassey.house.algorithm.sort.impl;

import com.cassey.house.algorithm.sort.SortService;

public class BubbleSortServiceImpl implements SortService {

    @Override
    public void sort(int[] arr) {
        sortDesc("冒泡排序");

        if (arr == null || arr.length == 1) return;

        int i, j, temp;

        //外循环为排序趟数，len个数进行len-1趟
        for (i = 0; i < arr.length - 1; i++)
            //内循环为每趟比较的次数，第i趟比较len-i次
            for (j = 0; j < arr.length - 1 - i; j++) {
                //相邻元素比较，若逆序则交换（升序为左大于右，降序反之)
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
    }
}
