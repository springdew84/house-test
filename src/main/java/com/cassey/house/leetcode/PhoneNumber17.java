package com.cassey.house.leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * 电话号码的字母组合
 * https://blog.csdn.net/qq_17550379/article/details/82459849?ivk_sa=1024320u
 *
 */
public class PhoneNumber17 {
    static String[] mapping = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

    public static void main(String[] args) {
        String[] test = getString("23");
        System.out.println(test);
    }

    public static String[] getString(String source) {
        Set<String> total = new HashSet<>();
        for(char c : source.toCharArray()) {
            total.add(mapping[Integer.valueOf(String.valueOf(c))]);
        }

        String totalStr = "";

        for(String str : total) {
            totalStr += str;
        }

        char[] totalC = totalStr.toCharArray();
        for(char c : totalC) {

        }

        System.out.println(total);
        return new String[0];
    }
}
