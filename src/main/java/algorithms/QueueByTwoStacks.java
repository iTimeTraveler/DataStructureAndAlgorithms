package algorithms;

import java.util.Scanner;
import java.util.Stack;

public class QueueByTwoStacks<E> {
	private Stack<E> s1 = new Stack<E>();	//push用栈s1
	private Stack<E> s2 = new Stack<E>();	//pop用栈s2
	private int mCount = 0;

	public static void main(String args[]){
		int[] array = new int[]{1,2,3,4,5,6,7,8,9};

		QueueByTwoStacks<String> queue = new QueueByTwoStacks<String>();
		for(int x : array){
			queue.offer(String.valueOf(x));
			System.out.println(queue.toString());
		}

		while(!queue.isEmpty()){
			String e = queue.poll();
			System.out.println("poll element: " + e);
			System.out.println(queue.toString());
		}

		String str = "";
		while(!str.equals("exit")){
			System.out.print("请输入任意字符（支持命令exit, ls, poll, peek, clear）:");
			Scanner sc = new Scanner(System.in);
			str = sc.nextLine();

			if(!str.equals("") && !str.equals("exit")){
				switch(str){
					case "poll":
						String res = queue.poll();
						System.out.println("poll(): " + res);
						break;
					case "ls":
						System.out.println(queue.toString());
						break;
					case "peek":
						System.out.println(queue.peek());
						break;
					case "clear":
						queue.clear();
						break;
					default:
						queue.offer(str);
						break;
				}
			}
		}
	}


	public void offer(E e){
		if(e == null){
			throw new NullPointerException("Couldn't offer a Null Object!");
		}
		s1.push(e);
		mCount++;
	}

	public E poll(){
		if(s2.isEmpty()){
			while(!s1.isEmpty()){
				s2.push(s1.pop());
			}
		}
		if(!s2.isEmpty()){
			mCount--;
			return s2.pop();
		}
		return null;
	}

	public E peek(){
		if(s2.isEmpty()){
			while(!s1.isEmpty()){
				s2.push(s1.pop());
			}
		}
		if(!s2.isEmpty()){
			return s2.peek();
		}
		return null;
	}

	public void clear(){
		s1.clear();
		s2.clear();
	}

	public boolean isEmpty(){
		return mCount <= 0;
	}


	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("Queue : \t[");
		for(int i = s1.size() - 1; i >= 0; i--){
			sb.append(s1.get(i)).append(" -> ");
		}
		sb.append(". ");
		for(int i = 0; i < s2.size(); i++){
			sb.append(s2.get(i));
			if(i != s2.size() - 1){
				sb.append(" -> ");
			}
		}
		sb.append("]\n");
		return sb.toString();
	}
}
