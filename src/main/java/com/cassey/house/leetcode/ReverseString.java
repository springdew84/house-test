package com.cassey.house.leetcode;

public class ReverseString {
    public static void reverseStringFun(char[] s) {
        int n = s.length / 2 ;
        char tmp;
        for(int i=0;i < n;i++) {
            tmp = s[i];
            s[i] = s[s.length - i - 1];
            s[s.length -i - 1] = tmp;
        }

        System.out.println(s);

    }

    public static void main(String[] args) {
        char[] s = {'h', 'e', 'l', 'l', 'o'};

        reverseStringFun(s);

        System.out.println(s);
    }
}
