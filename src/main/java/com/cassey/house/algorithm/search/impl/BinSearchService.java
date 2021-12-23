package com.cassey.house.algorithm.search.impl;

import com.cassey.house.algorithm.search.SearchService;
import com.cassey.house.algorithm.sort.SortService;
import com.cassey.house.algorithm.sort.impl.QuickSortServiceImpl;

/**
 * 折半查找（二分查找）
 * 时间复杂度： O（log2n）
 *
 * 折半查找的前提条件是需要有序表顺序存储，对于静态查找表，
 * 一次排序后不再变化，折半查找能得到不错的效率。
 * 但对于需要频繁执行插入或删除操作的数据集来说，
 * 维护有序的排序会带来不小的工作量，那就不建议使用。
 *                                  ——《大话数据结构》
 */
public class BinSearchService implements SearchService {

    @Override
    public String name() {
        return this.getClass().getSimpleName() + ":折半查找（二分查找）";
    }

    @Override
    public int find(int[] arr, int key) {
        SortService sortService = new QuickSortServiceImpl();
        sortService.sort(arr);

//        System.out.print("排序完毕：【");
//        for (int i : arr) {
//            System.out.print(i + ",");
//        }
//        System.out.println("】");

        int low = 0;
        int high = arr.length;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;  //先获取中间的位置

            if(mid >= arr.length) {//数组已经找完没找到
                return -1;
            }

            if (key > arr[mid]) {
                low = mid + 1;           //说明在中间右边

            } else if (key < arr[mid]) {
                high = mid - 1;          //说明在中间左边

            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 有重复数据时返回第一条
     * 没查找到时返回数组长度
     * @param arr
     * @param key
     * @return
     */
    public int find2(int[] arr, int key) {
        int middle;
        int start = 0;
        int end = arr.length - 1;

        while(start <= end) {
            middle = (start + end) / 2;

            if(middle >= arr.length) {
                return arr.length;
            }

            if(key > middle) {
                start = middle + 1;
            } else if(key < middle) {
                end = middle - 1;
            } else {
                int first = middle;
                while (first >= 0 && arr[first] == arr[middle]) {
                    first--;
                }

                return first++;
            }
        }

        return arr.length;
    }
}
