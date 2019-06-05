package com.smile.thread.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutor_util {

    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger(0);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i=0; i<100; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    count.getAndIncrement();
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        threadPool.shutdown();
        while(threadPool.getActiveCount() >= 1){}
        System.out.println(count);
    }
}
