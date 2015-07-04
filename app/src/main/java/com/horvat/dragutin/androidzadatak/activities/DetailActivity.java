package com.horvat.dragutin.androidzadatak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.horvat.dragutin.androidzadatak.R;
import com.horvat.dragutin.androidzadatak.database.DatabaseHandler;
import com.horvat.dragutin.androidzadatak.models.Hotel;
import com.horvat.dragutin.androidzadatak.models.Image;
import com.horvat.dragutin.androidzadatak.util.Util;

import java.util.TreeMap;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private TextView mAddress;
    private TextView mDescription;

    private ImageView mMainImg;
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;

    private RatingBar mRatingBar;
    private Hotel mHotel;
    private TreeMap<Integer, Image> mImages;
    private DatabaseHandler mDatabase;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        intData();
        intUi();
        showData();
        intListeners();

    }

    /**
     * Setting resource
     */
    private void showData() {

        setTitle(mHotel.getName());
        mTitle.setText(mHotel.getName());
        mAddress.setText(mHotel.getAddress());
        mDescription.setText(mHotel.getDescription());
        mRatingBar.setRating(mHotel.getRating());

        for (int i = 0; i < mImages.size(); i++) {

//            Util.showLog("KOJE su slike u array u aktivnosti: " + mImages.get(i).getBitmap().getByteCount() + " " + "koliko je velika array:" + mImages.size());
            mMainImg.setImageBitmap(Util.getBitmap(mImages.get(i).getImage()));
            mImg1.setImageBitmap(Util.getBitmap(mImages.get(i).getImage()));
            mImg2.setImageBitmap(Util.getBitmap(mImages.get(i).getImage()));
            mImg3.setImageBitmap(Util.getBitmap(mImages.get(i).getImage()));
        }


    }


    @Override
    public void intUi() {

        mTitle = (TextView) findViewById(R.id.detail_title);
        mAddress = (TextView) findViewById(R.id.detail_adress);
        mDescription = (TextView) findViewById(R.id.detail_description);

        mMainImg = (ImageView) findViewById(R.id.main_image);
        mImg1 = (ImageView) findViewById(R.id.image_num_one);
        mImg2 = (ImageView) findViewById(R.id.image_num_two);
        mImg3 = (ImageView) findViewById(R.id.image_num_three);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);

    }


    @Override
    public void intListeners() {

        mMainImg.setOnClickListener(this);
        mImg1.setOnClickListener(this);
        mImg2.setOnClickListener(this);
        mImg3.setOnClickListener(this);
    }

    @Override
    public void intData() {

        mIntent = getIntent();
        long id = mIntent.getLongExtra(OBJECT_ID, 1);
        Util.showLog("id koji prosljeđujeom u bazu" + id);

        mDatabase = new DatabaseHandler(this);
        mHotel = mDatabase.getHotelModel(id);
        mImages = mDatabase.getHotelsImage(id);
//        Util.showLog("ID:" + mHotel.getId() + "Name:" + mHotel.getName() + "Address:" + mHotel.getAddress() +
//                "Description:" + mHotel.getDescription() + "Array slika ima dužinu:" + mImages.get(1).getBitmap().getByteCount());

        mDatabase.close();


    }


    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        //TODO implement bonus image view activity
        switch (v.getId()) {
            case R.id.main_image:

                //do something
                break;
            case R.id.image_num_one:
                break;
            case R.id.image_num_two:
                break;
            case R.id.image_num_three:
                break;


        }
    }
}
