package com.smile.thread.security.reentrantLock;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//自己使用AQS(AbstractQueuedSynchronized)实现可重入锁
public class MyLock implements Lock {

    private Helper helper = new Helper();

    private class Helper extends AbstractQueuedSynchronizer{



        @Override
        protected boolean tryAcquire(int arg) {

            //第一次进来
            int state = getState();
            if(state == 0){
                if(compareAndSetState(0,arg)){
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }//原子操作状态
            }else if(Thread.currentThread() == getExclusiveOwnerThread()){
                setState(state + 1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            boolean flag = false;
            if(Thread.currentThread() != getExclusiveOwnerThread())throw new RuntimeException();
            int state = getState() - arg;
            if(state == 0){
                setExclusiveOwnerThread(null);
                flag = true;
            }
            setState(state);
            return flag;
        }

        Condition newCondition(){
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        helper.acquire(1);
    }

    @Override
    public void unlock() {
        helper.release(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        helper.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return helper.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return helper.tryAcquireNanos(1, unit.toNanos(time));
    }


    @Override
    public Condition newCondition() {
        return helper.newCondition();
    }


}
