package com.smile.thread.security;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* volatile关键字
* 1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
* 2）禁止进行指令重排序。
* 保证可见性，但是不保证原子性，所以不能替代synchronized使用
* */

public class Volatile_security {

    private volatile int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
        Volatile_security volatile_security = new Volatile_security();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                volatile_security.setValue(666);
            }
        }).start();

        for(int i=0; i<10;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" : "+volatile_security.getValue());
                }
            });
        }

        threadPool.shutdown();

    }
}
