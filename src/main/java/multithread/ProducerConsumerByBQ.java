package multithread;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者模式：使用{@link java.util.concurrent.BlockingQueue}实现
 */
public class ProducerConsumerByBQ{
	private static final int CAPACITY = 5;
	private static volatile AtomicInteger i = new AtomicInteger(0);

	public static void main(String args[]){
		LinkedBlockingDeque<Integer> blockingQueue = new LinkedBlockingDeque<Integer>(CAPACITY);

		Thread producer1 = new Producer("P-1", blockingQueue, CAPACITY);
		Thread producer2 = new Producer("P-2", blockingQueue, CAPACITY);
		Thread consumer1 = new Consumer("C1", blockingQueue, CAPACITY);
		Thread consumer2 = new Consumer("C2", blockingQueue, CAPACITY);
		Thread consumer3 = new Consumer("C3", blockingQueue, CAPACITY);

		producer1.start();
		producer2.start();
		consumer1.start();
		consumer2.start();
		consumer3.start();
	}

	/**
	 * 生产者
	 */
	public static class Producer extends Thread{
		private LinkedBlockingDeque<Integer> blockingQueue;
		String name;
		int maxSize;

		public Producer(String name, LinkedBlockingDeque<Integer> queue, int maxSize){
			super(name);
			this.name = name;
			this.blockingQueue = queue;
			this.maxSize = maxSize;
		}

		@Override
		public void run(){
			while(true){
				try {
					blockingQueue.put(i.get());
					System.out.println("[" + name + "] Producing value : +" + i.get());
					i.incrementAndGet();

					//暂停最多1秒
					Thread.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 消费者
	 */
	public static class Consumer extends Thread{
		private LinkedBlockingDeque<Integer> blockingQueue;
		String name;
		int maxSize;

		public Consumer(String name, LinkedBlockingDeque<Integer> queue, int maxSize){
			super(name);
			this.name = name;
			this.blockingQueue = queue;
			this.maxSize = maxSize;
		}

		@Override
		public void run(){
			while(true){
				try {
					int x = blockingQueue.take();
					System.out.println("[" + name + "] Consuming : " + x);

					//暂停最多1秒
					Thread.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
