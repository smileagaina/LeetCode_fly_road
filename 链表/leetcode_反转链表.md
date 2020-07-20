# 高级篇
Leetcode25  K个一组翻转链表
给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。

k 是一个正整数，它的值小于或等于链表的长度。

如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。

示例：

给你这个链表：1->2->3->4->5

当 k = 2 时，应当返回: 2->1->4->3->5

当 k = 3 时，应当返回: 3->2->1->4->5



说明：你的算法只能使用常数的额外空间。你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
```

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    static ListNode headN = null, tailN = null;
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode hair = new ListNode(0);
        hair.next = head;
        ListNode pre = hair;
        while (head != null) {
            ListNode tail = pre;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) return hair.next;
            }
            ListNode nextNode = tail.next;
            // 反转局部k个
            myReverse(head, tail);
            head = headN; tail = tailN;
            pre.next = head;
            tail.next = nextNode;
            pre = tail;
            head = tail.next;
        }
        return hair.next;
    }
    public void myReverse(ListNode head, ListNode tail) {
        ListNode prev = tail.next;
        ListNode p = head;
        while (prev != tail) {
            ListNode nex = p.next;
            p.next = prev;
            prev = p;
            p = nex;
        }
        headN = tail;
        tailN = head;
    }
}


```

**递归版？？？**
[https://leetcode-cn.com/problems/reverse-nodes-in-k-group/solution/di-gui-java-by-reedfan-2/](https://leetcode-cn.com/problems/reverse-nodes-in-k-group/solution/di-gui-java-by-reedfan-2/)

# 基础篇
反转链表
leetcode206 翻转链表

反转一个单链表。

示例:输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL


你可以迭代或递归地反转链表。你能否用两种方法解决这道题？

```

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode preNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode tempNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = tempNode;
        }
        return preNode;
    }
}


```

```
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode nk = reverseList(head.next);
        // 在递归返回的时候，进行链表反转
        head.next.next = head;
        head.next = null;
        return nk;
    }
}
```