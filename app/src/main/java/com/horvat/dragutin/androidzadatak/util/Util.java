package com.horvat.dragutin.androidzadatak.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.horvat.dragutin.androidzadatak.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by drago on 27.6.2015..
 */
public class Util {

    /**
     * Shows toast
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * For debugging purposes
     *
     * @param o
     */
    public static void showLog(Object o) {
        if (BuildConfig.DEBUG) {
            Log.d("TESTAPP", o.toString());
        }
    }

    /**
     * Log for showing exceptions
     *
     * @param e
     */
    public static void doLogException(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.d("TESTAPP", "Exception", e);
        }
    }

    /**
     * Shows toast
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * Get image from assets
     *
     * @param name    of image
     * @param context
     * @return image in drawable object
     */
    public static Drawable loadImageFromAssets(String name, Context context) {

        Drawable image = null;
        try {
            InputStream stream = context.getAssets().open(name);

            image = Drawable.createFromStream(stream, null);


        } catch (IOException e) {

            Util.showLog(e.getMessage());
        }

        return image;
    }

    /**
     * Converts drawable to bitmap and compress it byte array
     *
     * @param d drawable
     * @return byte array
     */
    public static byte[] bitmapToArray(Drawable d) {
        byte[] array = new byte[0];
        try {
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            array = stream.toByteArray();
            bitmap.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }

    /**
     * Converts from array to bitmap
     *
     * @param image in byte array
     * @return bitmap
     */
    public static Bitmap getBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Save string to preference
     *
     * @param context
     * @param value   to save
     * @param key     key
     */
    public static void saveStringToPreference(Context context, String value, String key) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).commit();

    }

    /**
     * Retrieve String from preference
     *
     * @param context
     * @param value   to save
     * @param key
     */
    public static String getStringFromPreference(Context context, String value, String key) {

        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, value);
    }


}


