package com.smile.thread.simulate_future;

import java.util.concurrent.Callable;

public class FutureFactory{

    public Future produceCake(){
        Future future = new Future();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cake cake = new Cake("smile", 6.99);
                future.set(cake);
            }
        }).start();
        return future;
    }

}
