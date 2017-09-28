package common;

public class BSTreeNode<T extends Comparable<?>> {
	public T val;
	private InnerColor color;
	public BSTreeNode<T> left;
	public BSTreeNode<T> right;
	public BSTreeNode<T> parent;
	public BSTreeNode(T x){
		val = x;
	}
	
	public static final InnerColor RED = InnerColor.RED;
	public static final InnerColor BLACK = InnerColor.BLACK;

	public void setColor(InnerColor color){
		this.color = color;
	}

	public InnerColor getColor(){
		return this.color;
	}

	public boolean isRed(){
	    return this.color == RED;
	}
	
	public boolean haveColor(){
		return this.color != null;
	}
	
	private enum InnerColor{
		BLACK, RED
	}

	@Override
	public String toString() {
		return "BSTreeNode{" +
				"value=" + val +
				", left=" + left.val +
				", right=" + right.val +
				", parent=" + parent.val +
				'}';
	}
}
