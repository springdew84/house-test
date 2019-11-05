package com.cassey.house.base.Collections;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * map
 */
public class HashMapDemo {
    public static void main(String[] args) {
        //Hash map的键值都可以为null
        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(null, null);
        hashMap.put(null, null);
        System.out.println("hashMap size: " + hashMap.size());

        //同步哈希表
        Map<Integer, Integer> hashTable = new Hashtable<>();

        //报错java.lang.NullPointerException
        //hashTable.put(11, null);

        //报错java.lang.NullPointerException
        //hashTable.put(null, null);

        System.out.println("hashTable size: " + hashTable.size());

        //基于红黑树提供顺序访问的Map,比HashMap节省空间，但它的查增删时间复杂度为O(log(n)),这点与HashMap不同
        Map<Integer, Integer> treeMap = new TreeMap<>();
        //值可为null
        treeMap.put(11, null);

        //报错java.lang.NullPointerException
        //treeMap.put(null, null);

        System.out.println("treeMap size: " + treeMap.size());
    }
}
