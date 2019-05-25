package com.smile.thread.security;

//synchronized修饰普通方法，作用域在单个对象实例，当存在多个对象实例，各个线程是互不干扰的
public class Sychronized_ordinary{

    public synchronized void method1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+": method-1 begin .");
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName()+": method-1 end ");
    }


    public static void main(String[] args) throws InterruptedException {
        Sychronized_ordinary ordinary = new Sychronized_ordinary();
        Sychronized_ordinary ordinary1 = new Sychronized_ordinary();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ordinary.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ordinary.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ordinary1.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

    }
}
