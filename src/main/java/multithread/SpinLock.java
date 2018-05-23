package multithread;
// Author : iTimeTraveler
// Date   : 2018-01-23

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用AtomicInteger实现自旋锁
 */
public class SpinLock implements Lock {

	private AtomicInteger state = new AtomicInteger(0);

	/**
	 * 自旋等待直到获得许可
	 */
	public void lock(){
		for (;;){
			//CAS指令要锁总线，效率很差。所以我们通过一个if判断避免了多次使用CAS指令。
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


	@Override
	public void lockInterruptibly() throws InterruptedException {
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
		return false;
	}

	@Override
	public Condition newCondition() {
		return null;
	}
}
