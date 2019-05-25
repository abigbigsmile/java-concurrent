package com.smile.thread.createThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Callable_Thread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("创建可以返回值的线程……");
        Thread.sleep(2000);
        return 666;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //FutureTask实现RunnableFuture，而RunnableFuture实现了Runnable接口
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable_Thread());
        //task可以作为Thread的参数
        Thread t1 = new Thread(task);
        t1.start();
        //获取返回值
        Integer result = task.get();
        System.out.println("Callable_Thread返回值：" + result);

    }
}
