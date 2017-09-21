package common;

public class ListNode<T> {
    public T val;
    public ListNode<T> next;
    public ListNode(T x) { val = x; }
    public ListNode(T val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    //打印输出链表
    public static void printAllNodes(ListNode head){
        ListNode node = head;
        while (node != null){
            System.out.print(node.val);
            node = node.next;
            System.out.print(node != null ? "->" : "");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
