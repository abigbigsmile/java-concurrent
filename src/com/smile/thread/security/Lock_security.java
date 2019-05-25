package com.smile.thread.security;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock_security {

    private int value = 0;
    //锁
    private Lock lock = new ReentrantLock();

    public void add(){
        lock.lock();//加锁
        value++;
        lock.unlock();
    }
    public int get(){return this.value;}

    public static void main(String[] args) {
        Lock_security lock_security = new Lock_security();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for(int i=0; i<100;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    lock_security.add();
                    System.out.println(Thread.currentThread().getName()+" : "+lock_security.get());
                }
            });
        }
        threadPool.shutdown();
    }

}
