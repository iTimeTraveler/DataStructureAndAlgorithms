package reference;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 */
public class TreeNode<T> {

	// 父节点
	protected TreeNode<T> parentNode;
	// 上一个节点
	protected TreeNode<T> prevSibling;
	// 所有子节点
	protected List<TreeNode<T>> children;
	//在兄弟节点列表中的索引
	protected int index = -1;
	protected T bindData;
	//是否可见的，用于节点遍历时，是否处理此节点及其后代节点，默认可见
	protected boolean visible = true;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public TreeNode() {
		children = new ArrayList<>();
	}

	public TreeNode(T bindData) {
		this();
		this.bindData = bindData;
	}

	/**
	 * 获取此节点中指定的孩子节点
	 *
	 * @param index 孩子节点列表索引
	 * @return 孩子节点
	 */
	public TreeNode<T> getChildNode(int index) {
		if (index < 0 || index > children.size() - 1) {
			return null;
		}
		return children.get(index);
	}

	/**
	 * 是否有子节点
	 *
	 * @return true为存在子节点
	 */
	public boolean hasChildren() {
		if (children == null) {
			return false;
		}
		return !children.isEmpty();
	}

	/**
	 * 获取节点附带的数据
	 *
	 * @return 附带数据
	 */
	public T getBindData() {
		return bindData;
	}

	/**
	 * 添加一个孩子节点
	 *
	 * @param node 待添加的孩子节点
	 */
	public void appendChildNode(TreeNode<T> node) {
		node.index = children.size();
		children.add(node);
		node.parentNode = this;
		node.prevSibling = getChildNode(node.index - 1);
	}

	/**
	 * 移除此节点中指定的孩子节点（从孩子节点集合中移除节点数据）
	 *
	 * @param index 孩子节点列表索引
	 */
	public void removeChildNode(int index) {
		children.remove(index);
		int size = children.size();
		for (int i = 0; i < size; i++) {
			children.get(i).index = i;
		}
	}

	/**
	 * 移除此节点中指定的孩子节点（从孩子节点集合中移除节点数据）
	 *
	 * @param node 孩子节点，直接子节点
	 */
	public void removeChildNode(TreeNode<T> node) {
		if (node.index >= 0 && node.parentNode != null) {
			removeChildNode(node.index);
		}
	}

	/**
	 * 获取下一个（右）兄弟节点
	 *
	 * @return 下一个（右）兄弟节点
	 */
	public TreeNode<T> getNextSibling() {
		if (parentNode != null) {
			if (index + 1 >= parentNode.children.size()) {
				return null;
			}
			return parentNode.children.get(index + 1);
		}
		return null;
	}

	/**
	 * 获取孩子节点个数
	 *
	 * @return 孩子节点个数
	 */
	public int childrenCount() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	/**
	 * 获取所有的孩子节点列表，此列表为原始数据，不能随意修改
	 *
	 * @return 直接孩子节点列表
	 */
	public List<TreeNode<T>> getChildren() {
		return children;
	}

	/**
	 * 获取后代节点个数
	 *
	 * @return 后代节点个数
	 */
	public int posteritiesCount() {
		return getPosterities().size();
	}

	/**
	 * 获取所有的后代节点列表
	 *
	 * @return 后代节点列表
	 */
	public List<TreeNode<T>> getPosterities() {
		List<TreeNode<T>> posterities = new ArrayList<>();
		loopTraversal((TreeNode<T> node) -> {
			if (node != this) {
				posterities.add(node);
			}
			return false;
		});
		return posterities;
	}

	/**
	 * 获取父节点
	 *
	 * @return 父节点
	 */
	public TreeNode<T> getParent() {
		return parentNode;
	}

	/**
	 * 从父节点中移除本节点
	 */
	public void remove() {
		if (parentNode != null) {
			parentNode.removeChildNode(this);
		}
	}

	/**
	 * 获取节点的深度
	 *
	 * @return 节点在整个树种的深度（层级）
	 */
	public int getDepth() {
		int d = 0;
		TreeNode<T> node = this;
		while ((node = node.parentNode) != null) {
			d++;
		}
		return d;
	}

	/**
	 * 迭代遍历所有节点
	 *
	 * @param visiter 节点访问
	 */
	public void loopTraversal(TreeNodeVisiter<T> visiter) {
		TreeNode<T> node = this;
		while (node != null) {
			if (visiter != null) {
				boolean flag = visiter.visite(node);
				if (flag) {
					return;
				}
			}
			if (node.childrenCount() > 0) {
				node = node.children.get(0);
			} else {
				node = toNextSiblingNode(node);
			}
		}
	}

	/**
	 * 找出节点的下一个兄弟节点，未找到则找其父节点的下一个兄弟节点<br>
	 * 循环直至找到某个节点的下一个节点<br>
	 * 最终未找到则返回空<br>
	 *
	 * @param node
	 * @return
	 */
	private TreeNode<T> toNextSiblingNode(TreeNode<T> node) {
		if (node == null) {
			return null;
		}
		if (node.getNextSibling() != null) {
			node = node.getNextSibling();
		} else {
			TreeNode<T> tempNode;
			while (node != null) {
				node = node.parentNode;
				tempNode = node;
				if (node != null) {
					node = node.getNextSibling();
				}
				if (node != null) {
					break;
				}
				node = tempNode;
			}
		}
		return node;
	}

	/**
	 * 递归遍历
	 *
	 * @param visiter 节点访问
	 * @param node 访问的初始节点，为Null则将本节点作为访问初始节点
	 */
	public void recursiveTraversal(TreeNodeVisiter<T> visiter, TreeNode<T> node) {
		if (node == null) {
			node = this;
		}
		if (visiter != null) {
			boolean flag = visiter.visite(node);
			if (flag) {
				return;
			}
		}
		List<TreeNode<T>> nodeChildren = node.children;
		if (nodeChildren == null) {
			return;
		}
		if (nodeChildren.isEmpty()) {
			return;
		}
		nodeChildren.forEach((TreeNode<T> childNode) -> {
			recursiveTraversal(visiter, childNode);
		});
	}

	/**
	 * 清除其孩子节点
	 */
	protected void clearChildren() {
		if (children == null) {
			return;
		}
		if (children.isEmpty()) {
			return;
		}
		children.clear();
	}

	/**
	 * 节点访问器
	 *
	 * @param <T> 节点附带数据的类型
	 */
	public interface TreeNodeVisiter<T> {

		/**
		 * 节点访问方法
		 *
		 * @param node 被访问的节点
		 * @return true表示查找到目标节点，false则为遍历所有节点
		 */
		public boolean visite(TreeNode<T> node);

	}
}
