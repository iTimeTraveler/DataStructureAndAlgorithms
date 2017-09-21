package common;


public class AVLTree<E extends Comparable<E>> extends Tree<E>{

	public AVLTree(TreeNode<E> root) {
		super(root);
	}
	
	/**
	 * 插入元素
	 * @param e
	 */
	public void insert(E e){
		root = insertInternal(root, e);
	}
	
	private TreeNode<E> insertInternal(TreeNode<E> root, E e){
		if(root == null){
			root = new TreeNode<E>(e);
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
	
	/**
	 * 删除元素
	 * @param e
	 */
	public void delete(E e){
		root = deleteInternal(root, e);
	}

	private TreeNode<E> deleteInternal(TreeNode<E> root, E e){
		if(root == null) return null;

		//递归找到要删除的元素
		int compare = e.compareTo(root.val);
		if(compare < 0){
			root.left = deleteInternal(root.left, e);
		}else if(compare > 0){
			root.right = deleteInternal(root.right, e);
		}else{
			// 删除当前元素
			// 找到左子树最大值填充到当前位置，并删除左子树中的那个最大值
			if(root.left != null){
				TreeNode<E> l = root.left;
				while(l.right != null){
					l = l.right;
				}
				root.val = l.val;
				root.left = deleteInternal(root.left, root.val);
			}else if(root.right != null){	// 或者找到右子树最大值填充到当前位置，并删除右子树中的那个最大值
				TreeNode<E> r = root.right;
				while(r.left != null){
					r = r.left;
				}
				root.val = r.val;
				root.right = deleteInternal(root.right, root.val);
			}else{
				root = null;
			}
		}

		//平衡旋转
		if(root != null){
			if(Tree.maxHeight(root.left) - Tree.maxHeight(root.right) == 2){
				if(Tree.maxHeight(root.left.left) - Tree.maxHeight(root.left.right) == 1){
					//LL型
					root = leftLeftRotate(root);
				}else{
					//LR型
					root = leftRightRotate(root);
				}
			}else if(Tree.maxHeight(root.right) - Tree.maxHeight(root.left) == 2){
				if(Tree.maxHeight(root.right.right) - Tree.maxHeight(root.right.left) == 1){
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
	public TreeNode<E> search(E key){
		if(root == null) return null;
		
		TreeNode<E> node = root;
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
	private TreeNode<E> leftLeftRotate(TreeNode<E> root) {
		TreeNode<E> l = root.left;
		root.left = l.right;
		l.right = root;
		return l;
	}

	//LR型平衡旋转
	private TreeNode<E> leftRightRotate(TreeNode<E> root) {
		root.left = rightRightRotate(root.left);
		return leftLeftRotate(root);
	}

	//RR型平衡旋转
	private TreeNode<E> rightRightRotate(TreeNode<E> root) {
		TreeNode<E> r = root.right;
		root.right = r.left;
		r.left = root;
		return r;
	}

	//RL型平衡旋转
	private TreeNode<E> rightLeftRotate(TreeNode<E> root) {
		root.right = leftLeftRotate(root.right);
		return rightRightRotate(root);
	}
}
