package com.smile.thread.simulate_future;

public class Main {

    public static void main(String[] args) {
        FutureFactory ff = new FutureFactory();
        Future future = ff.produceCake();
        System.out.println("我预定蛋糕成功……");
        System.out.println("我来取蛋糕："+future.get());
    }

}
