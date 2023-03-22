package com.tinieblas.tokomegawa.models;

public class Producto {

    private String name;
    private int price;
    private int imageResId;

    public Producto(String name, int price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
