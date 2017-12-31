package multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwoThreadPrinter {
	private static final Object object = new Object();

	private static Lock mLock = new ReentrantLock();
	private static Condition mP1 = mLock.newCondition();
	private static Condition mP2 = mLock.newCondition();

	private static int i = 0;

	public static void main(String args[]){
		//Lock + Condition方式实现
		Printer1 printer1 = new Printer1();
		Printer2 printer2 = new Printer2();
//		printer1.start();
//		printer2.start();

		//Object的wait + notifyAll实现
		Printer3 printer3 = new Printer3();
		Printer4 printer4 = new Printer4();
		printer3.start();
		printer4.start();
	}

	/**
	 * Lock + Condition方式实现
	 */
	private static class Printer1 extends Thread{

		@Override
		public void run() {
			while (true){
				try {
					mLock.lock();
					System.out.println("Printer1: " + i++);

					mP2.signalAll();
					mP1.await();
					mLock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class Printer2 extends Thread{

		@Override
		public void run() {
			while (true) {
				try {
					mLock.lock();
					System.out.println("Printer2: " + i++);

					mP1.signalAll();
					mP2.await();
					mLock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Object的wait + notifyAll实现
	 */
	private static class Printer3 extends Thread{

		@Override
		public void run() {
			while (true){
				synchronized(object){
					System.out.println("Printer3: " + i++);
					object.notifyAll();

					try {
						object.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static class Printer4 extends Thread{

		@Override
		public void run() {
			while (true) {
				synchronized(object){
					System.out.println("Printer4: " + i++);
					object.notifyAll();

					try {
						object.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}



