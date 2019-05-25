package com.smile.thread.communication.product_comsume;

public class Comsumer implements Runnable {

    private Store store ;

    public Comsumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (true){
                store.comsume();
                Thread.sleep(1234);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
