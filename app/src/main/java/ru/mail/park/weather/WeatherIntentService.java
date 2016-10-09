package ru.mail.park.weather;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.*;

public class WeatherIntentService extends IntentService {
    // TODO: set it by CityActivity class public field; don't harcode
//     private final String WEATHER_LOAD_ACTION = "ru.mail.park.weather.action.weather_load";

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Log.d("WeatherIntentService", String.valueOf(intent));
        if (intent != null) {
            WeatherStorage weatherStorage = WeatherStorage.getInstance(this);

            try {
                // it's heavy operation to load weather
                // and we assign this problem to intent service
                // for background mode
                City city = weatherStorage.getCurrentCity();
                WeatherUtils weatherUtils = WeatherUtils.getInstance();
                Weather weather = weatherUtils.loadWeather(city);
                weatherStorage.saveWeather(city, weather);

                sendBroadcast(new Intent(MainActivity.WEATHER_LOAD_ACTION));
            } catch (IOException e) {
                Toast.makeText(this, "Ошибка при загрузке данных",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
