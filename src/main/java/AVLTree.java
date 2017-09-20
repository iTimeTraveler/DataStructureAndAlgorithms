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
	
	private TreeNode<T> leftLeftRotate(TreeNode<T> root) {
		return null;
	}
	private TreeNode<T> leftRightRotate(TreeNode<T> root) {
		return null;
	}
	private TreeNode<T> rightRightRotate(TreeNode<T> root) {
		return null;
	}
	private TreeNode<T> rightLeftRotate(TreeNode<T> root) {
		return null;
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
	public TreeNode<T> search(T e){
		return null;
	}
}
