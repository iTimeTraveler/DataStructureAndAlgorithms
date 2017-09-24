package main.java.common;

/**
 * RBTree红黑树的定义如下:
 * 
 * 1. 任何一个节点都有颜色，黑色或者红色
 * 2. 根节点是黑色的
 * 3. 父子节点之间不能出现两个连续的红节点
 * 4. 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等
 * 5. 空节点被认为是黑色的
 */
public class RBTree<E extends Comparable<E>> extends Tree<E>{

	public RBTree(TreeNode<E> root) {
		super(root);
	}
	
	/**
	 * 新增节点
	 * @param e
	 */
	public void insert(E e){
		if(root == null){
			root = new TreeNode<E>(e);
			root.setColor(TreeNode.BLACK);
			root.parent = null;
		}else{
			TreeNode<E> node = root;
			while(node != null){
				int compare = e.compareTo(node.val);
				if(compare < 0){
					if(node.left != null){
						node = node.left;
					}else{
						TreeNode<E> tmp = new TreeNode<E>(e);
						tmp.setColor(TreeNode.RED);		//new node color is red.
						tmp.parent = node;
						
						node.left = tmp;
						node = node.left;
						break;
					}
				}else if(compare > 0){
					if(node.right != null){
						node = node.right;
					}else{
						TreeNode<E> tmp = new TreeNode<E>(e);
						tmp.setColor(TreeNode.RED);		//new node color is red.
						tmp.parent = node;
						
						node.right = tmp;
						node = node.right;
						break;
					}
				}else{
					System.out.println("Insert failed, Node:["+ e.toString() +"] has been existed.");
					break;
				}
			}
			
			fixupInsert(node);
		}
	}
	
	/**
	 * 只有在父节点为红色节点的时候是需要插入修复操作，分为以下的三种情况
	 * 
	 * 1. 叔叔节点也为红色。（将父节点和叔叔节点与祖父节点的颜色互换）
	 * 2. 叔叔节点为空，且祖父节点、父节点和新节点处于一条斜线上。（将B父节点进行旋转操作，并且和祖父节点A互换颜色）
	 * 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上。（将C当前节点进行旋转，然后就成了上面这个case 2）
	 * @param e
	 */
	private void fixupInsert(TreeNode<E> e){
		if(e == null) return;
		
		TreeNode<E> parent = e.parent;
		while(parent != null && parent.isRed()){
			TreeNode<E> uncle = getUncle(e);
			TreeNode<E> grandfather = parent.parent;
			
			//grandfather must be NOT null, because parent color is Red and could NOT be root.
			if(uncle == null){
				if(parent == grandfather.left){
					boolean isLeft = (e == parent.left);
					if(isLeft){
						//LL型: 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
						grandfather = leftLeftRotate(grandfather);
					}else{
						//LR型: 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						grandfather = leftRightRotate(grandfather);
					}
					grandfather.setColor(TreeNode.BLACK);
					grandfather.right.setColor(TreeNode.RED);
					
					//互换颜色可能导致上层规则被破坏，继续向上追溯
					parent = grandfather.parent;
				}else{
					boolean isRight = (e == parent.right);
					if(isRight){
						//RR型: 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
						grandfather = rightRightRotate(grandfather);
					}else{
						//RL型: 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						grandfather = rightLeftRotate(grandfather);
					}
					grandfather.setColor(TreeNode.BLACK);
					grandfather.left.setColor(TreeNode.RED);
					
					//互换颜色可能导致上层规则被破坏，继续向上追溯
					parent = grandfather.parent;
				}
			}else{	//1. 叔叔节点也为红色
				parent.setColor(TreeNode.BLACK);
				uncle.setColor(TreeNode.BLACK);
				grandfather.setColor(TreeNode.RED);
				
				e = grandfather;
				parent = e.parent;
			}
		}
		// 保证根节点是黑色
		root.setColor(TreeNode.BLACK);
	}
	
	private TreeNode<E> getUncle(TreeNode<E> e){
		if(e == null) return null;
		TreeNode<E> parent = e.parent;
		
		if(parent == null) return null;
		TreeNode<E> grandFather = parent.parent;
		
		if(grandFather == null) return null;
		if(parent == grandFather.left){
			return grandFather.right;
		}else if(parent == grandFather.right){
			return grandFather.left;
		}
		return null;
	}
	
	//LL型平衡旋转
	private TreeNode<E> leftLeftRotate(TreeNode<E> root) {
		TreeNode<E> rootParent = root.parent;
		TreeNode<E> l = root.left;		//new root is l.
		l.parent = rootParent;
		
		root.left = l.right;
		if(l.right != null){
			l.right.parent = root;
		}
		
		l.right = root;
		root.parent = l;
		
		// 旋转之后产生了新的根节点r，它的父节点指针关系要从原来的旧root改为新的r.
		if(rootParent != null){
			if(root == rootParent.left){
				rootParent.left = l;
			}else{
				rootParent.right = l;
			}
		}
		return l;
	}

	//LR型平衡旋转
	private TreeNode<E> leftRightRotate(TreeNode<E> root) {
		root.left = rightRightRotate(root.left);
		return leftLeftRotate(root);
	}

	//RR型平衡旋转
	private TreeNode<E> rightRightRotate(TreeNode<E> root) {
		TreeNode<E> rootParent = root.parent;
		TreeNode<E> r = root.right;		//new root is r.
		r.parent = rootParent;
		
		root.right = r.left;
		if(r.left != null){
			r.left.parent = root;
		}
		
		r.left = root;
		root.parent = r;
		
		// 旋转之后产生了新的根节点r，它的父节点指针关系要从原来的旧root改为新的r.
		if(rootParent != null){
			if(root == rootParent.left){
				rootParent.left = r;
			}else{
				rootParent.right = r;
			}
		}
		return r;
	}

	//RL型平衡旋转
	private TreeNode<E> rightLeftRotate(TreeNode<E> root) {
		root.right = leftLeftRotate(root.right);
		return rightRightRotate(root);
	}
}
