package common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuewenlong on 2017/9/19.
 */

public class CommonBuilder {
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(4,6,8,10,12,14,16,18,19,20);
		TreeNode root = buildBSTTree(nums);
		System.out.println(root.printDFS());
		System.out.println(root.printBFS());
		System.out.println(TreeNode.midTraverse(root));
		System.out.println(TreeNode.preTraverse(root));
		System.out.println(TreeNode.lastTraverse(root));
	}

	/**
	 * 链表转换为二叉搜索树BST
	 * @param nums
	 * @return root
	 */
	public static TreeNode buildBSTTree(List<Integer> nums){
		if(nums == null || nums.size() <= 0){
			return null;
		}
		Collections.sort(nums);
		return constructBSTTree(nums, 0, nums.size()-1);
	}

	private static TreeNode constructBSTTree(List<Integer> nums, int start, int end){
		if(nums == null || start < 0 || end < 0 || end - start < 0
				|| start > nums.size() || end > nums.size()){
			return null;
		}
		int mid = start + (end - start)/2;
		int val = nums.get(mid);
		TreeNode root = new TreeNode(val);
		root.left = constructBSTTree(nums, start, mid-1);
		root.right = constructBSTTree(nums, mid+1, end);
		return root;
	}
}
