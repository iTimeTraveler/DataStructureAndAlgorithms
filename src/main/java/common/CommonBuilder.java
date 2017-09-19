package main.java.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuewenlong on 2017/9/19.
 */

public class CommonBuilder {
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(4,6,8,10,12,14,16,18,19,20);
		TreeNode<Integer> root = buildBSTTree(nums);
		System.out.println(root.printDFS());
		System.out.println(root.printBFS());
		System.out.println(root.midTraverse());
		System.out.println(root.preTraverse());
		System.out.println(root.lastTraverse());
	}

	/**
	 * 链表转换为二叉搜索树BST
	 * @param nums
	 * @return root
	 */
	public static TreeNode<Integer> buildBSTTree(List<Integer> nums){
		if(nums == null || nums.size() <= 0){
			return null;
		}
		Collections.sort(nums);
		return constructBSTTree(nums, 0, nums.size()-1);
	}

	private static TreeNode<Integer> constructBSTTree(List<Integer> nums, int start, int end){
		if(nums == null || start < 0 || end < 0 || end - start < 0
				|| start > nums.size() || end > nums.size()){
			return null;
		}
		int mid = start + (end - start)/2;
		int val = nums.get(mid);
		TreeNode<Integer> root = new TreeNode<Integer>(val);
		root.left = constructBSTTree(nums, start, mid-1);
		root.right = constructBSTTree(nums, mid+1, end);
		return root;
	}
}
