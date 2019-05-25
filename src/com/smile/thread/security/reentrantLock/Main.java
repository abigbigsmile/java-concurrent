package com.smile.thread.security.reentrantLock;

import com.smile.thread.security.Volatile_security;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static int threadNum = 6000;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private MyLock myLock = new MyLock();
    private MyFairLock fairLock = new MyFairLock();
    private int value = 0;

    public int getValue() {
        return value;
    }

    public void add(){
//        reentrantLock.lock();
//        myLock.lock();
        fairLock.lock();
        this.value++;
        fairLock.unlock();
//        testReentrant();
//        myLock.unlock();
//        reentrantLock.unlock();
    }

    public void testReentrant(){
//        reentrantLock.lock();
//        myLock.lock();
        System.out.println(Thread.currentThread().getName()+" : testReentrant");
//        myLock.unlock();
//        reentrantLock.unlock();
    }

    public static void main(String[] args) {
        Main main = new Main();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        //System.out.println(Thread.activeCount());//注意思考

        for(int i=0; i<Main.threadNum;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    main.add();
                }
            });
        }
        threadPool.shutdown();
        while(Thread.activeCount()!=2){}
        System.out.println("最终的结果：" + main.getValue());
    }

}
