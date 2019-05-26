package com.smile.thread.simulate_future;

public class Cake {

    private String cakeName;
    private double price;

    public Cake(String cakeName, double price) {
        this.cakeName = cakeName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cake["+cakeName+","+price+"]";
    }
}
