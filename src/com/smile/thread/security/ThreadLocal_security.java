package com.smile.thread.security;

public class ThreadLocal_security {

    ThreadLocal<Integer> threadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return new Integer(0);
        }
    };

    public Integer getValue(){
        Integer value = threadLocal.get();
        value++;
        threadLocal.set(value);
        return value;
    }

    public static void main(String[] args) {
       ThreadLocal_security threadLocal_security = new ThreadLocal_security();
       new Thread(new Runnable() {
           @Override
           public void run() {
               while (true) {
                   System.out.println(Thread.currentThread().getName()+" : "+threadLocal_security.getValue());
                   try {
                       Thread.sleep(1234);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
       }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName()+" : "+threadLocal_security.getValue());
                    try {
                        Thread.sleep(194);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }





}
