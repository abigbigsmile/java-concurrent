package com.smile.thread.communication.wait_notify;

public class Run_1 implements Runnable{

    private Main main;

    public Run_1(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        try {
            main.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
