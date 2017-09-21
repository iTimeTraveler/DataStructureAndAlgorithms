package common;

public class ListNode<T> {
    public T val;
    public ListNode next;
    public ListNode(T x) { val = x; }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
