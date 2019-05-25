package com.smile.thread.createThread;

import java.util.concurrent.*;

public class Executor_Thread {

    /*
    * 通过Executor框架创建线程池
    * */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建有10个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        //提交任务给线程池中的线程，返回值为Future对象，通过Future能够获取线程的返回值
        Future<Integer> future = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 666;
            }
        });

        System.out.println(future.get());

        //同时，future可以接受返回的异常并且处理
        Future<String> future1 = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                int i = 1/0;
                return "hello";
            }
        });

        try {
            String str = future1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        threadPool.shutdown();

    }

}
