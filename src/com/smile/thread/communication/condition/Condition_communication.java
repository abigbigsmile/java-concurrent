package com.smile.thread.communication.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Condition_communication {

    private int signal = 0;
    Lock lock = new ReentrantLock();
    Condition ca = lock.newCondition();
    Condition cb = lock.newCondition();
    Condition cc = lock.newCondition();

    public void a(){
        lock.lock();
        while(signal != 0){
            try {
                Thread.sleep(100);
                ca.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal ++;
        cb.signal();
        lock.unlock();
    }
    public void b(){
        lock.lock();
        while(signal != 1){
            try {
                Thread.sleep(100);
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal ++;
        cc.signal();
        lock.unlock();
    }
    public void c(){
        lock.lock();
        while(signal != 2){
            try {
                Thread.sleep(100);
                cc.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 0;
        ca.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        Condition_communication cnc = new Condition_communication();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cnc.c();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cnc.b();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cnc.a();
                }
            }
        }).start();

    }

}
