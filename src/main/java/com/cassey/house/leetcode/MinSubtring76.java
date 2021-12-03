package com.cassey.house.leetcode;

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
        int[] sCount = new int[128];
        int[] tCount = new int[128];
        //在source中查到target中字母的个数
        int found = 0;

        for (int i = 0; i < 128; i++) {
            sCount[i] = 0;
            tCount[i] = 0;
        }

        for (char c : t.toCharArray()) {
            tCount[c]++;
        }

        for (char c : s.toCharArray()) {
            sCount[c]++;
        }

        for (int i = 0; i < s.length(); i++) {
            sCount[s.charAt(i)]++;

            if (sCount[s.charAt(i)] <= tCount[s.charAt(i)]) {
                found++;
            }

            //包含字符
            if(found == t.length()) {

            }
        }

        return "";
    }

    public static void main(String[] args) {
        minWindow("ADOBECODEBANC", "ABC");
    }
}
