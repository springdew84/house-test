package com.cassey.house.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String[] singletonArray(String s) {
        return new String[]{s};
    }

    public static String[] newStringArray(String... s) {
        if (s == null) {
            return null;
        }
        if (s.length == 0) {
            return new String[0];
        }

        String[] array = new String[s.length];
        System.arraycopy(s, 0, array, 0, s.length);

        return array;
    }

    public static String toString(Object o) {
        return o == null ? null : o.toString();
    }

    public static List<String> split2List(String s, String d) {
        if (isEmpty(s)) {
            return Collections.emptyList();
        }
        String[] arr = s.split(d);
        return Arrays.asList(arr);
    }

    public static List<Integer> split2Int(String s) {
        if (isEmpty(s)) {
            return Collections.emptyList();
        }

        List<Integer> list = new ArrayList<>();

        int offset = 0, count = 0;
        char[] chars = s.toCharArray();

        for (char c : chars) {
            //数字，正负号视为数字的一部分
            if (c == '+' || c == '-' || (c >= '0' && c <= '9')) {
                count++;
            }
            //否则，视作分隔符
            else {
                if (count > 0) {
                    list.add(Integer.valueOf(new String(chars, offset, count)));
                    offset = offset + count + 1;
                    count = 0;
                } else {
                    offset++;
                }
            }
        }

        if (count > 0) {
            list.add(Integer.valueOf(new String(chars, offset, count)));
        }

        return list;
    }

    public static String pick(String... s) {
        if (s != null && s.length > 0) {
            for (String s1 : s) {
                if (s1 != null && !s1.isEmpty()) {
                    return s1;
                }
            }
        }

        return null;
    }
}
