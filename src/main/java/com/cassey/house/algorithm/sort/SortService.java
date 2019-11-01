package com.cassey.house.algorithm.sort;

/**
 * 排序算法
 * 1.快速排序
 * 2.归并排序
 * 3.堆排序
 * 4.冒泡排序
 */
public interface SortService {
    /**
     * 排序
     * @param arr
     */
    void sort(int[] arr);

    /**
     * 排序算法
     * @param name
     */
    default void sortDesc(String name) {
        System.out.print("使用了【" + name + "】算法");
    }
}
