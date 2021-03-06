package com.mars.mars.vistara.restaurants.menu;

/**
 * Created by aappukuttan on 10/7/2017.
 */
public class Menu {

    private String name;
    private int price;
    private String imageUrl;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count++;
    }

    public void decrement() {
        if (this.count != 0) {
            this.count--;
        }
    }

    private int count;

    public Menu(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
