package algorithms;

import java.util.ArrayList;
import java.util.List;

import common.CommonBuilder;
import common.ListNode;

/**
 * 反转单链表
 */
public class ReverseLinkedList {

	public static void main(String[] args) {
		int[] array = {1,2,3,4,5,6};
		ListNode<Integer> a1 = CommonBuilder.buildLindedList(array);

		//反转单链表
		ListNode node;
		node = reverseList(a1);
//		node = reverseList1(a1);
//		node = reverseList2(a1);
//		node = reversePairedList(a1);

		ListNode.printAllNodes(node);
	}


	/**
	 * 方法一：使用数组拷贝的方式反转单链表
	 */
	public static ListNode reverseList1(ListNode head){
		if(head == null) return null;
		//转移到数组存储(这里用ArrayList方便些)
		List<ListNode> nodeList = new ArrayList<ListNode>();
		while (head != null) {
			nodeList.add(head);
			head = head.next;
		}
		int startIndex = nodeList.size() - 1;
		for (int i = startIndex; i >= 0; i--) {
			ListNode node = nodeList.get(i);
			if (i == 0) {
				node.next = null;
			} else {
				node.next = nodeList.get(i - 1);
			}
		}
		// 现在头结点是原来的尾节点
		head = nodeList.get(startIndex);
		return head;
	}


	/**
	 * 方法二：头插法建立新链表，达到反转目的
	 */
	public static ListNode reverseList2(ListNode head){
		if(head == null) return null;
		ListNode previous = null;   //记录上个节点
		ListNode newHead = null;    //头插法的新头节点
		while(head != null){
			newHead = new ListNode(head.val);   //新建头结点
			newHead.next = previous;
			previous = newHead;
			head = head.next;   //下一个节点
		}
		return newHead;
	}


	/**
	 * 方法三：使用三个指针原地反转单链表
	 */
	public static ListNode reverseList(ListNode head){
		if(head == null) return null;
		ListNode a = head;      //当前节点A
		ListNode b = head.next; //下个节点B
		ListNode temp;          //下下个节点C
		//头结点的指针先清空
		head.next = null;
		//有可能链表只有一个节点，所以需要看b是否为null
		while(b != null){
			temp = b.next;  // 记录C节点
			b.next = a;     // a->b 反向
			if(temp == null){
				break;
			}
			a = b;      //移动到下一个节点
			b = temp;
		}
		return b == null ? a : b;
	}


	/**
	 * 单链表两两反转
	 *
	 *      已知一个链表：a->b->c->d->e
	 * 每两个元素进行反转：b->a->d->c->e
	 */
	public static ListNode reversePairedList(ListNode head){
		if(head == null) return null;
		ListNode a = head;          //当前节点A
		ListNode b = head.next;     //下个节点B
		ListNode temp;              //下下个节点C
		ListNode previous = null;   //上一组的尾指针，在下一组反转后需要改变
		ListNode newHead = b == null ? a : b;
		while(b != null){
			temp = b.next;  // 记录C节点
			b.next = a;     // a->b 反向
			a.next = temp;
			if(previous != null){
				previous.next = b;
			}
			if(temp == null){
				break;
			}
			previous = a;
			a = temp;       //移动到下一组
			b = temp.next;
		}
		return newHead;
	}
}
