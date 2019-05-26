package com.smile.thread.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoin extends RecursiveTask<Integer> {

    private int begin;
    private int end;

    public ForkJoin(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }



    @Override
    protected Integer compute() {
        Integer sum = 0;
        if(end - begin <=2){
            for(int i=begin; i<=end; i++){
                sum += i;
            }
            return sum;
        }else{
            ForkJoin fj1 = new ForkJoin(begin, (end + begin)/2);
            ForkJoin fj2 = new ForkJoin(1+(end + begin)/2, end);
            Future<Integer> f1 = fj1.fork();
            Future<Integer> f2 = fj2.fork();
            Integer a = ((ForkJoinTask<Integer>) f1).join();
            Integer b = ((ForkJoinTask<Integer>) f2).join();
            sum = a + b;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoin fj = new ForkJoin(1,100000);
        ForkJoinPool fjp = new ForkJoinPool(10);
        Future<Integer> future = fjp.submit(fj);
        System.out.println("1+……+1000结果为："+future.get());

        List<String> list = new ArrayList<>();
        List<String> syncList = Collections.synchronizedList(list);
    }
}
