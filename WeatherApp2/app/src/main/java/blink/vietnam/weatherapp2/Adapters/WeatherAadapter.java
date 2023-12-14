package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import blink.vietnam.weatherapp2.Model.Weather;
import blink.vietnam.weatherapp2.R;

public class WeatherAadapter extends BaseAdapter {
    Context context;
    int layout;
    List<Weather> list;
    public WeatherAadapter(Context context, int layout, List<Weather> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder {
        TextView txtCurTime;
        TextView txtState;
        ImageView imgIcon;
        TextView txtMin;
        TextView txtMax;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            viewHolder.txtCurTime = view.findViewById(R.id.txtCurTime);
            viewHolder.txtState = view.findViewById(R.id.txtState);
            viewHolder.imgIcon = view.findViewById(R.id.imgIcon);
            viewHolder.txtMin = view.findViewById(R.id.txtMin);
            viewHolder.txtMax = view.findViewById(R.id.txtMax);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Weather weather = list.get(i);
        viewHolder.txtCurTime.setText(weather.getCurrentTime());
        viewHolder.txtState.setText(weather.getState());
        Picasso.get().load(weather.getUrlIcon()).into(viewHolder.imgIcon);
        viewHolder.txtMin.setText(weather.getMin());
        viewHolder.txtMax.setText(weather.getMax());
        return view;
    }
}
