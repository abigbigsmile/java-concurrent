package com.smile.thread.createThread;

import java.util.Arrays;
import java.util.List;

public class Lambda {

    public static void main(String[] args) {
        Integer[] ints = new Integer[]{10, 20, 30, 40, 50 };
        List<Integer> integerList = Arrays.asList(ints);
        //并行流实现并行输出
        integerList.parallelStream().forEach(System.out::println);

    }
}
