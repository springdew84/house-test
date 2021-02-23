package com.cassey.house.leetcode;

import java.util.HashSet;
import java.util.Set;

class LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> allChars = new HashSet<>();

        int n = s.length();
        int right = -1;
        int maxLength = 0;

        for(int i=0;i<n;i++) {
            if(i != 0) {
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

        int i = 5 /2;
        System.out.println("i:" + i);


        int res = lengthOfLongestSubstring("abcabcbb");
        System.out.println("res:" + res);
    }
}
