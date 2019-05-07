package com.example.maria.converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.example.maria.converter.Retrofit.App;
import com.example.maria.converter.Retrofit.ValutaList;

import android.view.View.OnClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements OnClickListener {

    private Timer timer;
    public Double[] ratio;
    public EditText valueText;
    public int value;
    public String[] converter;

    public ListAdapter adapter;
    public ListView listCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueText = (EditText) findViewById(R.id.value1);
        valueText.setOnClickListener(this);

        converter = new String[7];
        ratio = new Double[7];

        startTimer();

        String[] country = {"BGN", "NZD", "ILS", "RUB", "CAD", "PHP", "CHF"};
        String[] nameCountry = {"Bulgaria",
                                "new Zerland",
                                "Israel",
                                "Russia",
                                "Canada",
                                "Philippines",
                                "Switzerland"};
        int[] image = { R.drawable.bulgaria,
                        R.drawable.newzealand,
                        R.drawable.israel,
                        R.drawable.russia,
                        R.drawable.canada,
                        R.drawable.philippines,
                        R.drawable.switzerland
                        };

        adapter = new CustomAdapter(this, image, country, nameCountry, converter);
        listCountry = (ListView) findViewById(R.id.listValuta);
        listCountry.setAdapter(adapter);
    }


    //каждые 3 секунды запрашиваем данные
    public void startTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                App.getApiService().getValuta().enqueue(new Callback<ValutaList>(){
                    @Override
                    public void onResponse(Call<ValutaList> call, Response<ValutaList> response) {
                        ratio[0] = response.body().getRates().getBGN();
                        ratio[1] = response.body().getRates().getNZD();
                        ratio[2] = response.body().getRates().getILS();
                        ratio[3] = response.body().getRates().getRUB();
                        ratio[4] = response.body().getRates().getCAD();
                        ratio[5] = response.body().getRates().getPHP();
                        ratio[6] = response.body().getRates().getCHF();
                    }
                    @Override
                    public void onFailure(Call<ValutaList> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 3000);
    }


    @Override
    public void onClick(View v) {
        for (int i =0; i<7; i++){
            value = Integer.parseInt(valueText.getText().toString());
            converter[i] = Double.toString((double) (ratio[i] * value));
        }

        listCountry.setAdapter(adapter);
    }
}
