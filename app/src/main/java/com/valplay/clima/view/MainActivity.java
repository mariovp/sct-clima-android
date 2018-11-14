package com.valplay.clima.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.valplay.clima.App;
import com.valplay.clima.R;
import com.valplay.clima.model.entities.ClimaActual;
import com.valplay.clima.model.service.OpenWeatherMapService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;

    private TextView textView_temperatura;
    private TextView textView_humedad;
    private TextView textView_ciudad;
    private TextView textView_descripcion;
    private ImageView imageView_icono_clima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_temperatura = findViewById(R.id.textView_temperatura);
        textView_humedad = findViewById(R.id.textView_humedad);
        textView_ciudad = findViewById(R.id.textView_ciudad);
        textView_descripcion = findViewById(R.id.textView_descripcion);

        imageView_icono_clima = findViewById(R.id.imageView_icono_clima);

        descargarDatosClima();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (disposable != null)
            disposable.dispose();
    }

    private void descargarDatosClima() {

        OpenWeatherMapService openWeatherMapService = ((App) getApplication()).getOpenWeatherMapService();

        disposable = openWeatherMapService.getClimaActual(
                "4012176",
                "APP_ID_DE_OPEN_WEATHER_MAP",
                "es",
                "metric"
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClimaActual>() {
                               @Override
                               public void accept(ClimaActual climaActual) throws Exception {
                                   mostrarClima(climaActual);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("ClimaActualFragment", throwable.getMessage());
                            }
                        });
    }

    private void mostrarClima(ClimaActual climaActual) {

        textView_ciudad.setText("Culiacán, Sinaloa");

        String descripcion = climaActual.getDescripcion();
        textView_descripcion.setText(descripcion);

        String temperatura = climaActual.getTemperaturaActual()+"° C";
        textView_temperatura.setText(temperatura);

        String humedad = climaActual.getHumedad()+" %";
        textView_humedad.setText(humedad);

        String nombreIcono = climaActual.getNombreIcono();
        String urlIcono = "https://openweathermap.org/img/w/"+nombreIcono+".png";

        Glide.with(this).load(urlIcono).into(imageView_icono_clima);
    }

}
