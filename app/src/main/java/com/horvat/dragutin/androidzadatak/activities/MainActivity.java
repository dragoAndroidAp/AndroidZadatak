package com.horvat.dragutin.androidzadatak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.horvat.dragutin.androidzadatak.R;
import com.horvat.dragutin.androidzadatak.adapters.CustomListView;
import com.horvat.dragutin.androidzadatak.data.AsyncResponse;
import com.horvat.dragutin.androidzadatak.data.JsonParser;
import com.horvat.dragutin.androidzadatak.database.DatabaseHandler;
import com.horvat.dragutin.androidzadatak.models.Hotel;
import com.horvat.dragutin.androidzadatak.models.Image;
import com.horvat.dragutin.androidzadatak.models.ListViewModel;
import com.horvat.dragutin.androidzadatak.util.Util;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, AsyncResponse {


    private ListView mLocationList;
    private CustomListView mListViewAdapter;
    public Intent mIntent;

    private JsonParser mParse;
    private DatabaseHandler mDatabase;
    private ArrayList<ListViewModel> mList;
    private ArrayList<Hotel> mHotel;
    private ArrayList<Image> mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        intUi();
        intListeners();
        intData();


    }

    @Override
    public void thereIsNew(boolean thereIsNew) {


        ListViewModel listModel;
        mList = new ArrayList<>();
        mDatabase = new DatabaseHandler(this);
        mHotel = mDatabase.getListViewHotels();
        mImage = mDatabase.getAllMainImage(0);
//        int i = mDatabase.getHotelsCount();

        //Refreshing user interface
        for (int i = mDatabase.getHotelsCount() - 10; i < mDatabase.getHotelsCount(); i++) {

            listModel = new ListViewModel();
            listModel.setId(mHotel.get(i).getId());
            listModel.setTitle(mHotel.get(i).getName());
            listModel.setAddress(mHotel.get(i).getAddress());
            listModel.setImage(Util.getBitmap(mImage.get(i).getImage()));

            mList.add(listModel);

        }


        mListViewAdapter = new
                CustomListView(this, mList);
        mLocationList.setAdapter(mListViewAdapter);
        mList = null;


    }


    @Override
    public void intUi() {

        mLocationList = (ListView) findViewById(R.id.list_view_item);
        setTitle(getResources().getString(R.string.main_activity_name));
    }

    @Override
    public void intListeners() {
        mLocationList.setOnItemClickListener(this);
    }


    @Override
    public void intData() {

        mParse = new JsonParser(this);
        mParse.delegate = this;
        mParse.execute();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        /**
         * Starting new activity and passing id of object to new activity, this id is the same as in database and is used as a reference to to pull data from database
         */
        mIntent = new Intent(this, DetailActivity.class);
        Util.showLog("id za bazu je:" + id);
        mIntent.putExtra(OBJECT_ID, id);
        this.startActivity(mIntent);
    }


}
