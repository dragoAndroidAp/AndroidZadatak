package com.horvat.dragutin.androidzadatak.models;

import android.graphics.Bitmap;

/**
 * Created by drago on 1.7.2015..
 */
public class ListViewModel {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String title;
    private String address;
    private Bitmap image;


    public ListViewModel() {
    }

    public ListViewModel(String title, String address, Bitmap image) {
        this.address = address;
        this.image = image;
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
