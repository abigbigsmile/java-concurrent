package com.smile.thread.createThread;

/*
只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，
守护线程随着JVM一同结束工作，下面例子是当主线程结束时，守护线程也跟着结束。
注意：不要把有着输入输出操作的线程设置为守护线程，因为当其他User Thread全退出时，守护线程还没执行完输入输出操作，可能导致错误
*/

public class Deamon_Thread extends Thread {

    @Override
    public void run() {
        System.out.println("线程启动……");
        while(true){
            try {
                Thread.sleep(1000);
                System.out.println("sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Deamon_Thread();
        t1.setDaemon(true);//将线程t1设置为守护线程
        t1.start();
        Thread.sleep(5000);

    }
}
