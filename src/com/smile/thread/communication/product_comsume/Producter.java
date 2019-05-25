package com.smile.thread.communication.product_comsume;

public class Producter implements Runnable {

    private Store store ;

    public Producter(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (true){
                store.produce();
                Thread.sleep(2315);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
