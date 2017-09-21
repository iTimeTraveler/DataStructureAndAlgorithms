package common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CommonBuilder {
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,3,4,6,7,8,9,10,12,13,14,16,18,19,20,36);
//		List<Integer> nums = Arrays.asList(1,5,4,9,6,88, 2, 3);
//		List<Integer> nums = Arrays.asList(1,3,4,6,7,8,9);
//		Tree<Integer> tree = buildBSTTree(nums);
//		System.out.println(tree.root.printDFS());
//		System.out.println(tree.root.printBFS());
//		System.out.println(Tree.maxHeight(tree.root));
//		System.out.println(Tree.midTraverse(tree.root));
//		System.out.println(Tree.preTraverse(tree.root));
//		System.out.println(Tree.lastTraverse(tree.root));
//		TreePrinter.printTree(tree.root);
//		tree.print();

		AVLTree<Integer> avlTree = new AVLTree<Integer>(null);
		for(int i=0; i<nums.size(); i++) {
			avlTree.insert(nums.get(i));
        }
		System.out.println(avlTree.root.printDFS());
		System.out.println(avlTree.root.printBFS());
		System.out.println(Tree.maxHeight(avlTree.root));
		System.out.println(Tree.midTraverse(avlTree.root));
		System.out.println(Tree.preTraverse(avlTree.root));
		System.out.println(Tree.lastTraverse(avlTree.root));
		TreePrinter.printTree(avlTree.root);
		TreePrinter.printTree(avlTree.search(19));
		avlTree.print();

		avlTree.delete(19);
		TreePrinter.printTree(avlTree.root);
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
