package com.horvat.dragutin.androidzadatak.models;

/**
 * Created by drago on 30.6.2015..
 */
public class Hotel {

    private int id;
    private String name;
    private String address;
    private String description;
    private int rating;


    public Hotel() {
    }

    public Hotel(int id, int rating, String name, String address, String description) {
        this.id = id;
        this.rating = rating;
        this.name = name;
        this.address = address;
        this.description = description;


    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
