package com.smile.thread.util;

import java.util.concurrent.locks.StampedLock;

//下面这个Demo可以看出，在获取乐观读锁后，其他线程也可以同时获取目标对象的写锁
public class StampedLock_util {

    private StampedLock stampedLock = new StampedLock();

    private int value = 0;

    public void read(){
        long stamp = stampedLock.tryOptimisticRead();//获取乐观读锁
        System.out.println("获取乐观读锁,然后读：value = " + value);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!stampedLock.validate(stamp)){//判断在上面读的过程中有没有被修改过，如果有，则获取读锁重新读
            try {
                stamp = stampedLock.readLock();
                System.out.println("重新读取，value = " + value);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }

    }

    public void write(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long stamp = stampedLock.writeLock();
        try {
            System.out.println("获取写锁成功！！！");
            value = 666;
        }  finally {
            stampedLock.unlockWrite(stamp);
        }
    }
    public void readAndWrite(){
        long stamp = stampedLock.tryOptimisticRead();
        int v = value;
        try{
            if(!stampedLock.validate(stamp)){
                System.out.println("value被修改过哦！！！");
                stamp = stampedLock.readLock();
                v = value;//重新读
            }
            if(v == 0){
                stamp = stampedLock.tryConvertToWriteLock(stamp);//读锁转换为写锁
                value = 777;
            }
        }finally {
            stampedLock.unlock(stamp);
        }
    }

    public static void main(String[] args) {
        StampedLock_util su = new StampedLock_util();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                su.write();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                su.read();
            }
        }).start();*/
       su.readAndWrite();
       su.read();
    }




}
