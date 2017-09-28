package binarytree;


import java.util.Arrays;
import java.util.List;

import common.BSTreeNode;

/**
 * 平衡二叉树实现
 */
public class AVLTree<E extends Comparable<E>> extends BinaryTree<E> {

	public AVLTree(BSTreeNode<E> root) {
		super(root);
	}
	
	/**
	 * 插入元素
	 * @param e
	 */
	public void insert(E e){
		root = insertRecursion(root, e);
	}
	
	/**
	 * 递归执行元素插入
	 * @param root
	 * @param e
	 * @return
	 */
	private BSTreeNode<E> insertRecursion(BSTreeNode<E> root, E e){
		if(root == null){
			root = new BSTreeNode<E>(e);
		}else{
			int compare = e.compareTo(root.val);
			if(compare < 0){
				BSTreeNode<E> node = insertRecursion(root.left, e);
				node.parent = root;
				root.left = node;
				if(BinaryTree.maxHeight(root.left) - BinaryTree.maxHeight(root.right) == 2){
					if(e.compareTo(root.left.val) < 0){
						//LL型
						root = leftLeftRotate(root);
					}else{
						//LR型
						root = leftRightRotate(root);
					}
				}
			}else if(compare > 0){
				BSTreeNode<E> node = insertRecursion(root.right, e);
				node.parent = root;
				root.right = node;
				if(BinaryTree.maxHeight(root.right) - BinaryTree.maxHeight(root.left) == 2){
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
	
	/**
	 * 删除元素
	 * @param e
	 */
	public void delete(E e){
		root = deleteRecursion(root, e);
	}

	private BSTreeNode<E> deleteRecursion(BSTreeNode<E> root, E e){
		if(root == null) return null;

		//递归找到要删除的元素
		int compare = e.compareTo(root.val);
		if(compare < 0){
			root.left = deleteRecursion(root.left, e);
		}else if(compare > 0){
			root.right = deleteRecursion(root.right, e);
		}else{
			// 删除当前元素
			// 找到左子树最大值填充到当前位置，并删除左子树中的那个最大值
			// （此过程不会变动节点指针，所以不必考虑节点的parent）
			if(root.left != null){
				BSTreeNode<E> l = root.left;
				while(l.right != null){
					l = l.right;
				}
				root.val = l.val;
				root.left = deleteRecursion(root.left, root.val);
			}else if(root.right != null){	// 或者找到右子树最大值填充到当前位置，并删除右子树中的那个最大值
				BSTreeNode<E> r = root.right;
				while(r.left != null){
					r = r.left;
				}
				root.val = r.val;
				root.right = deleteRecursion(root.right, root.val);
			}else{
				root = null;
			}
		}

		//平衡旋转
		if(root != null){
			if(BinaryTree.maxHeight(root.left) - BinaryTree.maxHeight(root.right) == 2){
				if(BinaryTree.maxHeight(root.left.left) - BinaryTree.maxHeight(root.left.right) == 1){
					//LL型
					root = leftLeftRotate(root);
				}else{
					//LR型
					root = leftRightRotate(root);
				}
			}else if(BinaryTree.maxHeight(root.right) - BinaryTree.maxHeight(root.left) == 2){
				if(BinaryTree.maxHeight(root.right.right) - BinaryTree.maxHeight(root.right.left) == 1){
					//RR型
					root = rightRightRotate(root);
				}else{
					//RL型
					root = rightLeftRotate(root);
				}
			}
		}
		return root;
	}
	
	/**
	 * 查找元素
	 * @param key
	 */
	public BSTreeNode<E> search(E key){
		if(root == null) return null;
		
		BSTreeNode<E> node = root;
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

	//LL型平衡旋转
	private BSTreeNode<E> leftLeftRotate(BSTreeNode<E> root) {
		BSTreeNode<E> l = root.left;		//new root is l.
		l.parent = root.parent;
		
		root.left = l.right;
		if(l.right != null){
			l.right.parent = root;
		}
		
		l.right = root;
		root.parent = l;
		return l;
	}

	//LR型平衡旋转
	private BSTreeNode<E> leftRightRotate(BSTreeNode<E> root) {
		root.left = rightRightRotate(root.left);
		return leftLeftRotate(root);
	}

	//RR型平衡旋转
	private BSTreeNode<E> rightRightRotate(BSTreeNode<E> root) {
		BSTreeNode<E> r = root.right;		//new root is r.
		r.parent = root.parent;
		
		root.right = r.left;
		if(r.left != null){
			r.left.parent = root;
		}
		
		r.left = root;
		root.parent = r;
		return r;
	}

	//RL型平衡旋转
	private BSTreeNode<E> rightLeftRotate(BSTreeNode<E> root) {
		root.right = leftLeftRotate(root.right);
		return rightRightRotate(root);
	}

	public static void main(String[] args){
//		List<Integer> nums = Arrays.asList(1,2,3,4,6,7,8,9,10,12,13,14,16,18,19,20,36);
//		List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8);
		List<Integer> nums = Arrays.asList(6,2,9,4,5,7,3,8,1);

		AVLTree<Integer> avlTree = new AVLTree<Integer>(null);
		for(int i=0; i<nums.size(); i++) {
			avlTree.insert(nums.get(i));
		}
		System.out.println(avlTree.printDFS());
		System.out.println(avlTree.printBFS());
		System.out.println(BinaryTree.maxHeight(avlTree.root));
		System.out.println(BinaryTree.midTraverse(avlTree.root));
		System.out.println(BinaryTree.preTraverse(avlTree.root));
		System.out.println(BinaryTree.lastTraverse(avlTree.root));
		BSTPrinter.printTree(avlTree.root);
		BSTPrinter.printTree(avlTree.search(4));

		avlTree.delete(4);
		BSTPrinter.printRBTreeDetails(avlTree.root);
	}
}
