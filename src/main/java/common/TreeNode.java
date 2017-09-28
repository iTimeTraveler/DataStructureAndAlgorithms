package common;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
	public T val;
	public TreeNode<T> parent;
	public List<TreeNode<T>> children;
	public TreeNode(T value){
		this.val = value;
	}


	public void appendChildNode(TreeNode<T> node){
		if(children == null){
			children = new ArrayList<TreeNode<T>>();
		}
		children.add(node);
	}


	@Override
	public String toString() {
		StringBuilder childStr = new StringBuilder("[");
		for(TreeNode child : children){
			childStr.append(child.val + " ");
		}
		childStr.append("]");
		return "TreeNode{" +
				"value=" + val.toString() +
				"children=" + childStr +
				"parent=" + parent.val +
				"}";
	}
}
