package com.cassey.house.algorithm.sort;

import com.cassey.house.algorithm.sort.impl.BubbleSortServiceImpl;
import com.cassey.house.algorithm.sort.impl.HeapSortServiceImpl;
import com.cassey.house.algorithm.sort.impl.MergeSortServiceImpl;
import com.cassey.house.algorithm.sort.impl.QuickSortServiceImpl;

public class SortTest {
    public static void main(String[] args) {
        int[] arr = {34, 14, 54, 52, 24, 60, 11, 9, 16, 44};

        System.out.print("排序前：【");
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("】");

        SortService sortService = null;
        sortService = new QuickSortServiceImpl();
        sortService = new HeapSortServiceImpl();
        sortService = new BubbleSortServiceImpl();
        sortService = new MergeSortServiceImpl();

        sortService.sort(arr);

        System.out.print("排序后：【");
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("】");

    }
}
