package com.cassey.house.base;

import java.time.LocalDateTime;

public class LocalDataTimeTest {
    public static void main(String[] args) {
        String t = "https://test.com";

        if("https://test.com/aaaf//asdf".startsWith(t)) {
            return;
        }

        int dayOfYear = LocalDateTime.now().getDayOfYear();

        if ((dayOfYear & 1) == 1) {
            System.out.println("dayOfYear:" + dayOfYear + "是奇数");
        } else {
            System.out.println("dayOfYear:" + dayOfYear + "偶数");
        }
    }
}
