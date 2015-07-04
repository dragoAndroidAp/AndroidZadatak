package com.horvat.dragutin.androidzadatak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.horvat.dragutin.androidzadatak.models.Hotel;
import com.horvat.dragutin.androidzadatak.models.Image;
import com.horvat.dragutin.androidzadatak.util.Util;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by drago on 29.6.2015..
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "hotelDb";

    //Table hotel
    private static final String TABLE_HOTELS = "hotels";
    //Table image
    private static final String TABLE_IMAGE = "image";

    //Hotel columns
    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_NAME = "name";
    private static final String KEY_RATING = "rating";
    private static final String KEY_DESCRIPTION = "description";

    //Image columns
    private static final String KEY_IMAGE_ID = "image_id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_MAIN_IMAGE_ID = "main_image_id";

    //SQL statement for creating Hotel table
    private static final String CREATE_TABLE_HOTELS = "CREATE TABLE " + TABLE_HOTELS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_RATING + " INTEGER," + "UNIQUE(" + KEY_DESCRIPTION + ")" + "ON CONFLICT IGNORE" + ")";
    //SQL statement for creating image table
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_IMAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGE +
            " BLOB," + KEY_MAIN_IMAGE_ID + " INTEGER," + KEY_IMAGE_ID + " INTEGER," + "UNIQUE(" + KEY_IMAGE + ")" + "ON CONFLICT IGNORE" + ")";

    private SQLiteDatabase mDataBase;
    private ContentValues mValues;
    private Cursor mCursor;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating tables
        db.execSQL(CREATE_TABLE_HOTELS);
        db.execSQL(CREATE_TABLE_IMAGE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        onCreate(db);
    }

    /**
     * CRUD Operation
     **/

    /**
     * Adds hotel object to database
     *
     * @param hotel object
     * @return id of hotel
     */
    public void addHotel(Hotel hotel) {

        mDataBase = this.getWritableDatabase();
        mValues = new ContentValues();

        mValues.put(KEY_NAME, hotel.getName());
        mValues.put(KEY_ADDRESS, hotel.getAddress());
        mValues.put(KEY_DESCRIPTION, hotel.getDescription());
        mValues.put(KEY_RATING, hotel.getRating());

        mDataBase.insert(TABLE_HOTELS, null, mValues);
        mDataBase.close();
        mValues.clear();


    }

    /**
     * Adds image in database, image is in byte array form.
     *
     * @return imageId id of images in Table image
     */
    public void addHotelImage(int id, Image image) {

        mDataBase = this.getWritableDatabase();
        mValues = new ContentValues();

        mValues.put(KEY_IMAGE, image.getImage());
        mValues.put(KEY_MAIN_IMAGE_ID, id);
        mValues.put(KEY_IMAGE_ID, image.getImageId());


        mDataBase.insert(TABLE_IMAGE, null, mValues);
        mDataBase.close();
        mValues.clear();

    }

    /**
     * Get all hotels object
     *
     * @return List of all object hotels in database
     */
    public ArrayList<Hotel> getListViewHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_ID + "," + KEY_NAME + "," + KEY_ADDRESS + " FROM " + TABLE_HOTELS;
        mDataBase = this.getReadableDatabase();
        mCursor = mDataBase.rawQuery(selectQuery, null);
        if (mCursor.moveToFirst()) {
            do {
                Hotel h = new Hotel();
                h.setId(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)));
                h.setName(mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                h.setAddress(mCursor.getString(mCursor.getColumnIndex(KEY_ADDRESS)));
                hotels.add(h);

            } while (mCursor.moveToNext());
        }

        mDataBase.close();
        mCursor.close();
        return hotels;
    }


    /**
     * Get all main image
     *
     * @param id of main image
     * @return list of main image
     */
    public ArrayList<Image> getAllMainImage(long id) {
        ArrayList<Image> images = new ArrayList<>();
        //Select all main images
        String selectQuery = "SELECT " + KEY_IMAGE + " FROM " + TABLE_IMAGE + " WHERE " + KEY_MAIN_IMAGE_ID + "=" + id;
        mDataBase = this.getReadableDatabase();
        mCursor = mDataBase.rawQuery(selectQuery, null);
        if (mCursor.moveToFirst()) {

            do {

                Image i = new Image();
                //image
                i.setImage(mCursor.getBlob(mCursor.getColumnIndex(KEY_IMAGE)));

                images.add(i);
            } while (mCursor.moveToNext());

        }
        mDataBase.close();
        mCursor.close();
        return images;
    }

    /**
     * Returns single hotel model from database
     *
     * @param id of object in database
     * @return
     */
    public Hotel getHotelModel(long id) {
        //Select all data on id
        String selectQuery = "SELECT  * FROM " + TABLE_HOTELS + " WHERE " + KEY_ID + "=" + id;
        mDataBase = this.getReadableDatabase();
        mCursor = mDataBase.rawQuery(selectQuery, null);
        Hotel model = null;
        if (mCursor.moveToFirst()) {

            do {

                model = new Hotel(mCursor.getInt(mCursor.getColumnIndex(KEY_ID)),
                        mCursor.getInt(mCursor.getColumnIndex(KEY_RATING)),
                        mCursor.getString(mCursor.getColumnIndex(KEY_NAME)),
                        mCursor.getString(mCursor.getColumnIndex(KEY_ADDRESS)),
                        mCursor.getString(mCursor.getColumnIndex(KEY_DESCRIPTION)));

            } while (mCursor.moveToNext());
        }
        mDataBase.close();
        mCursor.close();
        return model;
    }

    public TreeMap<Integer, Image> getHotelsImage(long id) {
        TreeMap<Integer, Image> images = new TreeMap<>();
        String selectQuery = "SELECT " + KEY_IMAGE + " FROM " + TABLE_IMAGE + " WHERE " + TABLE_IMAGE + "." + KEY_IMAGE_ID + "=" + id;
        mDataBase = this.getReadableDatabase();
        mCursor = mDataBase.rawQuery(selectQuery, null);

        if (mCursor.moveToFirst()) {

            do {
                Image image = new Image();
                image.setImage(mCursor.getBlob(mCursor.getColumnIndex(KEY_IMAGE)));

                images.put(mCursor.getPosition(), image);
                Util.showLog("dali je images prazna?" + images.size());
            } while (mCursor.moveToNext());


        }
        mDataBase.close();
        mCursor.close();
        return images;
    }

    public int getHotelsCount() {
        String selectQuery = "SELECT " + KEY_ID + " " + " FROM " + TABLE_HOTELS;
        mDataBase = this.getReadableDatabase();
        mCursor = mDataBase.rawQuery(selectQuery, null);
        mCursor.moveToFirst();
        int count = mCursor.getCount();
        mDataBase.close();
        mCursor.close();

        return count;
    }


}

