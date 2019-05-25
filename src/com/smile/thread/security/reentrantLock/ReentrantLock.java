package com.smile.thread.security.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//自己实现可重入锁
public class ReentrantLock implements Lock {

    private boolean isLocked = false;//判断是否已经被锁
    private Thread lockedBy = null;//标记拿到锁的线程
    private int lockCount = 0;

    @Override
    public synchronized void lock() {

        while(isLocked && lockedBy != Thread.currentThread()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLocked = true;
        lockCount ++;
        lockedBy = Thread.currentThread();

    }

    //注意：使用notify的方法需要synchronized修饰，否则无法得知唤醒等待哪一把锁的线程

    @Override
    public synchronized void unlock() {
        if(lockedBy == Thread.currentThread()){
            lockCount--;
            if(lockCount == 0){
                isLocked = false;
                notify();
            }
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
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }

}
