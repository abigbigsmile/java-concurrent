package com.smile.thread.simulate_future;

public class Future {

    private Cake cake;

    private boolean isSet = false;

    public synchronized Cake get(){
        while(!isSet){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.cake;
    }

    public synchronized void set(Cake cake){
        this.cake = cake;
        isSet = true;
        notify();
    }

}
