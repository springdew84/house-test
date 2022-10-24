package com.cassey.house.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 电话号码的字母组合
 * https://blog.csdn.net/qq_17550379/article/details/82459849?ivk_sa=1024320u
 */
public class PhoneNumber17 {
    static String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    private static List<String> res = new ArrayList<>();
    private static StringBuilder path = new StringBuilder();

    public static void main(String[] args) {
        List<String> list = getString("23");
        System.out.println(list.stream().collect(Collectors.joining(",")));
    }

    public static List<String> getString(String source) {
        if (source == null || source.length() == 0) {
            return res;
        }

        letterCombination(source, 0);
        return res;
    }

    private static void letterCombination(String source, int pos) {
        if (path.length() == source.length()) {
            res.add(path.toString());
            return;
        }

        String currentChars = mapping[Integer.valueOf(String.valueOf(source.charAt(pos)))];

        for (int i = 0; i < currentChars.length(); i++) {
            path.append(currentChars.charAt(i));
            letterCombination(source, pos + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
