package com.smile.thread.security;


//synchronized修饰静态方法，作用域在该类的所有对象实例
public class Synchronized_static{

    public static synchronized void method1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+": method-1 begin .");
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName()+": method-1 end ");
    }


    public static void main(String[] args) throws InterruptedException {
        Synchronized_static ordinary = new Synchronized_static();
        Synchronized_static ordinary1 = new Synchronized_static();
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
