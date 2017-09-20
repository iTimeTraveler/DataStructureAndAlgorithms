package common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuewenlong on 2017/9/19.
 */

public class CommonBuilder {
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,3,4,6,7,8,9,10,12,13,14,16,18,19,20,36);
//		List<Integer> nums = Arrays.asList(1,3,4,6,7,8,9);
		Tree<Integer> tree = buildBSTTree(nums);
		System.out.println(tree.root.printDFS());
		System.out.println(tree.root.printBFS());
		System.out.println(Tree.maxHeight(tree.root));
		System.out.println(Tree.midTraverse(tree.root));
		System.out.println(Tree.preTraverse(tree.root));
		System.out.println(Tree.lastTraverse(tree.root));
		TreePrinter.printTree(tree.root);
		tree.print();
	}

	/**
	 * 链表转换为二叉搜索树BST
	 * @param nums
	 * @return root
	 */
	public static Tree<Integer> buildBSTTree(List<Integer> nums){
		if(nums == null || nums.size() <= 0){
			return null;
		}
		Collections.sort(nums);
		return new Tree<Integer>(constructBSTTree(nums, 0, nums.size()-1));
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
