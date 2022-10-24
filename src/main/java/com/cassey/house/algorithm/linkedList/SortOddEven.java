package com.cassey.house.algorithm.linkedList;

/**
 * 20220510 字节2
 * 一个链表奇数位上升序，偶数位上降序，不用额外空间让这个链表整体升序
 * 1->8->3->6->5->4->7->2->9
 */
public class SortOddEven {
    public static void main(String[] args) {

    }

    private static void sort(ListNode pRoot) {
        if(pRoot == null) {
            return;
        }

        //1.分奇偶两个链表
        ListNode[] nodes = divideListNode(pRoot);

        //2.偶数位逆转
        reverse(nodes[1]);

        //3.合并两个有序链表
        pRoot = merge(nodes[0], nodes[1]);


    }

    private static ListNode merge(ListNode node1, ListNode node2) {
        ListNode p1 = node1;
        ListNode p2 = node2;

        ListNode pRoot = new ListNode(-1);
        ListNode cur = pRoot;

        while (p1 != null && p2 != null) {
            if(p1.value < p2.value) {
                cur.next = p1;
                p1 = p1.next;
            } else {
                cur.next = p2;
                p2 = p2.next;
            }
            cur = cur.next;
        }

        if(p1 != null) {
            cur.next = p1;
        } else {
            cur.next = p2;
        }

        return pRoot.next;
    }

    private static void reverse(ListNode pRoot) {
        ListNode cur = pRoot;
        ListNode pre = null;

        while (cur.next != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
    }

    private static ListNode[] divideListNode(ListNode pRoot) {
        ListNode odd = new ListNode(-1);
        ListNode even = new ListNode(-1);

        ListNode oddCur = odd;
        ListNode eventCur = even;

        int flag = 1;
        while (pRoot != null) {
            if (flag % 2 == 0) {
                eventCur.next = pRoot;
                eventCur = eventCur.next;
            } else {
                oddCur.next = pRoot;
                oddCur = oddCur.next;
            }

            pRoot = pRoot.next;
            flag++;
        }

        ///要让最后两个末尾元素的下一个都指向null
        eventCur.next = null;
        oddCur.next= null;

        return new ListNode[] {odd.next, even.next};
    }
}
