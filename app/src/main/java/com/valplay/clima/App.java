package com.valplay.clima;

import android.app.Application;

import com.valplay.clima.model.service.OpenWeatherMapService;
import com.valplay.clima.model.service.RetrofitBuilder;

public class App extends Application {

    private OpenWeatherMapService openWeatherMapService;

    public App() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        openWeatherMapService = retrofitBuilder.build();

    }

    public OpenWeatherMapService getOpenWeatherMapService() {
        return openWeatherMapService;
    }

}
