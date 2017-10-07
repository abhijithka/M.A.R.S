package com.mars.mars.vistara.restaurants;

import com.mars.mars.vistara.restaurants.menu.Menu;

import java.util.List;

/**
 * Created by aappukuttan on 10/6/2017.
 */
public class Restaurant {

    private String name;
    private String imgUrl;

    private List<Menu> menu;

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

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
