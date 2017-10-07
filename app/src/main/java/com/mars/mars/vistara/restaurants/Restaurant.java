package com.mars.mars.vistara.restaurants;

/**
 * Created by aappukuttan on 10/6/2017.
 */
public class Restaurant {

    private String name;
    private String imgUrl;

    public String getName() {
        return name;
    }

    public Restaurant(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
