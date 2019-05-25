package com.smile.thread.security;

public class Sychronized_security {

    private static int value = 0;

    //使用synchronized来保证value同一时刻仅被一个线程访问
    public synchronized static int getValue(){
        return value++;
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(Thread.currentThread().getName()+":"+getValue());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(Thread.currentThread().getName()+":"+getValue());
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(Thread.currentThread().getName()+":"+getValue());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}


