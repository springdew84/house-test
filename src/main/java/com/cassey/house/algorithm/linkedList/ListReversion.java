package com.cassey.house.algorithm.linkedList;


/**
 * 链表逆转
 */
public class ListReversion {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};

        ListNode pHead = ListNode.create(array);
        pHead.print("before reverse");
        //ListNode res = reverse(pHead);
        //ListNode res = reverseBetweenMN(pHead, 3, 6);
        ListNode res = reverseKGroup(pHead, 3);
        res.print("after reverse");
    }

    /**
     * BM1 反转整个链表
     *
     * @param pHead
     * @return
     */
    public static ListNode reverse(ListNode pHead) {
        if (pHead == null) {
            return null;
        }

        ListNode pre = null;
        ListNode cur = pHead;

        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }

        return pre;
    }

    /**
     * BM2 反转链表区间
     *
     * @param pHead
     * @return
     */
    private static ListNode reverseBetweenMN(ListNode pHead, int m, int n) {
        if (pHead == null) {
            return null;
        }
        ListNode res = new ListNode(-1);
        res.next = pHead;
        ListNode pre = null;
        ListNode cur = pHead;

        //先找到m位置的节点
        for (int i = 1; i < m; i++) {
            pre = cur;
            cur = cur.next;
        }

        //反转
        for (int i = m; i < n; i++) {
            ListNode temp = cur.next;
            cur.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;
        }

        return res.next;
    }

    /**
     * BM3.链表中的节点每k个一组翻转
     * @param pHead
     * @param k
     * @return
     */
    private static ListNode reverseKGroup(ListNode pHead, int k) {
        ListNode tail = pHead;

        //找出每次反转的尾部节点
        for (int i = 0; i < k; i++) {
            if(tail == null) { //tail为空说明当前段节点数量不足k个，返回当前段
                return pHead;
            }
            tail = tail.next;
        }

        ListNode pre = null;
        ListNode cur = pHead;

        while (cur != tail) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }

        pHead.next = reverseKGroup(tail, k);
        return pre;
    }
}
