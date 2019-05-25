package com.smile.thread.communication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//并发编程工具类CountDownLatch
public class CountDownLatch_communication {

    private int[] nums;

    public CountDownLatch_communication(int size) {
        nums = new int[size];
    }

    public void cal(int line, String strLine, CountDownLatch countDownLatch){
        String[] strings = strLine.split(",");
        int sum = 0;
        for(String s : strings){
            sum += Integer.parseInt(s);
        }
        nums[line] = sum;
        System.out.println(Thread.currentThread().getName()+"：正在计算……"+strLine+"——结果为"+sum);
        countDownLatch.countDown();
    }

    public void print(){
        int sum = 0;
        for(int i : nums){
            sum += i;
        }
        System.out.println("总结果为："+sum);

    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<String> strList = readFile();
        int strCount = strList.size();
        CountDownLatch_communication cc = new CountDownLatch_communication(strCount);
        CountDownLatch countDownLatch = new CountDownLatch(strCount);
        for(int i=0; i<strCount; i++){
            final int j = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    cc.cal(j, strList.get(j), countDownLatch);
                }
            });

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
        cc.print();
    }

    public static List<String> readFile(){
        List<String> strings = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("myFile.txt"));
            String strLine;
            while(( strLine = bufferedReader.readLine()) != null){
                strings.add(strLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

}
