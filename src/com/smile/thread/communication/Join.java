package com.smile.thread.communication;

//主线程在执行a方法时，调用join(),然后执行完b方法后再继续执行a方法
//join():当前线程等待调用join()的线程执行完毕再往下执行
public class Join {


    public void a(){
        System.out.println(Thread.currentThread().getName()+" : 开始执行a()....");
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" : 执行a()完毕！！！");
    }

    public static void main(String[] args) throws InterruptedException {
        Join join = new Join();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                join.a();
            }
        });
        t1.start();
        t1.join();
        System.out.println("Main线程完毕。。");
    }



}
