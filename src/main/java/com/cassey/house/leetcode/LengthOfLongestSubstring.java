package com.cassey.house.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
class LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> allChars = new HashSet<>();

        int n = s.length();
        int right = -1;
        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            if (i != 0) {
                allChars.remove(s.charAt(i - 1));
            }

            while (right + 1 < n && !allChars.contains(s.charAt(right + 1))) {
                allChars.add(s.charAt(right + 1));
                right++;
            }

            maxLength = Math.max(maxLength, right - i + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        long ff = 333L;
        long ddd = 445L;
        System.out.println(1F * ff / ddd);

        int i = 5 / 2;
        System.out.println("i:" + i);


        int res = lengthOfLongestSubstring("abcabcbb");
        System.out.println("res:" + res);
    }
}
