package blink.vietnam.weatherapp2.Activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blink.vietnam.weatherapp2.Adapters.WeatherAdapter;
import blink.vietnam.weatherapp2.Adapters.WeatherAdapter2;
import blink.vietnam.weatherapp2.Model.Weather;
import blink.vietnam.weatherapp2.Model.WeatherDaily;
import blink.vietnam.weatherapp2.R;

public class MainActivity2 extends AppCompatActivity {
    static final String API_KEY2="e03050c5f7b3b9e88e004569fb3404fa";
    List<WeatherDaily> weatherList2;
    WeatherAdapter2 nextAdapter;
    ListView listNextDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listNextDay=findViewById(R.id.listNextDay);
        Intent intent=getIntent();
        String city=intent.getStringExtra("hanoi");
        weatherList2=new ArrayList<>();
        nextAdapter=new WeatherAdapter2(MainActivity2.this,R.layout.activity_future,weatherList2);
        listNextDay.setAdapter(nextAdapter);
        getJsonNextDay(city);
    }
    private void getJsonNextDay(String city){
        String url2="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+API_KEY2+"&units=metric";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list=response.getJSONArray("list");
                            for (int i=0;i<list.length();i++){
                                JSONObject item=list.getJSONObject(i);
                                String days=item.getString("dt");
                                long lday=Long.parseLong(days);
                                SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, yyyy-MM-dd, HH:mm");
                                Date date=new Date(lday*1000L);
                                String timeDaily=dateFormat.format(date);
                                JSONObject main=item.getJSONObject("main");
                                String tempDaily=main.getString("temp");
                                JSONArray weather=item.getJSONArray("weather");
                                JSONObject weatherItem=weather.getJSONObject(0);
                                String statusDaily=weatherItem.getString("description");
                                String icon=weatherItem.getString("icon");
                                String iconDaily="https://openweathermap.org/img/wn/"+icon+".png";
                                weatherList2.add(new WeatherDaily(timeDaily,tempDaily,statusDaily,iconDaily));
                            }
                                Toast.makeText(MainActivity2.this,""+weatherList2.size(),Toast.LENGTH_SHORT).show();
                                nextAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity2.this,"Error".toString(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this,"Không có dữ liệu cho thành phố "+error.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("Error",error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}