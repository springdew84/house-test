package com.cassey.house.algorithm.linkedList;


/**
 * 链表逆转
 */
public class ListReversion {
    public static void main(String[] args) {
        ListNode pHead = null;
        reverse(pHead);
    }

    private static void reverse(ListNode pHead) {
        if(pHead == null) {
            return;
        }

        ListNode pre = null;
        ListNode cur = pHead;

        while (cur.next != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }

    }
}
