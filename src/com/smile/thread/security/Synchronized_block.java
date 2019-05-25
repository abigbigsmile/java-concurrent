package com.smile.thread.security;


//synchronized修饰代码块，同步执行代码块中的内容
public class Synchronized_block{

    public  void method1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+": method-1 begin .");
        //synchronized (Synchronized_block.class){
        synchronized (this){
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName()+": method-1 execute ");
        }
        System.out.println(Thread.currentThread().getName()+": method-1 end ");
    }


    public static void main(String[] args) throws InterruptedException {
        Synchronized_block ordinary = new Synchronized_block();
        Synchronized_block ordinary1 = new Synchronized_block();
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