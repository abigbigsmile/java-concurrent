package com.smile.thread.communication;

import java.util.Random;
import java.util.concurrent.*;

//并发编程工具类CyclicBarrier
public class Cyclic_communication {

    public void waitForMeeting(CyclicBarrier cyclicBarrier){
        try {
            Thread.sleep(new Random().nextInt(4000));
            System.out.println(Thread.currentThread().getName()+"已来到会场，等待开会……");

            if(Thread.currentThread().getName().equals("pool-1-thread-7")){
                //Thread.currentThread().interrupt();
                //慎用reset(),观看源码可知，调用reset()后会调用breakBarrier();此时isBroken=true，唤醒
                //所有等待线程，但是之后又调用nextGeneration()，此时isBroken会被置为false,在这之后进来的
                //线程可能一直处于等待，不会再被唤醒
                TimeUnit.SECONDS.sleep(20);
                cyclicBarrier.reset();

            }

            cyclicBarrier.await();
            Thread.sleep(new Random().nextInt(3000));
            System.out.println(Thread.currentThread().getName()+"发言");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("全部人已经来到会场，会议正式开始！！！");
            }
        });
        Cyclic_communication cc = new Cyclic_communication();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int i=0; i<10; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    cc.waitForMeeting(cyclicBarrier);
                }
            });
        }
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("等待线程数："+cyclicBarrier.getNumberWaiting());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadPool.shutdown();
    }
}
