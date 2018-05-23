package multithread;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by iTimeTraveler on 2018/5/23.
 * 使用AtomicInteger实现可等待锁
 */

public class BlockLock implements Lock {

    private AtomicInteger state = new AtomicInteger(0);
    private ConcurrentLinkedQueue<Thread> waiters = new ConcurrentLinkedQueue<>();

    @Override
    public void lock() {
        if (state.compareAndSet(0, 1)) {
            return;
        }
        //放到等待队列
        waiters.add(Thread.currentThread());

        for (;;) {
            if (state.get() == 0) {
                if (state.compareAndSet(0, 1)) {
                    waiters.remove(Thread.currentThread());
                    return;
                }
            } else {
                LockSupport.park();     //挂起线程
            }
        }
    }

    @Override
    public void unlock() {
        state.set(0);
        //唤醒等待队列的第一个线程
        Thread waiterHead = waiters.peek();
        if(waiterHead != null){
            LockSupport.unpark(waiterHead); //唤醒线程
        }
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
