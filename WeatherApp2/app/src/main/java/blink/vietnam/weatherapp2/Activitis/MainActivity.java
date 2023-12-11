package blink.vietnam.weatherapp2.Activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blink.vietnam.weatherapp2.Adapters.WeatherAdapter;
import blink.vietnam.weatherapp2.Model.Weather;
import blink.vietnam.weatherapp2.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String API_KEY="d77cb61a5967bb592700365acc89844f";
    static final String API_KEY2="691d73cc14004ca396a6d561d2bd43f5";
    EditText editTextSearch;
    Button buttonSearch,buttonNext;
    TextView textViewCity,textViewTemp,textViewStatus,textViewDay,textViewCloud,textViewWind,textViewHumid;
    ImageView imageIcon;
    TextView txtTempFeel,txtVisibility,txtPressure;
    String city="";
    List<Weather> weatherList;

    WeatherAdapter hourlyAdapter;
    ListView listHourly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         listHourly.findViewById(R.id.listHourly);
        setContentView(R.layout.activity_main);
        mapping();
        buttonSearch.setOnClickListener(this);
        if(city==""){
            getJsonWeather("Hanoi");
        }else getJsonWeather(city);
        getWeatherHourly(city);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city=buttonSearch.getText().toString();
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });
        weatherList = new ArrayList<>();
        hourlyAdapter=new WeatherAdapter(MainActivity.this,R.layout.viewholder_hourly,weatherList);
        listHourly.setAdapter(hourlyAdapter);

    }


    public void getJsonWeather(String city){
        final String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY+"&units=metric";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weatherArray=response.getJSONArray("weather");
                            JSONObject weatherObject=weatherArray.getJSONObject(0);
                            String icon=weatherObject.getString("icon");
                            String urlIcon="https://openweathermap.org/img/wn/"+icon+".png";//icon
                            Picasso.get().load(urlIcon).into(imageIcon);
                            String visibility=response.getString("visibility");
                            txtVisibility.setText(visibility+"m");
                            String name=response.getString("name");
                            textViewCity.setText(name);
                            String temperStatus=weatherObject.getString("main");// status
                            textViewStatus.setText(temperStatus);
                            JSONObject main=response.getJSONObject("main");
                            String  temp=main.getString("temp"); //temp
                            textViewTemp.setText(temp+"ºC");
                            String tempreal=main.getString("feels_like");
                            txtTempFeel.setText(tempreal+"ºC");
                            String humidity=main.getString("humidity");
                            textViewHumid.setText(humidity+"%");
                            String pressure=main.getString("pressure");
                            txtPressure.setText(pressure+"hPa");
                            JSONObject wind=response.getJSONObject("wind");
                            String speed=wind.getString("speed");
                            textViewWind.setText(speed+"m/s");
                            JSONObject clouds=response.getJSONObject("clouds");
                            String all=clouds.getString("all");
                            textViewCloud.setText(all+"%");
                            String day=response.getString("dt");
                            long lday=Long.parseLong(day);
                            SimpleDateFormat dateFormat=new SimpleDateFormat();
                            Date date=new Date(lday*1000L);
                            String curentTime=dateFormat.format(date);
                            textViewDay.setText(curentTime);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this,""+city.toString(),Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Không có dữ liệu cho thành phố "+city.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void getWeatherHourly(String city){
        String url2="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+API_KEY2+"&units=metric";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list =response.getJSONArray("list");
                            for(int i=0;i<list.length();i++){
                                JSONObject item=list.getJSONObject(i);
                                String hourly=item.getString("dt");
                                long lday=Long.parseLong(hourly);
                                SimpleDateFormat dateFormat=new SimpleDateFormat();
                                Date date=new Date(lday*1000L);
                                String time=dateFormat.format(date);
                                JSONObject main=item.getJSONObject("main");
                                JSONObject temper=item.getJSONObject("temp");
                                String temp=temper.getString("temp");
                                JSONArray weather=item.getJSONArray("weather");
                                JSONObject weatherItem=weather.getJSONObject(0);
                                String icon=weatherItem.getString("icon");
                                String urlIcon="https://openweathermap.org/img/wn/"+icon+".png";
                                weatherList.add(new Weather(time,temp,urlIcon));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this,""+city.toString(),Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Không có dữ liệu cho thành phố "+ error.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("Error",error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }




    private void mapping(){
        editTextSearch=findViewById(R.id.editTextSearch);
        buttonSearch=findViewById(R.id.buttonSearch);
        imageIcon=findViewById(R.id.imageIcon);
        textViewTemp=findViewById(R.id.textViewTemp);
        textViewStatus=findViewById(R.id.textViewStatus);
        textViewDay=findViewById(R.id.textViewDay);
        textViewCloud=findViewById(R.id.textViewCloud);
        textViewWind=findViewById(R.id.textViewWind);
        textViewHumid=findViewById(R.id.textViewHumid);
        buttonNext=findViewById(R.id.buttonNext);
        textViewCity=findViewById(R.id.textViewCity);
        txtVisibility=findViewById(R.id.txtVisibility);
        txtPressure=findViewById(R.id.txtPressure);
        txtTempFeel=findViewById(R.id.txtTempFeel);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSearch:
                city=editTextSearch.getText().toString().trim();
                if(city.equals(""))
                    city="Hanoi";
                getJsonWeather(city);
                break;
            case R.id.buttonNext:
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("city",city);
                startActivity(intent);
                break;
        }
    }
}