package common;

import java.util.LinkedList;
import java.util.Queue;

public class Tree<T extends Comparable<?>>{
	public TreeNode<T> root;

	public Tree(TreeNode<T> root){
		this.root = root;
	}

	/**
	 * 中序遍历
	 */
	public static <T extends Comparable<?>> String midTraverse(TreeNode<T> node){
		if(node == null) return null;
		return ((node.left != null ? Tree.midTraverse(node.left) + ", " : "")
				+ String.valueOf(node.val) + ", "
				+ (node.right != null ? Tree.midTraverse(node.right) + ", " : ""));
	}

	/**
	 * 先序遍历
	 */
	public static <T extends Comparable<?>> String preTraverse(TreeNode<T> node){
		if(node == null) return null;
		return (String.valueOf(node.val) + ", "
				+ (node.left != null ? Tree.preTraverse(node.left) + ", " : "")
				+ (node.right != null ? Tree.preTraverse(node.right) + ", " : ""));
	}

	/**
	 * 后序遍历
	 */
	public static <T extends Comparable<?>> String lastTraverse(TreeNode<T> node){
		if(node == null) return null;
		return ((node.left != null ? Tree.lastTraverse(node.left) + ", " : "")
				+ (node.right != null ? Tree.lastTraverse(node.right) + ", " : "")
				+ String.valueOf(node.val) + ", ");
	}

	public static <T extends Comparable<?>> int maxHeight(TreeNode<T> node) {
		if (node == null)
			return 0;
		return Math.max(Tree.maxHeight(node.left), Tree.maxHeight(node.right)) + 1;
	}

	public void print(){
		TreePrinter.print(root);
	}

	@Deprecated
	public void printTest(){
		if(root == null) return;

		System.out.println("=====================Tree========================");
		int level = 1;
		int nums = 0;
		int height = Tree.maxHeight(root);
		int wordWidth = root.getMaxWordWidth();
		String black = fillBlack("", wordWidth);
		Queue<TreeNode<T>> queue = new LinkedList<TreeNode<T>>();
		queue.offer(root);

		//计算最顶层间隔
		String interval = "   ";
		StringBuilder gap = new StringBuilder(interval);
		for (int i = 0; i < height; i++){
			String s = new String(gap);
			gap = new StringBuilder(s + s);
		}

		System.out.print(gap.substring(gap.length()/2));
		while(!queue.isEmpty()){
			//下一层级, 换行
			if(nums > (Math.pow(2, level) - 2)){
				level++;
				gap = new StringBuilder(gap.substring(gap.length()/2));
				System.out.print("\n" + gap.substring(gap.length()/2));
			}

			TreeNode<T> n = queue.poll();
			if(n == null){		//空节点占位符
				System.out.print(black + gap);
				nums++;
				continue;
			}
			System.out.print(fillBlack(n.val.toString(), wordWidth) + gap.toString());
			nums++;
			queue.offer(n.left);
			queue.offer(n.right);
		}
		System.out.println("\n=====================Tree========================");
	}

	private String fillBlack(String str, int maxlength){
		StringBuilder gap = new StringBuilder(str);
		if(str != null && !"".equals(str)){
			maxlength = maxlength - str.length();
		}
		for (int i = 0; i < maxlength; i++){
			gap.append(" ");
		}
		return gap.toString();
	}
}
