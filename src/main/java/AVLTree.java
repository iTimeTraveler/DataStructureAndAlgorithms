package main.java;

import main.java.common.Tree;
import main.java.common.TreeNode;

public class AVLTree<T extends Comparable<T>> extends Tree{

	public AVLTree(TreeNode<T> root) {
		super(root);
	}
	
	/**
	 * 插入元素
	 * @param e
	 */
	public void insert(T e){
		root = insertInternal(root, e);
	}
	
	private TreeNode<T> insertInternal(TreeNode<T> root, T e){
		if(root == null){
			root = new TreeNode<T>(e);
		}else{
			int compare = e.compareTo(root.val);
			if(compare < 0){
				root.left = insertInternal(root.left, e);
				if(Tree.maxHeight(root.left) - Tree.maxHeight(root.right) == 2){
					if(e.compareTo(root.left.val) < 0){
						//LL型
						root = leftLeftRotate(root);
					}else{
						//LR型
						root = leftRightRotate(root);
					}
				}
			}else if(compare > 0){
				root.right = insertInternal(root.right, e);
				if(Tree.maxHeight(root.right) - Tree.maxHeight(root.left) == 2){
					if(e.compareTo(root.right.val) > 0){
						//RR型
						root = rightRightRotate(root);
					}else{
						//RL型
						root = rightLeftRotate(root);
					}
				}
			}else{
				System.out.println("Insert failed, Node:["+ e.toString() +"] has been existed.");
			}
		}
		return root;
	}
	
	//LL型平衡旋转
	private TreeNode<T> leftLeftRotate(TreeNode<T> root) {
		TreeNode<T> l = root.left;
		root.left = l.right;
		l.right = root;
		return l;
	}
	
	//LR型平衡旋转
	private TreeNode<T> leftRightRotate(TreeNode<T> root) {
		root.left = rightRightRotate(root.left);
		return leftLeftRotate(root);
	}
	
	//RR型平衡旋转
	private TreeNode<T> rightRightRotate(TreeNode<T> root) {
		TreeNode<T> r = root.right;
		root.right = r.left;
		r.left = root;
		return r;
	}

	//RL型平衡旋转
	private TreeNode<T> rightLeftRotate(TreeNode<T> root) {
		root.right = leftLeftRotate(root.right);
		return rightRightRotate(root);
	}

	/**
	 * 删除元素
	 * @param e
	 */
	public void delete(T e){
	}
	
	/**
	 * 查找元素
	 * @param e
	 */
	public TreeNode<T> search(T key){
		if(root == null) return null;
		
		TreeNode<T> node = root;
		while(node != null){
			int compare = key.compareTo(node.val);
			if(compare < 0){
				node = node.left;
			}else if(compare > 0){
				node = node.right;
			}else{
				return node;
			}
		}
		return null;
	}
}
