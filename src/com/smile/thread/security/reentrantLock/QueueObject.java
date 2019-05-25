package com.smile.thread.security.reentrantLock;

public class QueueObject {

    private boolean isNotified = false;

    public synchronized void dowait() throws InterruptedException {

        while(!isNotified){
            wait();
        }
        isNotified = false;

    }

    public synchronized void doNotify(){
        notify();
        isNotified = true;
    }

    public boolean equals(Object o) {
        return this == o;
    }

}
