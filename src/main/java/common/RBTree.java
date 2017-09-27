package common;

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
	private static boolean BLACK = true;
	private static boolean RED = false;

	public RBTree(TreeNode<E> root) {
		super(root);
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

			fixAfterInsertion(node);
		}
	}
	
	/**
	 * 只有在父节点为红色节点的时候是需要插入修复操作，分为以下的三种情况
	 * 
	 * case 1. 叔叔节点也为红色。（将父节点和叔叔节点与祖父节点的颜色互换）
	 * case 2. 叔叔节点为空，且祖父节点、父节点和新节点处于一条斜线上。（将B父节点进行旋转操作，并且和祖父节点A互换颜色）
	 * case 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上。（将C当前节点进行旋转，然后就成了上面这个case 2）
	 * @param e
	 */
	private void fixAfterInsertion(TreeNode<E> e){
		if(e == null) return;
		
		while(e != null && e != root && e.parent != null && e.parent.isRed()){
			TreeNode<E> parent = e.parent;
			TreeNode<E> uncle = getUncle(e, parent);
			TreeNode<E> grandfather = parent.parent;
			
			//grandfather must be NOT null, because parent color is Red and could NOT be root.
			if(uncle == null){
				if(parent == grandfather.left){
					boolean isLeft = (e == parent.left);
					if(isLeft){
						//LL型: case 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
						grandfather = leftLeftRotate(grandfather);
					}else{
						//LR型: case 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						grandfather = leftRightRotate(grandfather);
					}
					grandfather.setColor(TreeNode.BLACK);
					grandfather.right.setColor(TreeNode.RED);
					
					//互换颜色可能导致上层规则被破坏，继续向上追溯
					e = grandfather;
				}else{
					boolean isRight = (e == parent.right);
					if(isRight){
						//RR型: case 2. 叔叔节点为空，且祖父节点、父节点和新节点在一条斜线上
						grandfather = rightRightRotate(grandfather);
					}else{
						//RL型: case 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
						grandfather = rightLeftRotate(grandfather);
					}
					grandfather.setColor(TreeNode.BLACK);
					grandfather.left.setColor(TreeNode.RED);
					
					//互换颜色可能导致上层规则被破坏，继续向上追溯
					e = grandfather;
				}
			}else{	//case 1. 叔叔节点也为红色（不可能既不为空还为黑色）
				parent.setColor(TreeNode.BLACK);
				uncle.setColor(TreeNode.BLACK);
				grandfather.setColor(TreeNode.RED);
				
				e = grandfather;
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

		TreeNode<E> node = root;
		while(node != null){
			int compare = e.compareTo(node.val);
			if(compare < 0){
				node = node.left;
			}else if(compare > 0){
				node = node.right;
			}else{
				boolean isOriginalBlack = !node.isRed();
				TreeNode<E> replacement = node;
				TreeNode<E> removeNode = node;
				TreeNode<E> parent = node.parent;
				if(node.left == null && node.right == null){
					// 如果左右子树均为空：直接删除
					if(isOriginalBlack){
						fixAfterDeletion(node);
					}

					if(parent != null){
						if(node == parent.left){
							parent.left = null;
						}else if(node == parent.right){
							parent.right = null;
						}
						node.parent = null;
					}else{
						root = null;
					}
					return;
				}else if(node.left != null && node.right == null){
					// 如果只有左子树：把左子树提上来替换掉要被删除的node
					replacement = node.left;
					replaceWithUniqueSon(node, node.left);
				}else if(node.right != null && node.left == null){
					// 如果只有右子树：把右子树提上来替换掉要被删除的node
					replacement = node.right;
					replaceWithUniqueSon(node, node.right);
				}else{
					// 如果左右子树都存在：从右子树中找到最小值节点替换到需要被删除的地方
					removeNode = node.right;
					while(removeNode.left != null){
						removeNode = removeNode.left;
					}

					isOriginalBlack = !removeNode.isRed();
					replacement = removeNode.right;
					if(removeNode == node.right){	//右子树没有左孩子
						replaceWithUniqueSon(node, node.right);
					}else{
						// 使用node的后继节点替代node，并删掉这个后继节点
						node.val = removeNode.val;

						if(removeNode.right == null){
							if(removeNode == removeNode.parent.left){
								removeNode.parent.left = null;
							}else if(removeNode == removeNode.parent.right){
								removeNode.parent.right = null;
							}
						}else{
							replaceWithUniqueSon(removeNode, removeNode.right);
						}
					}
				}

				// 修复操作
				if(isOriginalBlack){
					// x 要么是一个红节点，要么是一个 NIL 的叶节点，而不可能是一个内部节点
					fixAfterDeletion(replacement);
				}

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
	private void replaceWithUniqueSon(TreeNode<E> old, TreeNode<E> newNode){
		if(old == null || newNode == null) return;
		TreeNode<E> newNodeParent = newNode.parent;
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

		TreeNode<E> oldParent = old.parent;
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
	private void fixAfterDeletion(TreeNode<E> x){
		while(x != root && !x.isRed()){
			if(x == leftOf(parentOf(x))){
				TreeNode<E> brother = rightOf(parentOf(x));

				// case 1：兄弟节点为红色，此时父节点和兄弟节点的子节点均为黑（交换brother和parent颜色，并旋转，就转化成了case2）
				if(colorOf(brother) == RED){
					setColor(brother, BLACK);
					setColor(parentOf(x), RED);
					rightRightRotate(parentOf(x));
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
						leftLeftRotate(brother);
					}
					// case 4：兄弟节点是黑色，但是兄弟节点的右子是红色，左子的颜色任意（调换brother和parent的颜色，并将brother->right涂黑, 再对parent进行一次左旋）
					setColor(brother, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(brother), BLACK);
					rightRightRotate(parentOf(x));
					x = root;	// 修复完成，退出
				}
			}else{
				TreeNode<E> brother = leftOf(parentOf(x));

				// case 1：兄弟节点为红色，此时父节点和兄弟节点的子节点均为黑（交换brother和parent颜色，并旋转，就转化成了case2）
				if(colorOf(brother) == RED){
					setColor(brother, BLACK);
					setColor(parentOf(x), RED);
					leftLeftRotate(parentOf(x));
					brother = leftOf(parentOf(x));
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
						leftLeftRotate(brother);
					}
					// case 4：兄弟节点是黑色，但是兄弟节点的右子是红色，左子的颜色任意（调换brother和parent的颜色，并将brother->right涂黑, 再对parent进行一次左旋）
					setColor(brother, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(brother), BLACK);
					rightRightRotate(parentOf(x));
					x = root;	// 修复完成，退出
				}
			}
		}
	}
	
	
	/**
	 * 获取叔叔节点
	 * @param e
	 * @return
	 */
	private TreeNode<E> getUncle(TreeNode<E> e, TreeNode<E> parent){
		parent = (e == null ? parent : e.parent);
		if(parent == null) return null;
		if(e == null) return parent.left == null ? parent.right : parent.left;

		TreeNode<E> grandFather = parent.parent;
		if(grandFather == null) return null;
		if(parent == grandFather.left){
			return grandFather.right;
		}else if(parent == grandFather.right){
			return grandFather.left;
		}
		return null;
	}
	
	private static <E extends Comparable<?>> TreeNode<E> leftOf(TreeNode<E> e){
		return (e == null) ? null : e.left;
	}

	private static <E extends Comparable<?>> TreeNode<E> rightOf(TreeNode<E> e){
		return (e == null) ? null : e.right;
	}

	private static <E extends Comparable<?>> TreeNode<E> parentOf(TreeNode<E> e){
		return (e == null) ? null : e.parent;
	}

	private static <E extends Comparable<?>> void setColor(TreeNode<E> e, boolean isBlack){
		if(e != null){
			e.setColor(isBlack ? TreeNode.BLACK : TreeNode.RED);
		}
	}

	private static <E extends Comparable<?>> boolean colorOf(TreeNode<E> e){
		return (e == null) || !(e.getColor() == TreeNode.RED);
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
		}else{
			this.root = l;
		}
		root = l;
		return root;
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
		}else{
			this.root = r;
		}
		root = r;
		return root;
	}

	//RL型平衡旋转
	private TreeNode<E> rightLeftRotate(TreeNode<E> root) {
		root.right = leftLeftRotate(root.right);
		return rightRightRotate(root);
	}
}
