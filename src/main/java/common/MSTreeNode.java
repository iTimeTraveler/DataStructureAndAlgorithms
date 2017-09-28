package common;

import java.util.ArrayList;
import java.util.List;

/**
 * 多叉树节点
 */
public class MSTreeNode<T> {
	public T val;
	public MSTreeNode<T> parent;
	public List<MSTreeNode<T>> children;
	public MSTreeNode(T value){
		this.val = value;
	}


	public void appendChildNode(MSTreeNode<T> node){
		if(children == null){
			children = new ArrayList<MSTreeNode<T>>();
		}
		children.add(node);
	}


	@Override
	public String toString() {
		StringBuilder childStr = new StringBuilder("[");
		for(MSTreeNode child : children){
			childStr.append(child.val + " ");
		}
		childStr.append("]");
		return "MSTreeNode{" +
				"value=" + val.toString() +
				"children=" + childStr +
				"parent=" + parent.val +
				"}";
	}
}
