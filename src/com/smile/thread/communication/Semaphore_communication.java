package com.smile.thread.communication;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//并发工具类Semaphore
//Semaphore类是一个计数信号量，必须由获取它的线程释放，通常用于限制可以访问某些资源（物理或逻辑的）线程数目。
public class Semaphore_communication {

    public void restrictAccess(Semaphore semaphore){

        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+" : 进来啦！！");
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    public static void main(String[] args) {
        Semaphore_communication sc = new Semaphore_communication();
        Semaphore semaphore = new Semaphore(5);
        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        for(int i=0; i<50; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    sc.restrictAccess(semaphore);
                }
            });
        }
        threadPool.shutdown();
    }

}
