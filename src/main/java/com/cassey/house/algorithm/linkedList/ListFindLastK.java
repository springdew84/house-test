package com.cassey.house.algorithm.linkedList;

public class ListFindLastK {
    public static void main(String[] args) {
        int[] array = {-3, 5, 4, 1, 2, 3, 4, 5, 6, 7, 8};
        ListNode pHead = ListNode.create(array);

        pHead.print("before");
        //ListNode res = findKthToTail(pHead, 3);
        //ListNode res = removeNthFromEnd(pHead, 8);

        int[] array2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        ListNode pHead2 = ListNode.create(array2);
        ListNode res = findFirstCommonNode(pHead, pHead2);

        res.print("after");
    }

    /**
     * BM8. 链表中倒数最后k个结点
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode findKthToTail(ListNode head, int k) {
        if (head == null) return null;
        if (k <= 0) return null;

        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < k; i++) {
            if (fast != null) {
                fast = fast.next;
            } else {
                return null;
            }
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * BM8. 链表中倒数最后k个结点
     * 先遍历得到链表节点总数
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode findKthToTail2(ListNode head, int k) {
        if (head == null) return null;
        if (k <= 0) return null;

        ListNode cur = head;
        int len = 0;
        while (cur != null) {
            cur = cur.next;
            len++;
        }

        if (len < k) {//长度比k小，返回空链表
            return null;
        }

        cur = head;
        for (int i = 0; i < len - k; i++) {
            cur = cur.next;
        }

        return cur;
    }

    /**
     * BM9. 删除链表的倒数第n个节点
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;

        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < n; i++) {
            if (fast != null) {
                fast = fast.next;
            }
        }
        ListNode headTemp = new ListNode(-1);
        headTemp.next = head;
        ListNode pre = headTemp;

        while (fast != null) {
            fast = fast.next;
            pre = slow;
            slow = slow.next;
        }

        pre.next = slow.next;

        return headTemp.next;
    }

    /**
     * BM10. 两个链表的第一个公共结点
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public static ListNode findFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        int len1 = getLength(pHead1);
        int len2 = getLength(pHead2);

        if (len1 > len2) {
            int n = len1 - len2;
            while (n > 0) {
                pHead1 = pHead1.next;
                n--;
            }
        } else if(len1 < len2) {
            int n = len2 - len1;
            while (n > 0) {
                pHead2 = pHead2.next;
                n--;
            }
        }

        while (pHead1 != null && pHead2 != null && !pHead1.equals(pHead2)) {
            pHead1 = pHead1.next;
            pHead2 = pHead2.next;
        }

        return pHead1;
    }

    private static int getLength(ListNode head) {
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            len++;
        }

        return len;
    }
}
