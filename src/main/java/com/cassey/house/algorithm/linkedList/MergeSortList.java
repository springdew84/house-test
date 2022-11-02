package com.cassey.house.algorithm.linkedList;


import java.util.ArrayList;
import java.util.List;

public class MergeSortList {
    public static void main(String[] args) {
        int[] values1 = {1, 3, 5, 7, 9};
        ListNode list1 = ListNode.create(values1);
        int[] values2 = {2, 4, 6, 8, 18};
        ListNode list2 = ListNode.create(values2);

        //ListNode listNode = merge(list1, list2);

        int[] values3 = {10, 14, 16, 28};
        ListNode list3 = ListNode.create(values3);
        List<ListNode> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);
        list.add(list3);

        ListNode listNode = mergeLists(list);
        listNode.print("after merge:");
    }

    /**
     * BM4. 合并有序链表
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode res = new ListNode(-1);
        ListNode cur = res;

        while (list1 != null && list2 != null) {
            if (list1.value > list2.value) {
                cur.next = list2;
                list2 = list2.next;
            } else {
                cur.next = list1;
                list1 = list1.next;
            }
            cur = cur.next;
        }

        if(list1 != null) {
            cur.next = list1;
        } else  if(list2 != null) {
            cur.next = list2;
        }

        return res.next;
    }

    /**
     * BM5. 合并k个已排序的链表
     * @param lists
     * @return
     */
    public static ListNode mergeLists(List<ListNode> lists) {
        if(lists == null) return null;
        if(lists.size() == 1) return lists.get(0);

        return divideMerge(lists, 0, lists.size() - 1);
    }


    private static ListNode divideMerge(List<ListNode> lists, int left, int right) {
        if(left > right) {
            return null;
        } else if(left == right) {
            return lists.get(left);
        }

        int mid = (left + right) / 2;
        return merge(divideMerge(lists, left, mid), divideMerge(lists, mid + 1, right));
    }
}
