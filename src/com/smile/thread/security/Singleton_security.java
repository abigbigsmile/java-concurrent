package com.smile.thread.security;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singleton_security {

    //私有化构造方法
    private Singleton_security(){}

    private static volatile Singleton_security instance;

    //解决单例模式出现的线程安全问题可以使用synchronized修饰getInstance(),但是开销很多（多次开锁解锁）,
    //采用双重检查枷锁，但是jvm进行优化时，出现指令重排序，还是会有问题
    public static Singleton_security getInstance() throws InterruptedException {
        if(instance == null){
            synchronized(Singleton_security.class){
                if(instance == null){
                    instance = new Singleton_security();//指令重排序

                    //先开辟内存空间
                    //在内存空间中实例化对象
                    //将引用指向内存空间
                    //当指令重排序时：比如先引用指向，再开辟空间，则instance==null的判断就会失效
                    //使用volatile解决指令重排序的问题
                }
            }
        }
        return instance;
    }

}

class Main{
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i=0; i<10; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + Singleton_security.getInstance().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}