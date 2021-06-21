package com.cassey.house.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/array-and-string/c5tv3/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class MergeArray {
    public static void main(String[] args) {
        int[][] intervals = new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("intervals:" + Arrays.deepToString(intervals));
        int[][] res = merge(intervals);
        System.out.println("res:" + Arrays.deepToString(res));
    }

    private static int[][] merge(int[][] intervals) {
        int i = 0;
        List<int[]> array = new ArrayList<>();
        for (; ; ) {
            if (i >= intervals.length) break;
            int[] temp = intervals[i];
            while (true) {

                if ((i + 1) < intervals.length && ((intervals[i][1] >= intervals[i + 1][0])
                || intervals[i][1] == intervals[i + 1][1])){
                    temp[1] = intervals[i + 1][1];
                    i++;
                } else {
                    array.add(temp);
                    i++;
                    break;
                }
            }
        }

        int[][] res = new int[array.size()][2];
        for (int j = 0; j < array.size(); j++) {
            res[j] = array.get(j);
        }

        return res;
    }
}
