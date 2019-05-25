package com.smile.thread.communication;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//可以在对中对元素进行配对和交换的线程的同步点。每个线程将条目上的某个方法呈现给 exchange 方法，
// 与伙伴线程进行匹配，并且在返回时接收其伙伴的对象。
//Exchanger 可能在应用程序（比如遗传算法和管道设计）中很有用。
public class Exchanger_communication {

    public void a(Exchanger<String> exchanger){
        System.out.println("a 到达战场……");
        String atob = "smile";
        try {
            System.out.println("a 已发送机密信息，等待回复……");
            String btoa = exchanger.exchange(atob);
            System.out.println("b to a : "+ btoa);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public void b(Exchanger<String> exchanger){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String btoa = "huang";
        try {
            System.out.println("b 回复……");
            String atob = exchanger.exchange(btoa);
            System.out.println("a to b : "+atob);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Exchanger_communication ec = new Exchanger_communication();
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ec.a(exchanger);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ec.b(exchanger);
            }
        }).start();
    }


}
