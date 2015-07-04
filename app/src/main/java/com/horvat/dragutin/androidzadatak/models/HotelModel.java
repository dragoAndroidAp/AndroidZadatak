package com.horvat.dragutin.androidzadatak.models;

import android.graphics.Bitmap;

/**
 * Created by drago on 26.6.2015..
 */
public class HotelModel extends Hotel {


    public Bitmap getImage(int index) {
        return images[index];
    }

    public void setImages(Bitmap[] images) {
        this.images = images;
    }


    public Bitmap[] getImages() {
        return images;
    }

    private Bitmap[] images;

    /**
     * Constructor for hotel model
     *
     * @param id          of hotel
     * @param rating      of hotel
     * @param name        of hotel
     * @param address     of hotel
     * @param description of hotel
     * @param images      array of bitmaps
     */
    public HotelModel(int id, int rating, String name, String address, String description, Bitmap[] images) {
        super(id, rating, name, address, description);

        this.images = images;
    }


}
