package com.cassey.house.base;

public class BinaryArrayTest {
    private static int[][] barray = {{3,2,3,4},{12,43,74,46},{6,8,20,15}};

    private static int[][] barray2 = new int[2][5];

    public static void main(String[] args) {
        System.out.println("baaray[0,1]:" + barray[0][1]);

        int index = 0;
        for(int i = 0; i< barray2.length;i++) {
            for(int j = 0;j< barray2[0].length;j++) {
                barray2[i][j] = index++;
            }
        }
        System.out.println("baaray2[0,1]:" + barray2[0][1]);
        System.out.println("baaray2[1,0]:" + barray2[1][0]);
    }
}
