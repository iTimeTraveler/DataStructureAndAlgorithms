package common;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode(int x){
		val = x;
	}

	/**
	 * 中序遍历
	 */
	public static String midTraverse(TreeNode root){
		if(root == null) return "";
		StringBuilder sb = new StringBuilder();
		sb.append(midTraverse(root.left) + String.valueOf(root.val) +", "+  midTraverse(root.right));
		return sb.toString();
	}

	/**
	 * 先序遍历
	 */
	public static String preTraverse(TreeNode root){
		if(root == null) return "";
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(root.val) +", " + midTraverse(root.left) +  midTraverse(root.right));
		return sb.toString();
	}

	/**
	 * 后序遍历
	 */
	public static String lastTraverse(TreeNode root){
		if(root == null) return "";
		StringBuilder sb = new StringBuilder();
		sb.append(midTraverse(root.left) + ", " + midTraverse(root.right) + String.valueOf(root.val));
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
	private void breadthFirstSearch(TreeNode node, StringBuilder sb){
		if(node == null || sb == null) return;
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.offer(node);

		while(!queue.isEmpty()){
			TreeNode n = queue.poll();
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
	private void depthFirstSearch(TreeNode node, StringBuilder sb){
		if(node == null || sb == null) return;
		sb.append(node.val + ", ");
		depthFirstSearch(node.left, sb);
		depthFirstSearch(node.right, sb);
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
