package com.horvat.dragutin.androidzadatak.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.horvat.dragutin.androidzadatak.R;
import com.horvat.dragutin.androidzadatak.models.ListViewModel;

import java.util.ArrayList;

/**
 * Created by drago on 26.6.2015..
 */
public class CustomListView extends BaseAdapter {

    private ArrayList<ListViewModel> listOfHotels;
    private LayoutInflater mInflater;
    private Context mContext;


    public CustomListView(Context context, ArrayList<ListViewModel> listOfHotels) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.listOfHotels = listOfHotels;
    }


    @Override
    public int getCount() {
        return listOfHotels.size();
    }

    @Override
    public ListViewModel getItem(int position) {
        return listOfHotels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listOfHotels.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;

        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.layout_list_item, parent, false);
            mHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            mHolder.txtAddress = (TextView) convertView.findViewById(R.id.address);
            mHolder.mainImage = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ListViewModel model = listOfHotels.get(position);

        mHolder.txtTitle.setText(model.getTitle());
        mHolder.txtAddress.setText(model.getAddress());
        mHolder.mainImage.setImageBitmap(model.getImage());

        return convertView;

    }

    private class ViewHolder {
        TextView txtTitle;
        TextView txtAddress;
        ImageView mainImage;

    }
}
