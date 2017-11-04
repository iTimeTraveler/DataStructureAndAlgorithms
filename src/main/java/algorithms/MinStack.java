package main.java.algorithms;

import java.util.Stack;


/**
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * 
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 */
public class MinStack<E extends Comparable<E>> {
	private Stack<E> mStack = new Stack<E>();
	private Stack<Integer> mMinStack = new Stack<Integer>();
	
	
	public static void main(String args[]){
		int[] array = new int[]{5,7,6,8,3,6,9,3,5,8,2,10,1};
		MinStack<Integer> test = new MinStack<Integer>();
		System.out.println(test.toString());
		
		System.out.println("> Push\n");
		for(int x : array){
			test.push(x);
			System.out.println(test.toString());
		}
		System.out.println("> Pop\n");
		while(!test.isEmpry()){
			test.pop();
			System.out.println(test.toString());
		}
	}
	
	
	public void push(E e){
		if(mStack == null){
			mStack = new Stack<E>();
		}
		mStack.push(e);
		//如果新元素比最小栈栈顶标记的元素还小，需要同时记录，压入最小栈
		if(mMinStack.isEmpty() || e.compareTo(mStack.get(mMinStack.peek())) < 0){
			mMinStack.push(mStack.size() - 1);
		}
	}
	
	public E pop(){
		if(mStack != null && !mStack.isEmpty()){
			E e = mStack.pop();
			//如果弹出的元素是最小栈栈顶标记的元素，同时移除最小栈的这个记录
			if(!mMinStack.isEmpty() && mMinStack.peek() == mStack.size()){
				mMinStack.pop();
			}
			return e;
		}
		return null;
	}
	
	/**
	 * O(1)时间内得到当前的栈空间里，最小的值
	 * @return min
	 */
	public E getMinElement(){
		if(!mMinStack.isEmpty() && mMinStack.peek() < mStack.size()){
			return mStack.get(mMinStack.peek());
		}
		return null;
	}
	
	public E top(){
		return mStack.peek();		
	}
	
	public boolean isEmpry(){
		return mStack.isEmpty();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("Stack : \t[");
		for(int i = 0; i < mStack.size(); i++){
			sb.append(mStack.get(i));
			if(i != mStack.size() - 1){
				sb.append(" -> ");
			}
		}
		sb.append("]\n").append("MinStack : \t[");
		for(int i = 0; i < mMinStack.size(); i++){
			sb.append(mMinStack.get(i));
			if(i != mMinStack.size() - 1){
				sb.append(" -> ");
			}
		}
		sb.append("]\n").append("min Element : \t" +
				getMinElement() + "_[pos:" + (mMinStack.isEmpty() ? "null" : mMinStack.peek()) + "]\n");
		return sb.toString();
	}
}
