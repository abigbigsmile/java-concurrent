package com.smile.thread.security;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

//原子更新，底层使用CAS原子操作
public class Atomic_security {

    //基本类型
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    //数组类型
    //private AtomicIntegerArray atomicIntegerArray;
    //引用类型:原子更新引用
    //private AtomicReference<User> atomicReference;
    //引用类型的字段，需为volatile修饰，且为public
    //private AtomicIntegerFieldUpdater<User> atomicIntegerFieldUpdater;

    private int value = 0;

    public void add(){value++;}
    public int get(){return this.value;}

    public void addAtomic(){atomicInteger.getAndIncrement();}
    public int getAtomic(){return atomicInteger.get();}


    public static void main(String[] args) {
        User user = new User();
        AtomicIntegerFieldUpdater<User> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        System.out.println(atomicIntegerFieldUpdater.getAndSet(user,666));
        System.out.println(atomicIntegerFieldUpdater.getAndIncrement(user));
        System.out.println(atomicIntegerFieldUpdater.getAndIncrement(user));


        /*Atomic_security atomic_security = new Atomic_security();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for(int i=0; i<100;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
//                    atomic_security.add();
//                    System.out.println(Thread.currentThread().getName()+" : "+atomic_security.get());
                      atomic_security.addAtomic();
                    System.out.println(Thread.currentThread().getName()+" : "+atomic_security.getAtomic());
                }
            });
        }
        threadPool.shutdown();*/
    }

}

class User{
    private String userName;
    public volatile int age;
}