package main.java.common;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode<T> {
	public T val;
	public boolean isRed;
	public TreeNode<T> left;
	public TreeNode<T> right;
	public TreeNode<T> parent;
	public TreeNode(T x){
		val = x;
	}

	/**
	 * 中序遍历
	 */
	public String midTraverse(){
		StringBuilder sb = new StringBuilder();
		sb.append((left != null ? left.midTraverse() : "")
				+ String.valueOf(val) + ", " 
				+ (right != null ? right.midTraverse() : ""));
		return sb.toString();
	}

	/**
	 * 先序遍历
	 */
	public String preTraverse(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(val) + ", " 
				+ (left != null ? left.midTraverse() : "")
				+ (right != null ? right.midTraverse() : ""));
		return sb.toString();
	}

	/**
	 * 后序遍历
	 */
	public String lastTraverse(){
		StringBuilder sb = new StringBuilder();
		sb.append((left != null ? left.midTraverse() : "")  + ", " 
				+ (right != null ? right.midTraverse() : "")
				+ String.valueOf(val));
		return sb.toString();
	}

	public String printBFS(){
		StringBuilder sb = new StringBuilder();
		breadthFirstSearch(this, sb);
		return sb.toString();
	}

	public String printDFS(){
		StringBuilder sb = new StringBuilder();
		depthFirstSearch(this, sb);
		return sb.toString();
	}

	/**
	 * 广度优先搜索BFS
	 */
	private void breadthFirstSearch(TreeNode<T> node, StringBuilder sb){
		if(node == null || sb == null) return;
		Queue<TreeNode<T>> queue = new LinkedList<TreeNode<T>>();
		queue.offer(node);

		while(!queue.isEmpty()){
			TreeNode<T> n = queue.poll();
			if(n == null){
				continue;
			}
			sb.append(n.val + ", ");
			queue.offer(n.left);
			queue.offer(n.right);
		}
	}

	/**
	 * 深度优先搜索DFS
	 */
	private void depthFirstSearch(TreeNode<T> node, StringBuilder sb){
		if(node == null || sb == null) return;
		sb.append(node.val + ", ");
		depthFirstSearch(node.left, sb);
		depthFirstSearch(node.right, sb);
	}

	public int getHeight(){
		if(left == null && right == null){
			return 1;
		}else if(left == null) {
			return 1 + right.getHeight();
		}else if(right == null) {
			return 1 + left.getHeight();
		}else{
			return 1 + Math.max(left.getHeight(), right.getHeight());
		}
	}
	
	@Override
	public String toString() {
		return "TreeNode{" +
				"value=" + val +
				", left=" + left +
				", right=" + right +
				'}';
	}
}
