package com.smile.thread.security.read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//使用读写锁
public class ReadWrite {
    //获取读写锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Map<String, Object> map = new HashMap<>();
    private boolean canUpdate = true ;//标志是否能够被修改
    private volatile int data = 0;//加volatile保证可见性

    /*
    锁降级中读锁的获取是否必要呢？答案是必要的。主要是为了保证数据的可见性，如果当前线程不获取读锁而是直接释放写锁，
    假设此刻另一个线程（记作线程T）获取了写锁并修改了数据，那么当前线程无法感知线程T的数据更新。如果当前线程获取读锁，
    即遵循锁降级的步骤，则线程T将会被阻塞，直到当前线程使用数据并释放读锁之后，线程T才能获取写锁进行数据更新。
    */
    public void downGradeLock(){
        readLock.lock();//保证拿到canUpdate最新值，加读锁
        if(canUpdate){
            readLock.unlock();
            writeLock.lock();
            //再次判断，因为读锁释放时可能其他线程获取写锁已对canUpdate修改
            try {
                if(canUpdate){
                    data = 666;
                    //在释放前，将写锁降级，获取读锁
                    canUpdate = false;
                }
               readLock.lock();
            } finally {
                writeLock.unlock();
            }
        }
        System.out.println(data);
        readLock.unlock();
    }


    public Object get(String key){
        readLock.lock();
        System.out.println("读锁"+Thread.currentThread().getName()+"->正在读……");
        try {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return map.get(key);
        }finally {
            synchronized (this){
                readLock.unlock();
                System.out.println("读锁"+Thread.currentThread().getName()+"->读取完毕。。。。");
            }
        }
    }

    public void put(String key, Object value){
        writeLock.lock();
        System.out.println("写锁"+Thread.currentThread().getName()+"->正在写！");
        try {
            map.put(key, value);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            synchronized (this){
                writeLock.unlock();
                System.out.println("写锁"+Thread.currentThread().getName()+"->写完毕！");
            }
        }
    }

    public static void main(String[] args) {
        ReadWrite readWrite = new ReadWrite();
        ExecutorService executorService = Executors.newCachedThreadPool();
        /*
        for(int i=0; i<5; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    readWrite.get("hello");
                }
            });
        }
        for(int i=0; i<2; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    readWrite.put("1","hello");
                    readWrite.put("2","huang");
                }
            });
        }
*/
        for(int i=0; i<5; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    readWrite.downGradeLock();
                }
            });
        }
        executorService.shutdown();
    }


}
