package com.elgroup.lenovo.bottlepop.Models;

/**
 * Created by Lenovo on 13-01-2016.
 */
public class BookingPagerModel {

    String name;
    String price;
    String[] imagesNames;


    public BookingPagerModel(String name,String price,String[] imagesNames){

        this.setName(name);
        this.setPrice(price);
        this.setImagesNames(imagesNames);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String[] getImagesNames() {
        return imagesNames;
    }

    public void setImagesNames(String[] imagesNames) {
        this.imagesNames = imagesNames;
    }
}
