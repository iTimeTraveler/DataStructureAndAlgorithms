package binarytree;

import java.util.Arrays;
import java.util.List;

import common.BSTreeNode;

/**
 * 红黑树RBTree的定义如下:
 * 
 * 1. 任何一个节点都有颜色，黑色或者红色
 * 2. 根节点是黑色的
 * 3. 父子节点之间不能出现两个连续的红节点
 * 4. 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等
 * 5. 空节点被认为是黑色的
 */
public class RBTree<E extends Comparable<E>> extends BinaryTree<E> {
	private static boolean BLACK = true;
	private static boolean RED = false;

	public RBTree(BSTreeNode<E> root) {
		super(root);
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
	
	/**
	 * 新增节点
	 * @param e
	 */
	public void insert(E e){
		if(root == null){
			root = new BSTreeNode<E>(e);
			root.setColor(BSTreeNode.BLACK);
			root.parent = null;
		}else{
			BSTreeNode<E> node = root;
			while(node != null){
				int compare = e.compareTo(node.val);
				if(compare < 0){
					if(node.left != null){
						node = node.left;
					}else{
						BSTreeNode<E> tmp = new BSTreeNode<E>(e);
						tmp.setColor(BSTreeNode.RED);		//new node color is red.
						tmp.parent = node;
						
						node.left = tmp;
						node = node.left;
						break;
					}
				}else if(compare > 0){
					if(node.right != null){
						node = node.right;
					}else{
						BSTreeNode<E> tmp = new BSTreeNode<E>(e);
						tmp.setColor(BSTreeNode.RED);		//new node color is red.
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

			fixAfterInsertion(node);
		}
	}
	
	/**
	 * 只有在父节点为红色节点的时候是需要插入修复操作，分为以下的三种情况
	 * 
	 * case 1. 叔叔节点也为红色。（将父节点和叔叔节点与祖父节点的颜色互换）
	 * case 2. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上。（将C当前节点进行旋转，然后就成了这个case 3）
	 * case 3. 叔叔节点为空，且祖父节点、父节点和新节点处于一条斜线上。（将B父节点进行旋转操作，并且和祖父节点A互换颜色）
	 * @param x
	 */
	private void fixAfterInsertion(BSTreeNode<E> x){
		if(x == null) return;
		
		while(x.parent != null && x.parent.isRed()){
			BSTreeNode<E> parent = x.parent;
			BSTreeNode<E> uncle = getUncle(x, parent);
			BSTreeNode<E> grandfather = parent.parent;
			
			//grandfather must be NOT null, because parent color is Red and could NOT be root.
			if(uncle == null || colorOf(uncle) == BLACK){
				if(parent == grandfather.left){
					boolean isRight = (x == parent.right);
					if(!isRight){
						//LR型: case 2. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						rotateToLeft(parent);
					}
					//LL型: case 3. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
					rotateToRight(grandfather);
					
					//互换颜色可能导致上层规则被破坏，继续向上追溯
					if(isRight){
						setColor(x, BLACK);
					}else{
						setColor(parent, BLACK);
					}
					setColor(grandfather, RED);
				}else{
					boolean isLeft = (x == parent.left);
					if(isLeft){
						//RL型: case 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						rotateToRight(parent);
					}
					//RR型: case 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
					rotateToLeft(grandfather);
					
					//互换颜色可能导致上层规则被破坏，继续向上追溯
					if(isLeft){
						setColor(x, BLACK);
					}else{
						setColor(parent, BLACK);
					}
					setColor(grandfather, RED);
				}
			}else{	//case 1. 叔叔节点也为红色
				parent.setColor(BSTreeNode.BLACK);
				uncle.setColor(BSTreeNode.BLACK);
				grandfather.setColor(BSTreeNode.RED);
				
				x = grandfather;
			}
		}
		// 保证根节点是黑色
		setColor(root, BLACK);
	}
	
	/**
	 * 删除节点
	 * @param e
	 */
	public void delete(E e){
		if(root == null) return;

		BSTreeNode<E> node = root;
		while(node != null){
			int compare = e.compareTo(node.val);
			if(compare < 0){
				node = node.left;
			}else if(compare > 0){
				node = node.right;
			}else{
				boolean isOriginalBlack = !node.isRed();
				BSTreeNode<E> replacement = node;
				BSTreeNode<E> removeNode = node;
				BSTreeNode<E> parent = node.parent;
				
				// 如果左右子树都存在：从右子树中找到最小值节点替换到需要被删除的地方
				if(node.left != null && node.right != null){
					removeNode = node.right;
					while(removeNode.left != null){
						removeNode = removeNode.left;
					}
					
					// 使用node的后继节点替代node，并删掉这个后继节点
					node.val = removeNode.val;
					node = removeNode;
					isOriginalBlack = !removeNode.isRed();
				}
				
				// 如果只有左子树：把左子树提上来替换掉要被删除的node
				// 如果只有右子树：把右子树提上来替换掉要被删除的node
				replacement = (node.left != null) ? node.left : node.right;
				if(replacement != null){
					replaceWithUniqueSon(node, replacement);
					
					// 修复操作
					if(isOriginalBlack){
						// x 要么是一个红节点，要么是一个 NIL 的叶节点，而不可能是一个内部节点
						fixAfterDeletion(replacement);
					}
				}else{
					// 如果左右子树均为空：直接删除
					if(node.parent != null){
						if(isOriginalBlack){
							fixAfterDeletion(node);
						}
						if(node == node.parent.left){
							node.parent.left = null;
						}else if(node == node.parent.right){
							node.parent.right = null;
						}
						node.parent = null;
					}else{
						root = null;
					}
				}
				
//				else if(node.left != null && node.right == null){
//					replacement = node.left;
//					replaceWithUniqueSon(node, node.left);
//				}else if(node.right != null && node.left == null){
//					replacement = node.right;
//					replaceWithUniqueSon(node, node.right);
//				}else{
//					
//					replacement = removeNode.right;
//					if(removeNode == node.right){	//右子树没有左孩子
//						replaceWithUniqueSon(node, node.right);
//					}else{
//						// 使用node的后继节点替代node，并删掉这个后继节点
//						node.val = removeNode.val;
//
//						if(removeNode.right == null){
//							if(removeNode == removeNode.parent.left){
//								removeNode.parent.left = null;
//							}else if(removeNode == removeNode.parent.right){
//								removeNode.parent.right = null;
//							}
//						}else{
//							replaceWithUniqueSon(removeNode, removeNode.right);
//						}
//					}
//				}

				// 保证根节点是黑色
				setColor(root, BLACK);
				return;
			}
		}
	}

	/**
	 * 替代功能：如下例子
	 *         2
	 *        / \
	 *       /   \
	 *      /     \
	 *     1       old
	 *    / \       \
	 *   0   7       new
	 *  /   / \     / \
	 * 2   1   0   8   9
	 *        /
	 *       7
	 *
	 * @param old  old
	 * @param newNode  new
	 */
	private void replaceWithUniqueSon(BSTreeNode<E> old, BSTreeNode<E> newNode){
		if(old == null || newNode == null) return;
		BSTreeNode<E> newNodeParent = newNode.parent;
		if(newNodeParent == old){		//如果是用旧节点的子节点替换它，防止父子关系出现死循环
			if(newNode == newNodeParent.left){
				newNode.right = old.right;
				newNode.parent = old.parent;
				if(old.right != null){
					old.right.parent = newNode;
				}
			}else if(newNode == newNodeParent.right){
				newNode.left = old.left;
				newNode.parent = old.parent;
				if(old.left != null){
					old.left.parent = newNode;
				}
			}
		}else{
			newNode.left = old.left;
			newNode.right = old.right;
			newNode.parent = old.parent;
		}
		newNode.setColor(old.getColor());

		BSTreeNode<E> oldParent = old.parent;
		if(oldParent != null){
			if(old == oldParent.left){
				oldParent.left = newNode;
			}else if(old == oldParent.right){
				oldParent.right = newNode;
			}
		}

		old.left = old.right = old.parent = null;
	}
	
	/**
	 * 删除修复操作是针对删除黑色节点才有的
	 *
	 * case 1：兄弟节点为红色（此时父节点和兄弟节点的子节点均为黑）
	 * case 2：兄弟节点是黑色，且兄弟节点的两个子节点全为黑色
	 * case 3：兄弟节点是黑色，兄弟的左子是红色，右子是黑色
	 * case 4：兄弟节点是黑色，但是兄弟节点的右子是红色，左子的颜色任意
	 *
	 * 删除修复操作在遇到被删除的节点是红色节点或者到达root节点时，修复操作完毕。
	 * @param x
	 */
	private void fixAfterDeletion(BSTreeNode<E> x){
		while(x != root && colorOf(x) == BLACK){
			if(x == leftOf(parentOf(x))){
				BSTreeNode<E> brother = rightOf(parentOf(x));

				// case 1：兄弟节点为红色，此时父节点和兄弟节点的子节点均为黑（交换brother和parent颜色，并旋转，就转化成了case2）
				if(colorOf(brother) == RED){
					setColor(brother, BLACK);
					setColor(parentOf(x), RED);
					rotateToLeft(parentOf(x));
					brother = rightOf(parentOf(x));
				}
				// case 2：兄弟节点是黑色，且兄弟节点的两个子节点全为黑色（将兄弟节点涂红，然后往上递归）
				if(colorOf(leftOf(brother)) == BLACK && colorOf(rightOf(brother)) == BLACK){
					setColor(brother, RED);
					x = parentOf(x);
				}else{
					// case 3：兄弟节点是黑色，兄弟的左子是红色，右子是黑色（brother->left是红色，我们就将其涂黑，然后将brother涂红，再对brother进行右旋，就转化成了case4）
					if(colorOf(rightOf(brother)) == BLACK){
						setColor(brother, RED);
						setColor(leftOf(brother), BLACK);
						rotateToRight(brother);
						brother = rightOf(parentOf(x));
					}
					// case 4：兄弟节点是黑色，但是兄弟节点的右子是红色，左子的颜色任意（为了借用兄弟节点的黑色填充到被删黑节点所在的子树，调换brother和parent的颜色，并将brother->right涂黑, 再对parent进行一次左旋）
					setColor(brother, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(brother), BLACK);
					rotateToLeft(parentOf(x));
					x = root;	// 修复完成，退出
				}
			}else{
				BSTreeNode<E> brother = leftOf(parentOf(x));

				// case 1：兄弟节点为红色，此时父节点和兄弟节点的子节点均为黑（交换brother和parent颜色，并旋转，就转化成了case2）
				if(colorOf(brother) == RED){
					setColor(brother, BLACK);
					setColor(parentOf(x), RED);
					rotateToRight(parentOf(x));
					brother = leftOf(parentOf(x));
				}
				// case 2：兄弟节点是黑色，且兄弟节点的两个子节点全为黑色（将兄弟节点涂红，然后往上递归）
				if(colorOf(leftOf(brother)) == BLACK && colorOf(rightOf(brother)) == BLACK){
					setColor(brother, RED);
					x = parentOf(x);
				}else{
					// case 3：兄弟节点是黑色，兄弟的左子是红色，右子是黑色（brother->left是红色，我们就将其涂黑，然后将brother涂红，再对brother进行右旋，就转化成了case4）
					if(colorOf(leftOf(brother)) == BLACK){
						setColor(brother, RED);
						setColor(rightOf(brother), BLACK);
						rotateToLeft(brother);
						brother = leftOf(parentOf(x));
					}
					// case 4：兄弟节点是黑色，但是兄弟节点的右子是红色，左子的颜色任意（调换brother和parent的颜色，并将brother->right涂黑, 再对parent进行一次左旋）
					setColor(brother, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(leftOf(brother), BLACK);
					rotateToRight(parentOf(x));
					x = root;	// 修复完成，退出
				}
			}
		}
		// 将x设置为黑色
		setColor(x, BLACK);
	}
	
	
	/**
	 * 获取叔叔节点
	 * @param e
	 * @return
	 */
	private BSTreeNode<E> getUncle(BSTreeNode<E> e, BSTreeNode<E> parent){
		parent = (e == null ? parent : e.parent);
		if(parent == null) return null;
		if(e == null) return parent.left == null ? parent.right : parent.left;

		BSTreeNode<E> grandFather = parent.parent;
		if(grandFather == null) return null;
		if(parent == grandFather.left){
			return grandFather.right;
		}else if(parent == grandFather.right){
			return grandFather.left;
		}
		return null;
	}
	
	private static <E extends Comparable<?>> BSTreeNode<E> leftOf(BSTreeNode<E> e){
		return (e == null) ? null : e.left;
	}

	private static <E extends Comparable<?>> BSTreeNode<E> rightOf(BSTreeNode<E> e){
		return (e == null) ? null : e.right;
	}

	private static <E extends Comparable<?>> BSTreeNode<E> parentOf(BSTreeNode<E> e){
		return (e == null) ? null : e.parent;
	}

	private static <E extends Comparable<?>> void setColor(BSTreeNode<E> e, boolean isBlack){
		if(e != null){
			e.setColor(isBlack ? BSTreeNode.BLACK : BSTreeNode.RED);
		}
	}

	private static <E extends Comparable<?>> boolean colorOf(BSTreeNode<E> e){
		return (e == null) || !(e.getColor() == BSTreeNode.RED);
	}

	//LL型平衡旋转
	private BSTreeNode<E> rotateToRight(BSTreeNode<E> root) {
		BSTreeNode<E> rootParent = root.parent;
		BSTreeNode<E> l = root.left;		//new root is l.
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
		}else{
			this.root = l;
		}
		return l;
	}

	//RR型平衡旋转
	private BSTreeNode<E> rotateToLeft(BSTreeNode<E> root) {
		BSTreeNode<E> rootParent = root.parent;
		BSTreeNode<E> r = root.right;		//new root is r.
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
		}else{
			this.root = r;
		}
		return r;
	}

	public static void main(String[] args){
//		List<Integer> nums = Arrays.asList(1,2,3,4,6,7,8,9,10,12,13,14,16,18,19,20,36);
//		List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8);
		List<Integer> nums = Arrays.asList(6,2,9,4,5,7,3,8,1);

		RBTree<Integer> rbTree = new RBTree<Integer>(null);
		for(int i=0; i<nums.size(); i++) {
			rbTree.insert(nums.get(i));
		}

		BSTPrinter.printTree(rbTree.root);
		BSTPrinter.printRBTreeDetails(rbTree.root);

		List<Integer> dels = Arrays.asList(2,4,7,9,5);
		for(int i=0; i<dels.size(); i++) {
			System.out.println("===================delete " + dels.get(i) + "==================");
			rbTree.delete(dels.get(i));
			BSTPrinter.printTree(rbTree.root);
			BSTPrinter.printRBTreeDetails(rbTree.root);
		}
	}
}
