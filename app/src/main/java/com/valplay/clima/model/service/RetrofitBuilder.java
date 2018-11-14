package com.valplay.clima.model.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.valplay.clima.model.entities.ClimaActual;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public OpenWeatherMapService build() {

        OkHttpClient okHttpClient = buildHttpClient();
        Gson gsonConverter = buildGsonConverter();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(OpenWeatherMapService.class);
    }

    private OkHttpClient buildHttpClient() {

        int timeOut = 30;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();
    }

    private Gson buildGsonConverter() {

        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonDeserializer<ClimaActual> climaActualJsonDeserializer = buildClimaActualDeserializer();

        gsonBuilder.registerTypeAdapter(ClimaActual.class, climaActualJsonDeserializer);

        return gsonBuilder.create();
    }

    private JsonDeserializer<ClimaActual> buildClimaActualDeserializer() {

        return new JsonDeserializer<ClimaActual>() {
            @Override
            public ClimaActual deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                JsonObject jsonObject = json.getAsJsonObject();

                String descripcion = jsonObject.get("weather").getAsJsonArray()
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();

                String nombreIcono = jsonObject.get("weather").getAsJsonArray()
                        .get(0).getAsJsonObject()
                        .get("icon").getAsString();

                int temperatura = jsonObject.get("main").getAsJsonObject()
                        .get("temp").getAsInt();

                int humedad = jsonObject.get("main").getAsJsonObject()
                        .get("humidity").getAsInt();

                return new ClimaActual(
                        descripcion,
                        nombreIcono,
                        temperatura,
                        humedad
                );
            }
        };
    }

}
