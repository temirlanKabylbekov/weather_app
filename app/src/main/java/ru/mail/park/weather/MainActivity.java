package ru.mail.park.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import ru.mail.weather.lib.*;

public class MainActivity extends AppCompatActivity {

    private WeatherStorage weatherStorage;

    public final  String WEATHER_LOAD_ACTION = "ru.mail.park.weather.action.weather_load";

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // handle with buttons
        findViewById(R.id.btn_city_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CityActivity.class));
            }
        });
        findViewById(R.id.btn_enable_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundUpdate(true);
            }
        });
        findViewById(R.id.btn_disable_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundUpdate(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(this.WEATHER_LOAD_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setCityNameAndWeather();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected  void onResume() {
        super.onResume();
        setCityNameAndWeather();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void setCityNameAndWeather() {
        weatherStorage = WeatherStorage.getInstance(MainActivity.this);
        City city = weatherStorage.getCurrentCity();
        // set the name of the button
        Button button = (Button)findViewById(R.id.btn_city_name);
        button.setText(city.name());

        // set the weather info of this city
        Weather weather = weatherStorage.getLastSavedWeather(city);
        int temperature = weather.getTemperature();
        String info = weather.getDescription();
        TextView weatherInfo = (TextView)findViewById(R.id.text_weather_info);
        weatherInfo.setText(String.format("%d С: %s", temperature, info));
    }

    private void backgroundUpdate(boolean isUpdate) {
        Intent intent = new Intent(this, WeatherIntentService.class);
        if (isUpdate) {
            WeatherUtils.getInstance().schedule(this, intent);
        } else {
            WeatherUtils.getInstance().unschedule(this, intent);
        }
    }
}

