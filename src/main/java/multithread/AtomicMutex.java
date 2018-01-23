package multithread;
// Author : iTimeTraveler
// Date   : 2018-01-23

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicMutex {

	public static void main(String[] args) {
		int THREAD_NUM = 10;
		TestMutex test = new TestMutex();
		Thread[] threads = new Thread[THREAD_NUM];

		// 创建10个线程，让它们不断地去增加test.sum的值
		for (int i = 0; i < THREAD_NUM; i++) {
			Thread t = new Thread() {
				public void run() {
					for (int j = 0; j < 100; j++) {
						test.add();
					}
				}
			};
			t.start();
			threads[i] = t;
		}

		for (Thread t : threads) {
			try {
				t.join();	//等待子线程执行完成
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(test.sum);
	}


	public static class TestMutex {
		private int sum = 0;
		private AtomicInteger atomicInteger = new AtomicInteger(0);
		private SpinLock lock = new SpinLock();

		public void add() {
//			if(!atomicInteger.compareAndSet(0, 1)){
//				return;
//			}

			lock.lock();
			if (sum < 30000) {
				try {
					// 这sleep一下，增加线程切换的概率里
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sum += 50;
			}

			lock.unlock();
//			atomicInteger.set(0);
		}
	}

}
