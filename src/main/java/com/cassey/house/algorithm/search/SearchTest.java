package com.cassey.house.algorithm.search;

import com.cassey.house.algorithm.search.impl.BinSearchService;
import com.cassey.house.algorithm.search.impl.FibonacciSearchService;
import com.cassey.house.algorithm.sort.SortService;
import com.cassey.house.algorithm.sort.impl.BubbleSortServiceImpl;
import com.cassey.house.algorithm.sort.impl.HeapSortServiceImpl;
import com.cassey.house.algorithm.sort.impl.MergeSortServiceImpl;
import com.cassey.house.algorithm.sort.impl.QuickSortServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTest {
    public static void main(String[] args) {
        //int[] arr = {34, 14, 54, 52, 24, 60, 11, 9, 16, 44};
        int[] arr = {1, 1, 1, 1, 1, 4, 4, 5, 6};

        System.out.print("数组：【");
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("】");

        String s = "";
        System.out.println("请输入要查找的元素");
        System.out.print(">");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            s = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int key = Integer.parseInt(s.trim());

        List<SearchService> serviceList = new ArrayList<>();
        serviceList.add(new BinSearchService());
        serviceList.add(new FibonacciSearchService());

        serviceList.forEach(service -> {
            int res = service.find(arr, key);

            if (res >= 0) {
                System.out.println(service.name() + ", success搜索[" + key + "]，索引为[" + res + "]");
            } else {
                System.out.println(service.name() + ", fail搜索[" + key + "]");
            }
        });

    }
}
