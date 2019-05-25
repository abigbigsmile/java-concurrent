package com.smile.thread.communication.product_comsume;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Store {

    private volatile int bread = 0;
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 0;

    public synchronized void produce() throws InterruptedException {
        while(bread >= MAX_SIZE){
            System.out.println(Thread.currentThread().getName()+" : 面包太多了！！！");
            wait();
        }
        bread++;
        System.out.println(Thread.currentThread().getName()+" : 生产面包，当前面包数-"+bread);
        notifyAll();
    }

    public synchronized void comsume() throws InterruptedException {
        while(bread <= MIN_SIZE){
            System.out.println(Thread.currentThread().getName()+" : 面包不够了……");
            wait();
        }
        bread--;
        System.out.println(Thread.currentThread().getName()+" : 消费面包，当前面包数-"+bread);
        notifyAll();
    }

    public static void main(String[] args) {
        Store store = new Store();
        ExecutorService producterPool = Executors.newFixedThreadPool(4);
        ExecutorService comsumerPool = Executors.newFixedThreadPool(7);

        for(int i=0; i<4; i++){
            producterPool.execute(new Producter(store));
        }
        for(int i=0; i<7; i++){
            comsumerPool.execute(new Comsumer(store));
        }

        producterPool.shutdown();
        comsumerPool.shutdown();
    }
}
