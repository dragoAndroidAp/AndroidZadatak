package com.horvat.dragutin.androidzadatak.models;

import android.graphics.Bitmap;

/**
 * Created by drago on 30.6.2015..
 */
public class Image {

    private int id;
    private int imageId;
    private byte[] image;
    private Bitmap bitmap;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    /**
     * Default constructor
     */
    public Image() {
    }

    /**
     * Constructor with only image parameter
     *
     * @param image
     */
    public Image(byte[] image, int imageId) {
        this.image = image;
        this.imageId = imageId;
    }

    /**
     * Image constructor
     *
     * @param id
     * @param imageId
     * @param image
     */
    public Image(int id, int imageId, byte[] image) {
        this.id = id;
        this.imageId = imageId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int hotelId) {
        this.imageId = hotelId;
    }

}
