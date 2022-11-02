package com.cassey.house.algorithm.linkedList;

public class ListNode {
    int value;
    ListNode next;

    @Override
    public boolean equals(Object anObject) {
        ListNode listNode = (ListNode) anObject;
        return this.value == listNode.value;
    }

    public ListNode(int value) {
        this.value = value;
    }

    public static ListNode create(int[] values) {
        ListNode pHead = new ListNode(values[0]);
        ListNode cur = pHead;

        for (int i = 1; i < values.length; i++) {
            ListNode listNode = new ListNode(values[i]);
            cur.next = listNode;
            cur = listNode;
        }

        return pHead;
    }

    public void print(String log) {
        print(log, this);
    }

    public static void print(String log, ListNode pHead) {
        ListNode cur = pHead;

        StringBuilder sb = new StringBuilder();
        sb.append("====").append(log).append(": [");

        //最大节点数
        final int maxLength = 100000;
        int i = 0;

        while (cur != null) {
            sb.append(cur.value);
            sb.append(",");
            cur = cur.next;
            i++;

            //循环链表保护下
            if(i > maxLength) break;
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        System.out.println(sb);
    }
}