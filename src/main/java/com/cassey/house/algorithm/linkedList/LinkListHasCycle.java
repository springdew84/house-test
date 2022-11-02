package com.cassey.house.algorithm.linkedList;

public class LinkListHasCycle {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(6);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(2);
        ListNode node6 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node3;

        ListNode head = node1;
        System.out.println("hasCycle:" + hasCycle(head));

        ListNode entryNode = entryOfNodeLoop(head);
        entryNode.print("list entry node:");
    }

    /**
     * BM6. 判断链表中是否有环
     *
     * @param head
     * @return
     */
    private static boolean hasCycle(ListNode head) {
        if(head == null) {
            return false;
        }

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow) {
                return true;
            }
        }

        return false;
    }

    /**
     * BM6. 判断链表中是否有环
     *
     * @param head
     * @return
     */
    private static ListNode getCycleNode(ListNode head) {
        if(head == null) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow) {
                return fast;
            }
        }

        return null;
    }

    /**
     * BM7. 链表中环的入口节点
     * @param head
     * @return
     */
    public static ListNode entryOfNodeLoop(ListNode head) {
        ListNode slow = getCycleNode(head);
        if(slow == null) {
            return null;
        }

        //快指针回到起点
        ListNode fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }
}
