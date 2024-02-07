package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView textResult;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    DecimalFormat df = new DecimalFormat("#.#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city =findViewById(R.id.city);
        textResult=findViewById(R.id.textView);
        imageView1=findViewById(R.id.clear);
        imageView2=findViewById(R.id.clouds);
        imageView3=findViewById(R.id.drizzle);
        imageView4=findViewById(R.id.mist);
        imageView5=findViewById(R.id.snow);
    }

    public void getCurrentWeather(View view) {
        String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
        String apiKey = "d2ce52f6fe7d5903ef26865fbd1df0ce";

        String c = city.getText().toString().trim();

        Uri.Builder builder = Uri.parse(baseUrl)
                .buildUpon()
                .appendQueryParameter("q", c)
                .appendQueryParameter("appid", apiKey);

        String urlF = builder.build().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlF, new Response.Listener<String>() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {
                    clear();
                    JSONObject json= null;
                    try {
                        json = new JSONObject(response);

                        String main = json.getJSONArray("weather").getJSONObject(0).getString("main");
                        JSONObject main2 = json.getJSONObject("main");

                        double temp = main2.getDouble("temp");

                        String humidity = main2.getString("humidity");

                        String speed =json.getJSONObject("wind").getString("speed");

                        String place = json.getString("name");

                        textResult.setText("Current weather of " + place + "\nTemperature: " + df.format(temp) + " K" + "\nState of sky: " + main + "\nHumidity: " + humidity + " g/m³" + "\nWind speed: " + speed + " km/h");
                        represent(main);


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
           }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                    textResult.setText("the city has not been found");
                }
            });
            RequestQueue r = Volley.newRequestQueue(getApplicationContext());
            r.add(stringRequest);
        }

        public void represent(String main){
            if(main.equalsIgnoreCase("Clear")){
                imageView1.setVisibility(View.VISIBLE);
            }
            if(main.equalsIgnoreCase("Clouds")){
                imageView2.setVisibility(View.VISIBLE);
            }
            if(main.equalsIgnoreCase("Drizzle")){
                imageView3.setVisibility(View.VISIBLE);
            }
            if(main.equalsIgnoreCase("Mist")){
                imageView4.setVisibility(View.VISIBLE);
            }
            if(main.equalsIgnoreCase("Snow")){
                imageView5.setVisibility(View.VISIBLE);
            }
            if(main.equalsIgnoreCase("Rain")){
                imageView3.setVisibility(View.VISIBLE);
            }
        }

        public void clear(){
            imageView1.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
        }

    }


