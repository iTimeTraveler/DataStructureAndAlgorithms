package main.java.multithread;

import java.util.LinkedList;
import java.util.Queue;


/**
 * http://www.importnew.com/16453.html
 */
public class ProducerConsumer {
	int maxSize;
	
	public static void main(String args[]){
		Queue<Integer> queue = new LinkedList<Integer>();
		
		Thread producer = new Producer("P", queue);
		Thread consumer = new Consumer("C", queue);
		
		producer.start();
		consumer.start();
	}
	
	/**
	 * 生产者
	 */
	public static class Producer extends Thread{
		private Queue<Integer> queue;
		
		public Producer(String name, Queue<Integer> queue){
			super(name);
			this.queue = queue;
		}
		
		@Override
		public void run(){
			
		}
	}
	
	/**
	 * 消费者
	 */
	public static class Consumer extends Thread{
		private Queue<Integer> queue;
		
		public Consumer(String name, Queue<Integer> queue){
			super(name); 
			this.queue = queue;
		}
		
		@Override
		public void run(){
			
		}
	}
}
