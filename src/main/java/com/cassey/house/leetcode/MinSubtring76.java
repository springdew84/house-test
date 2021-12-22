package com.cassey.house.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 *  76. 最小覆盖子串（Minimum Window Substring）
 *
 * 题目描述：
 *
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字符的最小子串。
 *
 * 示例：
 *
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 *
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 */
public class MinSubtring76 {
    public static String minWindow(String s, String t) {

        int len = s.length();
        Set<Character> tSet = new HashSet<>();
        for(char tt : t.toCharArray()) {
            tSet.add(tt);
        }

        int start = -1;
        for (int i = 0; i < len; i++) {
            Character c = s.charAt(0);
            if(tSet.contains(c) && start > -1) {

            }
        }

        return "";
    }

    public static void main(String[] args) {
        minWindow("ADOBECODEBANC", "ABC");
    }
}
