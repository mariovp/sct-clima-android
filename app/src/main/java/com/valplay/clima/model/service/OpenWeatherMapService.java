package com.valplay.clima.model.service;

import com.valplay.clima.model.entities.ClimaActual;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {

    @GET("data/2.5/weather")
    Single<ClimaActual> getClimaActual(
            @Query("id")
            String cityId,
            @Query("appid")
            String appId,
            @Query("lang")
            String lang,
            @Query("units")
            String units
    );

}
