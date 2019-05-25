package com.smile.thread.security.reentrantLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//自己实现公平锁（原理：按等待时间来分配锁，即每次去队列第一个）
public class MyFairLock implements Lock {

    private boolean isLocked = false;
    private Thread lockedBy = null;
    private List<QueueObject> waitList = new ArrayList<>();

    @Override
    public synchronized void lock(){

        QueueObject queueObject = new QueueObject();//每次调用一次lock就会新建一个临时对象标记当前线程
        boolean isLockedForThisThread = true;
        synchronized (this) {
            waitList.add(queueObject);
            //不管有没有获得FairLock对象锁，先将监视器压入队列。
        }

        while (isLockedForThisThread) {
            synchronized (this) {
                isLockedForThisThread = isLocked || waitList.get(0) != queueObject;
                /*
                当isLockedForThisThread==false的时候，当前线程获取FairLock实例的锁，这要求两个条件：
                1、没有其它线程占用FairLock对象（isLock==false）
                2、当前线程的监视器处在队列头部（主要是为了拦截在FairLock未被占有的时候乱入的不是最先的线程）
                如果当前线程不在头部，那么主动wait()
                */
                if (!isLockedForThisThread) {//满足获取FairLock锁的条件
                    isLocked = true;//标记锁被占用
                    waitList.remove(queueObject);//移除该线程的监视器
                    lockedBy = Thread.currentThread();
                    return;
                }
            }
            //如果FairLock锁已被占，或者当前线程不在队列头，那么调用监视器的doWait()等待
            try {
                queueObject.dowait();
                //如果一个线程从wait()返回，那么它必然在队列头
            } catch (InterruptedException e) {
                synchronized (this) {
                    waitList.remove(queueObject);//出现异常，及时收尾
                }
            }
        }


        /*while(isLocked){
            QueueObject queueObject = new QueueObject();
            waitList.add(queueObject);
            try {
                queueObject.dowait();
            } catch (InterruptedException e) {
                waitList.remove(queueObject);
                e.printStackTrace();
            }
        }
        isLocked = true;*/
    }

    @Override
    public synchronized void unlock() {

        if (this.lockedBy != Thread.currentThread()) {
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked = false;
        lockedBy = null;
        if (!waitList.isEmpty()) {
            waitList.get(0).doNotify();
        }


       /* isLocked = false;
        if(!waitList.isEmpty()){
            waitList.get(0).doNotify();
            waitList.remove(0);
        }*/


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
