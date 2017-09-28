package binarytree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import common.BSTreeNode;
import common.CommonBuilder;

/**
 * Binary Search Tree (BST) implement.
 */
public class BinaryTree<E extends Comparable<?>>{
	public BSTreeNode<E> root;
	
	public BinaryTree(BSTreeNode<E> root){
		this.root = root;
	}


	public String printBFS(){
		StringBuilder sb = new StringBuilder();
		breadthFirstSearch(root, sb);
		return sb.toString();
	}

	public String printDFS(){
		StringBuilder sb = new StringBuilder();
		depthFirstSearchRecursion(root, sb);
		return sb.toString();
	}

	/**
	 * 广度优先搜索BFS（非递归写法）
	 */
	private void breadthFirstSearch(BSTreeNode<E> node, StringBuilder sb){
		if(node == null || sb == null) return;
		Queue<BSTreeNode<E>> queue = new LinkedList<BSTreeNode<E>>();
		queue.offer(node);

		while(!queue.isEmpty()){
			BSTreeNode<E> n = queue.poll();
			if(n == null){
				continue;
			}
			sb.append(n.val + ", ");
			queue.offer(n.left);
			queue.offer(n.right);
		}
	}

	/**
	 * 深度优先搜索DFS（递归写法）
	 */
	private void depthFirstSearchRecursion(BSTreeNode<E> node, StringBuilder sb){
		if(node == null || sb == null) return;
		sb.append(node.val + ", ");
		depthFirstSearchRecursion(node.left, sb);
		depthFirstSearchRecursion(node.right, sb);
	}

	/**
	 * 深度优先搜索DFS（非递归写法）
	 */
	public void depthFirstSearch(){
		if(root == null) return;

		Stack<BSTreeNode<E>> stack = new Stack<BSTreeNode<E>>();
		stack.push(root);
		while(!stack.isEmpty()){
			BSTreeNode<E> element = stack.pop();
			System.out.print(element.val + ", ");

			//子节点倒序压栈
			if(element.right != null){
				stack.push(element.right);
			}
			if(element.left != null){
				stack.push(element.left);
			}
		}
	}

	/**
	 * 中序遍历
	 */
	public static <T extends Comparable<?>> String midTraverse(BSTreeNode<T> node){
		if(node == null) return null;
		return ((node.left != null ? BinaryTree.midTraverse(node.left) + ", " : "")
				+ String.valueOf(node.val) + ", "
				+ (node.right != null ? BinaryTree.midTraverse(node.right) + ", " : ""));
	}

	/**
	 * 先序遍历
	 */
	public static <T extends Comparable<?>> String preTraverse(BSTreeNode<T> node){
		if(node == null) return null;
		return (String.valueOf(node.val) + ", "
				+ (node.left != null ? BinaryTree.preTraverse(node.left) + ", " : "")
				+ (node.right != null ? BinaryTree.preTraverse(node.right) + ", " : ""));
	}

	/**
	 * 后序遍历
	 */
	public static <T extends Comparable<?>> String lastTraverse(BSTreeNode<T> node){
		if(node == null) return null;
		return ((node.left != null ? BinaryTree.lastTraverse(node.left) + ", " : "")
				+ (node.right != null ? BinaryTree.lastTraverse(node.right) + ", " : "")
				+ String.valueOf(node.val) + ", ");
	}
	
	/**
	 * 最小值
	 */
	public static <T extends Comparable<?>> BSTreeNode<T> getMinElement(BSTreeNode<T> node) {
		if(node == null) return null;
		while(node.left != null){
			node = node.left;
		}
		return node;
	}
	
	/**
	 * 最大值
	 */
	public static <T extends Comparable<?>> BSTreeNode<T> getMaxElement(BSTreeNode<T> node) {
		if(node == null) return null;
		while(node.right != null){
			node = node.right;
		}
		return node;
	}

	/**
	 * 树的高度，递归实现
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<?>> int maxHeight(BSTreeNode<T> node) {
		if (node == null)
			return 0;
		return Math.max(BinaryTree.maxHeight(node.left), BinaryTree.maxHeight(node.right)) + 1;
	}

	/**
	 * 所有元素最大字符数，递归实现
	 * @return
	 */
	public int getMaxWordWidth(){
		return getMaxWordWidth(root);
	}

	private int getMaxWordWidth(BSTreeNode<E> node){
		int vw = 0, lw = 0, rw = 0;
		if(node.val != null){
			vw = node.val.toString().length();
		}
		if(node.left != null){
			lw = getMaxWordWidth(node.left);
		}
		if(node.right != null){
			rw = getMaxWordWidth(node.right);
		}
		return Math.max(vw, Math.max(lw, rw));
	}

	public void print(){
		BSTPrinter.print(root);
	}


	public static void main(String[] args) {
//		ist<Integer> nums = Arrays.asList(1,2,3,4,6,7,8,9,10,12,13,14,16,18,19,20,36);
//		List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8);
		List<Integer> nums = Arrays.asList(6,2,9,4,5,7,3,8,1);

		BinaryTree<Integer> tree = CommonBuilder.buildBSTTree(nums);
		tree.depthFirstSearch();
		System.out.println(tree.printDFS());
		System.out.println(tree.printBFS());
		System.out.println(BinaryTree.maxHeight(tree.root));
		System.out.println(BinaryTree.midTraverse(tree.root));
		System.out.println(BinaryTree.preTraverse(tree.root));
		System.out.println(BinaryTree.lastTraverse(tree.root));
		BSTPrinter.printTree(tree.root);
		tree.print();
	}
}
