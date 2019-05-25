package com.smile.thread.communication.wait_notify;

public class Run_2 implements Runnable{

    private Main main;

    public Run_2(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.set(1);
    }
}
