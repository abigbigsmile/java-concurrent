package com.smile.thread.communication.wait_notify;

import java.util.concurrent.TimeUnit;

public class Demo {

    private volatile int data = 0;

    public int get(){
        return this.data;
    }

    public void set(int d){
        this.data = d;
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(demo){
                    System.out.println(Thread.currentThread().getName()+"在等待中……");
                    int d = demo.get();
                    if(d != 1){
                        try {
                            demo.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName()+"取值成功。");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (demo){
                    try {
                        TimeUnit.SECONDS.sleep(3);//睡眠3秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    demo.set(1);
                    demo.notify();
                }
            }
        }).start();
    }
}
