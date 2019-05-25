package com.smile.thread.security;
//锁可重入
public class Reentrant {

    public synchronized void method1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+" : method-1");
        Thread.sleep(2000);
       // method2();
    }

    public synchronized void method2(){
        System.out.println(Thread.currentThread().getName()+" : method-2");
    }

    public static void main(String[] args) {
        Reentrant reentrant = new Reentrant();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrant.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrant.method2();
            }
        }).start();

    }
}
