package multiwaytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import common.TreeNode;

/**
 * Multiway Search Tree (MST) implementation.
 */
public class MSTree<E> {
	public TreeNode<E> root;

	public MSTree(TreeNode<E> root){
		this.root = root;
	}

	/**
	 * 深度优先搜索DFS（非递归实现）
	 */
	public String depthFirstSearch(){
		if(root == null) return null;
		StringBuilder sb = new StringBuilder();

		Stack<TreeNode<E>> stack = new Stack<TreeNode<E>>();
		stack.push(root);
		while(!stack.isEmpty()){
			TreeNode<E> element = stack.pop();
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
	public String depthFirstSearchRecursion(TreeNode<E> node){
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

		Queue<TreeNode<E>> queue = new LinkedList<TreeNode<E>>();
		queue.offer(root);
		while(!queue.isEmpty()){
			TreeNode<E> e = queue.poll();

			sb.append(e.val + ", ");
			for(int i = 0; e.children != null && i < e.children.size(); i++){
				queue.offer(e.children.get(i));
			}
		}
		return sb.toString();
	}


	public static void main(String[] args){
		TreeNode<Integer> root = new TreeNode<Integer>(0);
		TreeNode<Integer> n101 = new TreeNode<Integer>(1);
		TreeNode<Integer> n102 = new TreeNode<Integer>(2);
		TreeNode<Integer> n103 = new TreeNode<Integer>(3);
		TreeNode<Integer> n104 = new TreeNode<Integer>(4);

		TreeNode<Integer> n1021 = new TreeNode<Integer>(21);
		TreeNode<Integer> n1022 = new TreeNode<Integer>(22);
		TreeNode<Integer> n1023 = new TreeNode<Integer>(23);

		TreeNode<Integer> n10231 = new TreeNode<Integer>(31);

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
