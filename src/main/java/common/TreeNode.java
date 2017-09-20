package common;

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

	/**
	 * 所有元素最大字符数
	 * @return
	 */
	public int getMaxWordWidth(){
		int vw = 0, lw = 0, rw = 0;
		if(val != null){
			vw = val.toString().length();
		}
		if(left != null){
			lw = left.getMaxWordWidth();
		}
		if(right != null){
			rw = right.getMaxWordWidth();
		}
		return Math.max(vw, Math.max(lw, rw));
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
