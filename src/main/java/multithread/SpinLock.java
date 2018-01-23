package multithread;
// Author : iTimeTraveler
// Date   : 2018-01-23

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger实现锁
 */
public class SpinLock {

	private AtomicInteger state = new AtomicInteger(0);

	/**
	 * 自旋等待直到获得锁
	 */
	public void lock(){
		for (;;){
			if (state.get() == 1) {
				continue;
			} else if(state.compareAndSet(0, 1)){
				return;
			}
		}
	}

	public void unlock(){
		state.set(0);
	}
}
