package com.cassey.house.algorithm.sort;

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
