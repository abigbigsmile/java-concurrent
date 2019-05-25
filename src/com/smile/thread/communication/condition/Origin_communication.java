package com.smile.thread.communication.condition;

//使用wait()和notifyAll()来实现有序打印abc
public class Origin_communication {

    private int signal = 0;

    public synchronized void a(){
        while(signal != 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal ++;
        notifyAll();
    }
    public synchronized void b(){
        while(signal != 1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal ++;
        notifyAll();
    }
    public synchronized void c(){
        while(signal != 2){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 0;
        notifyAll();
    }

    public static void main(String[] args) {
        Origin_communication cc = new Origin_communication();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cc.a();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cc.b();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cc.c();
                }
            }
        }).start();
    }
}
