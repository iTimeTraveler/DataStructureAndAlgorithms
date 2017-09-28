package multiwaytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import common.MSTreeNode;

/**
 * 多叉树实现 Multiway Search Tree (MST) implementation.
 */
public class MSTree<E> {
	public MSTreeNode<E> root;

	public MSTree(MSTreeNode<E> root){
		this.root = root;
	}

	/**
	 * 深度优先搜索DFS（非递归实现）
	 */
	public String depthFirstSearch(){
		if(root == null) return null;
		StringBuilder sb = new StringBuilder();

		Stack<MSTreeNode<E>> stack = new Stack<MSTreeNode<E>>();
		stack.push(root);
		while(!stack.isEmpty()){
			MSTreeNode<E> element = stack.pop();
			sb.append(element.val + ", ");

			//子节点们需要倒序压栈
			if(element.children != null){
				for(int i = element.children.size() - 1; i >= 0; i--){
					stack.push(element.children.get(i));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 深度优先搜索DFS（递归实现）
	 */
	public String depthFirstSearchRecursion(MSTreeNode<E> node){
		if(node == null) return null;
		StringBuilder sb = new StringBuilder();

		sb.append(node.val + ", ");
		for(int i = 0; node.children != null && i < node.children.size(); i++){
			String c = depthFirstSearchRecursion(node.children.get(i));
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 广度优先搜索BFS（非递归实现）
	 */
	public String breadthFirstSearch(){
		if(root == null) return null;
		StringBuilder sb = new StringBuilder();

		Queue<MSTreeNode<E>> queue = new LinkedList<MSTreeNode<E>>();
		queue.offer(root);
		while(!queue.isEmpty()){
			MSTreeNode<E> e = queue.poll();

			sb.append(e.val + ", ");
			for(int i = 0; e.children != null && i < e.children.size(); i++){
				queue.offer(e.children.get(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 节点遍历类型枚举
	 */
	static public enum TraversalType {
		/**
		 * 迭代
		 */
		LOOP,
		/**
		 * 递归
		 */
		RECURSIVE;
	}


	public static void main(String[] args){
		MSTreeNode<Integer> root = new MSTreeNode<Integer>(0);
		MSTreeNode<Integer> n101 = new MSTreeNode<Integer>(1);
		MSTreeNode<Integer> n102 = new MSTreeNode<Integer>(2);
		MSTreeNode<Integer> n103 = new MSTreeNode<Integer>(3);
		MSTreeNode<Integer> n104 = new MSTreeNode<Integer>(4);

		MSTreeNode<Integer> n1021 = new MSTreeNode<Integer>(21);
		MSTreeNode<Integer> n1022 = new MSTreeNode<Integer>(22);
		MSTreeNode<Integer> n1023 = new MSTreeNode<Integer>(23);

		MSTreeNode<Integer> n10231 = new MSTreeNode<Integer>(31);

		MSTree<Integer> tree = new MSTree<Integer>(root);
		n1023.appendChildNode(n10231);
		n102.appendChildNode(n1021);
		n102.appendChildNode(n1022);
		n102.appendChildNode(n1023);
		tree.root.appendChildNode(n101);
		tree.root.appendChildNode(n102);
		tree.root.appendChildNode(n103);
		tree.root.appendChildNode(n104);

		System.out.println(tree.depthFirstSearch());
		System.out.println(tree.depthFirstSearchRecursion(tree.root));
		System.out.println(tree.breadthFirstSearch());
	}
}
