package com.horvat.dragutin.androidzadatak.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.horvat.dragutin.androidzadatak.database.DatabaseHandler;
import com.horvat.dragutin.androidzadatak.models.Hotel;
import com.horvat.dragutin.androidzadatak.models.Image;
import com.horvat.dragutin.androidzadatak.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by drago on 29.6.2015..
 */
public class JsonParser extends AsyncTask<Boolean, Void, Boolean> {

    public AsyncResponse delegate = null;
    private Context mContext;
    private ProgressDialog mDialog;
    private JSONArray mJsonArray = null;

    private DatabaseHandler mDatabase;

    private boolean thereIsNew = true;

    //Models
    private Hotel mHotelModel;
    private Image mImageModel;

    //JSON nodes
    private static String TAG_HOTELS = "hotels";
    private static String TAG_ID = "id";
    private static String TAG_NAME = "name";
    private static String TAG_RATING = "rating";
    private static String TAG_ADDRESS = "address";
    private static String TAG_DESCRIPTION = "description";
    private static String TAG_IMAGE = "image";

    private static String JSON_STRING = "json";


    /**
     * Class constructor passing context from activity
     *
     * @param context
     */
    public JsonParser(Context context) {
        this.mContext = context;
    }

    /**
     * Loading Json from a file in assets
     *
     * @return string format of a json
     */
    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = mContext.getAssets().open("json_data.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    /**
     * Pars json strings
     *
     * @param string
     * @return
     */
    private int stringToInt(String string) {
        int num = 0;
        try {
            num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        return num;
    }


    @Override
    protected Boolean doInBackground(Boolean... params) {
        String jsonString = loadJSONFromAsset();
        mDatabase = new DatabaseHandler(mContext);

        /* Getting old json for comparison */
        String oldJson = Util.getStringFromPreference(mContext, "", JSON_STRING);

        if (jsonString != null && !jsonString.equals(oldJson)) {
            try {
                JSONObject mJson = new JSONObject(jsonString);

                mJsonArray = mJson.getJSONArray(TAG_HOTELS);

                for (int i = 0; i < mJsonArray.length(); i++) {

                    JSONObject mH = mJsonArray.getJSONObject(i);

                    //Getting data from json object


                    //Creating new object and adding to database
                    mHotelModel = new Hotel(stringToInt(mH.getString(TAG_ID)), stringToInt(mH.getString(TAG_RATING)),
                            mH.getString(TAG_NAME), mH.getString(TAG_ADDRESS), mH.getString(TAG_DESCRIPTION));


                    mDatabase.addHotel(mHotelModel);
                    mHotelModel = null;

                    JSONArray image = (JSONArray) mH.get(TAG_IMAGE);
                    // Getting image from json array


                    for (int j = 0; j < image.length(); j++) {


                        byte[] array = Util.bitmapToArray(Util.loadImageFromAssets(image.getString(j), mContext));

                        mImageModel = new Image(array, stringToInt(mH.getString(TAG_ID)));

                        mDatabase.addHotelImage(j, mImageModel);
                        mImageModel = null;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Util.showLog("Error" + e.getMessage());
            }
        } else {

            thereIsNew = false;
            Util.showLog("Can't load json from a file, or there is no new data in Json");
        }

        /* Saving new json to preference */
        Util.saveStringToPreference(mContext, jsonString, JSON_STRING);

        return thereIsNew;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Loading json data...");
        mDialog.show();

    }


    @Override
    protected void onPostExecute(Boolean thereIsNew) {
        super.onPostExecute(thereIsNew);

        mDatabase.close();
        if (mDialog.isShowing()) {

            mDialog.dismiss();
            Util.showToast(mContext, "finished!");
            delegate.thereIsNew(thereIsNew);
        }

        if (!thereIsNew) {
            Util.showToast(mContext, "There is no new json file!");
        }


    }
}
