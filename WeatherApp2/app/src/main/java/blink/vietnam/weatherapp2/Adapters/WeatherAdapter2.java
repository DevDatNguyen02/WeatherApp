package blink.vietnam.weatherapp2.Adapters;

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
import blink.vietnam.weatherapp2.Model.WeatherDaily;
import blink.vietnam.weatherapp2.R;

public class WeatherAdapter2 extends BaseAdapter {
    Context context2;
    int layout2;
    List<WeatherDaily> list2;

    public WeatherAdapter2(Context context2, int layout2, List<WeatherDaily> list2) {
        this.context2 = context2;
        this.layout2 = layout2;
        this.list2 = list2;
    }


    @Override
    public int getCount() {

        return list2.size();
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
        TextView statusTxt;
        TextView tempTxt;
        ImageView imgIcon;
        TextView dayTxt;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WeatherAdapter2.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new WeatherAdapter2.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context2.getSystemService(context2.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout2, null);
            viewHolder.statusTxt.findViewById(R.id.statusTxt);
            viewHolder.tempTxt.findViewById(R.id.tempTxt);
            viewHolder.dayTxt.findViewById(R.id.dayTxt);
            viewHolder.imgIcon.findViewById(R.id.imgIcon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (WeatherAdapter2.ViewHolder) view.getTag();
        }
        WeatherDaily weather = list2.get(i);
        viewHolder.dayTxt.setText(weather.getTimeDaily());
        viewHolder.tempTxt.setText(weather.getTempDaily());
        viewHolder.statusTxt.setText(weather.getStatusDaily());
        Picasso.get().load(weather.getIconDaily()).into(viewHolder.imgIcon);
        return view;
    }





}
