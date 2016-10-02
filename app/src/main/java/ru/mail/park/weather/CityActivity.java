package ru.mail.park.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

import ru.mail.weather.lib.*;

public class CityActivity extends AppCompatActivity {

    private  void clickHandler(String cityName) {
        City city = City.valueOf(cityName);
        WeatherStorage weatherStorage = WeatherStorage.getInstance(CityActivity.this);
        weatherStorage.setCurrentCity(city);

        Intent intent = new Intent(CityActivity.this, WeatherIntentService.class);
        startService(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        findViewById(R.id.btn_city1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler("VICE_CITY");
            }
        });

        findViewById(R.id.btn_city2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler("RACCOON_CITY");
            }
        });

        findViewById(R.id.btn_city3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler("SPRINGFIELD");
            }
        });

        findViewById(R.id.btn_city4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler("SILENT_HILL");
            }
        });

        findViewById(R.id.btn_city5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler("SOUTH_PARK");
            }
        });
    }
}