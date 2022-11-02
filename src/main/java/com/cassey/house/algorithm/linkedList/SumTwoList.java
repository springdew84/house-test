package com.cassey.house.algorithm.linkedList;

public class SumTwoList {
    public static void main(String[] args) {
        int[] values = {9, 8, 3, 6};
        ListNode head1 = ListNode.create(values);

        int[] values2 = {5, 2, 6};
        ListNode head2 = ListNode.create(values2);

        ListNode res = sumTwoList(head1, head2);
        res.print("after sum");
    }

    /**
     * BM11. 两个链表生成相加链表
     * @param head1
     * @param head2
     * @return
     */
    public static ListNode sumTwoList(ListNode head1, ListNode head2) {
        if(head1 == null) return head2;
        if(head2 == null) return head1;

        head1 = ListReversion.reverse(head1);
        head2 = ListReversion.reverse(head2);
        ListNode res = new ListNode(-1);
        ListNode cur = res;
        int carry = 0;

        while (head1 != null || head2 != null || carry != 0) {
            int val1 = head1 == null ? 0: head1.value;
            int val2 = head2 == null ? 0: head2.value;
            if(head1 != null) head1 = head1.next;
            if(head2 != null) head2 = head2.next;

            int val = val1 + val2 + carry;
            carry = val / 10;
            ListNode node = new ListNode(val % 10);
            cur.next = node;
            cur = node;
        }

        return ListReversion.reverse(res.next);
    }
}
