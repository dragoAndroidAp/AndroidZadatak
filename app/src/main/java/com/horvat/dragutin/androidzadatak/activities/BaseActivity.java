package com.horvat.dragutin.androidzadatak.activities;

import android.support.v7.app.ActionBarActivity;

/**
 * Created by drago on 26.6.2015..
 */
abstract class BaseActivity extends ActionBarActivity {

    /**
     *
     */
    public static final String PASSING_OBJECT = "hotel_object";
    public static final String OBJECT_ID = "object_id";

    /**
     * Used to initialize user interface for different activity's
     */
    public abstract void intUi();


    /**
     * Used to initialize listeners for different activity's
     */
    public abstract void intListeners();

    /**
     * Data initializer
     */
    public abstract void intData();


}
