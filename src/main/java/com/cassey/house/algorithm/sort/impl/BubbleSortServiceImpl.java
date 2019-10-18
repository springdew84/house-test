package com.cassey.house.algorithm.sort.impl;

import com.cassey.house.algorithm.sort.SortService;

public class BubbleSortServiceImpl implements SortService {

    @Override
    public void sort(int[] arr) {
        sortDesc("堆排序");

        if (arr == null || arr.length == 1) return;

        /********将数组元素转化为大顶堆********/

        //递推公式就是 int root = 2*i, int left = 2*i+1, int right = 2*i+2;
        int cursor = arr.length / 2;
        for (int i = cursor; i >= 0; i--) {
            buildMaxHeap(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i >= 1; i--) {
            //经过上面的一些操作，arr[0]是当前数组里最大的元素，需要和末尾的元素交换，然后拿出最大的元素
            swap(arr, 0, i);

            //交换完后，下次遍历的时候，就应该跳过最后一个元素，也就是最大的那个值，然后重新构建最大堆的大小就是减去1，从0的
            //位置开始最大堆
            buildMaxHeap(arr, i, 0);
        }
    }

    /**
     * 构建大顶堆
     * @param arr
     * @param heapSize
     * @param index
     */
    private void buildMaxHeap(int[] arr, int heapSize, int index) {
        //左子树节点
        int left = index * 2 + 1;
        //右子树节点
        int right = index * 2 + 2;
        //暂定index位置的就是最大值
        int maxValue = index;

        //如果左子节点的值，比当前最大的值大，就把最大值的位置换成左子节点的位置
        if(left < heapSize && arr[left] > arr[maxValue]) {
            maxValue = left;
        }

        //如果右子节点的值，比当前最大的值大，就把最大值的位置换成右子节点的位置
        if(right < heapSize && arr[right] > arr[maxValue]) {
            maxValue = right;
        }

        //如果不相等说明，这个子节点的值有比自己大的，位置发生了交换了位置
        if(maxValue != index) {
            //交换元素位置
            swap(arr, maxValue, index);

            //交换完位置后还需要判断子节点是否打破了最大堆的性质。最大堆性质：两个子节点都比父节点小。
            buildMaxHeap(arr, heapSize, maxValue);
        }
    }

    /**
     * 交换元素
     * @param arr
     * @param j
     * @param j
     */
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
