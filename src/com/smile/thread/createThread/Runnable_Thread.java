package com.smile.thread.createThread;


//关于start()方法之后，run方法的调用时机是获得cpu后由新线程自动调用的
//把需要处理的代码放到run()方法中，start()方法启动线程将自动调用run()方法，这个由java的内存机制规定的。并且run()方法必需是public访问权限，返回值类型为void。
//当程序调用start方法一个新线程将会被创建，并且在run方法中的代码将会在新线程上运行
//当然，也可以使用内部类的方式创建线程
public class Runnable_Thread implements Runnable{
    @Override
    public void run() {
        System.out.println("实现Runnable接口来创建线程……");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable_Thread());
        t1.start();//可以观看一下源码，start方法是如何调用Runnable_Thread的run方法的
    }
}
