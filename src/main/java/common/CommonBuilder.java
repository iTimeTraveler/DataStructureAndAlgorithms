package main.java.common;

import java.util.Collections;
import java.util.List;

import binarytree.BinaryTree;


public class CommonBuilder {

	/**
	 * Integer链表转换为二叉搜索树BST
	 * @param nums
	 * @return root
	 */
	public static BinaryTree<Integer> buildBSTTree(List<Integer> nums){
		if(nums == null || nums.size() <= 0){
			return null;
		}
		Collections.sort(nums);
		return new BinaryTree<Integer>(constructBSTTree(nums, 0, nums.size()-1));
	}

	private static BSTreeNode<Integer> constructBSTTree(List<Integer> nums, int start, int end){
		if(nums == null || start < 0 || end < 0 || end - start < 0
				|| start > nums.size() || end > nums.size()){
			return null;
		}
		int mid = start + (end - start)/2;
		int val = nums.get(mid);
		BSTreeNode<Integer> root = new BSTreeNode<Integer>(val);
		root.left = constructBSTTree(nums, start, mid-1);
		root.right = constructBSTTree(nums, mid+1, end);
		return root;
	}

	/**
	 * Integer数组转换为链表
	 * @param arr
	 * @return head
	 */
	public static ListNode<Integer> buildLindedList(int[] arr) {
		ListNode<Integer> head = new ListNode<Integer>(arr[0], null);
		ListNode<Integer> p = head;
		for (int i = 1; i < arr.length; i++) {
			p.next = new ListNode<Integer>(arr[i],null);
			p = p.next;
		}
		return head;
	}
}
