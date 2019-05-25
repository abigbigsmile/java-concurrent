package com.smile.thread.communication.wait_notify;

public class Main {

    private volatile int data = 0;

    public synchronized int get() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"在等待中……");
        if(this.data != 1){
            wait();//这个方法会释放对象实例的锁
        }
        System.out.println(Thread.currentThread().getName()+"获取成功");
        return this.data;
    }

    public synchronized void set(int d){
        this.data = d;
        //notify();//随机唤醒一个
        notifyAll();
        System.out.println(Thread.currentThread().getName()+"唤醒！！！");
    }

    public static void main(String[] args) {
        Main main = new Main();
        Thread t1 = new Thread(new Run_1(main));
        Thread t2 = new Thread(new Run_1(main));
        Thread t3 = new Thread(new Run_1(main));
        Thread t4 = new Thread(new Run_1(main));
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread t5 = new Thread(new Run_2(main));
        t5.start();
    }


}
