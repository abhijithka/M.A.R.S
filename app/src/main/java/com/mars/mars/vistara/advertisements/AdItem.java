package com.mars.mars.vistara.advertisements;

/**
 * Created by aappukuttan on 10/4/2017.
 */
public class AdItem {

    private String restaurantName;
    private String itemName;
    private Integer price;

    public AdItem(String restaurantName, String itemName, Integer price) {
        this.restaurantName = restaurantName;
        this.itemName = itemName;
        this.price = price;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
