package main.java.algorithms;

import main.java.common.ListNode;

public class LoopList {
	
	public static void main(String args[]){
		
		ListNode<Integer> n0 = new ListNode<Integer>(0);
		ListNode<Integer> n1 = new ListNode<Integer>(1);
		ListNode<Integer> n2 = new ListNode<Integer>(2);
		ListNode<Integer> n3 = new ListNode<Integer>(3);
		ListNode<Integer> n4 = new ListNode<Integer>(4);
		ListNode<Integer> n5 = new ListNode<Integer>(5);
		ListNode<Integer> n6 = new ListNode<Integer>(6);
		
		n0.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n0;
		
		System.out.println(isLoopList(n0));
		System.out.println(findEntranceInLoopList(n0).val);
	}
	
	/**
	 * 判断单链表是否存在环
	 * @param head
	 * @return
	 */
	public static <T> boolean isLoopList(ListNode<T> head){
		ListNode<T> slowPointer, fastPointer;
		
		//使用快慢指针，慢指针每次向前一步，快指针每次两步
		slowPointer = fastPointer = head;
		while(fastPointer != null && fastPointer.next != null){
			slowPointer = slowPointer.next;
			fastPointer = fastPointer.next.next;
			
			//两指针相遇则有环
			if(slowPointer == fastPointer){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 找到有环链表的入口
	 * @param head
	 * @return
	 */
	public static <T> ListNode<T> findEntranceInLoopList(ListNode<T> head){
		ListNode<T> slowPointer, fastPointer;
		
		//使用快慢指针，慢指针每次向前一步，快指针每次两步
		boolean isLoop = false;
		slowPointer = fastPointer = head;
		while(fastPointer != null && fastPointer.next != null){
			slowPointer = slowPointer.next;
			fastPointer = fastPointer.next.next;
			
			//两指针相遇则有环
			if(slowPointer == fastPointer){
				isLoop = true;
				break;
			}
		}
		
		//一个指针从链表头开始，一个从相遇点开始，每次一步，再次相遇的点即是入口节点
		if(isLoop){
			slowPointer = head;
			while(fastPointer != null && fastPointer.next != null){
				//两指针相遇的点即是入口节点
				if(slowPointer == fastPointer){
					return slowPointer;
				}
				
				slowPointer = slowPointer.next;
				fastPointer = fastPointer.next;
			}
		}
		return null;
	}

}
