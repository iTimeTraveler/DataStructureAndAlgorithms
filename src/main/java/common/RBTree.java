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
			root.isRed = false;
			root.parent = null;
		}else{
			TreeNode<E> node = root;
			while(true){
				if(node == null) break;
				int compare = e.compareTo(node.val);
				if(compare < 0){
					if(node.left != null){
						node = node.left;
					}else{
						TreeNode<E> tmp = new TreeNode<E>(e);
						tmp.isRed = true;		//new node color is red.
						tmp.parent = node;
						node.left = tmp;
						break;
					}
				}else if(compare > 0){
					if(node.right != null){
						node = node.right;
					}else{
						TreeNode<E> tmp = new TreeNode<E>(e);
						tmp.isRed = true;		//new node color is red.
						tmp.parent = node;
						node.right = tmp;
						break;
					}
				}else{
					System.out.println("Insert failed, Node:["+ e.toString() +"] has been existed.");
					break;
				}
			}
			
			fixInsert(node);
		}
	}
	
	/**
	 * 只有在父节点为红色节点的时候是需要插入修复操作，分为以下的三种情况
	 * 
	 * 1. 叔叔节点也为红色。
	 * 2. 叔叔节点为空，且祖父节点、父节点和新节点处于一条斜线上。（将B父节点进行旋转操作，并且和祖父节点A互换颜色）
	 * 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上。（将C当前节点进行旋转，然后就成了上面这个case 2）
	 * @param e
	 */
	private void fixInsert(TreeNode<E> e){
		if(e == null) return;
		
		TreeNode<E> parent = e.parent;
		while(parent != null && parent.isRed){
			TreeNode<E> uncle = getUncle(e);
			TreeNode<E> grandfather = parent.parent;
			
			//grandfather must be NOT null, because parent color is Red and could not be root.
			if(uncle == null){
				if(parent == grandfather.left){
					boolean isLeft = (e == parent.left);
					if(isLeft){
						//LL型: 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
						grandfather = leftLeftRotate(grandfather);
						grandfather.isRed = false;
						grandfather.right.isRed = true;
						parent = null;
					}else{
						//LR型: 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						grandfather = leftRightRotate(grandfather);
						grandfather.isRed = true;
						grandfather.right.isRed = false;
					}
					
				}else {
					boolean isRight = (e == parent.right);
					if(isRight){
						//RR型: 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
						grandfather = rightRightRotate(grandfather);
						grandfather.isRed = false;
						grandfather.left.isRed = true;
						parent = null;
					}else{
						//RL型: 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						grandfather = rightLeftRotate(grandfather);
						grandfather.isRed = true;
						grandfather.left.isRed = false;
					}
				}
				e = grandfather;
				parent = e.parent;
				parent = null;
			}else{	//1. 叔叔节点也为红色：将父节点和叔叔节点与祖父节点的颜色互换
				parent.isRed = false;
				uncle.isRed = false;
				grandfather.isRed = true;
				
				e = parent;
				parent = e.parent;
			}
		}
		root.isRed = false;
		root.parent = null;
	}
	
	private TreeNode<E> getUncle(TreeNode<E> e){
		if(e == null) return null;
		TreeNode<E> parent = e.parent;
		
		if(parent == null) return null;
		TreeNode<E> grandFather = parent.parent;
		
		if(grandFather == null) return null;
		if(e == grandFather.left){
			return grandFather.right;
		}else if(e == grandFather.right){
			return grandFather.left;
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
