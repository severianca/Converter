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
    public double value;
    public String[] converter;

    public ListAdapter adapter;
    public ListView listCountry;

    public Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueText = (EditText) findViewById(R.id.value1);
        valueText.setOnClickListener(this);


        converter = new String[13];
        ratio = new Double[13];

        startTimer();


        String[] country = getResources().getStringArray(R.array.country);
        String[] nameCountry = getResources().getStringArray(R.array.nameCountry);


        int[] image = { R.drawable.bulgaria,
                        R.drawable.newzealand,
                        R.drawable.israel,
                        R.drawable.russia,
                        R.drawable.canada,
                        R.drawable.philippines,
                        R.drawable.switzerland,
                        R.drawable.australia,
                        R.drawable.japan,
                        R.drawable.turkey,
                        R.drawable.china,
                        R.drawable.malaysia,
                        R.drawable.thailand
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
                        ratio[7] = response.body().getRates().getAUD();
                        ratio[8] = response.body().getRates().getJPY();
                        ratio[9] = response.body().getRates().getTRY();
                        ratio[10] = response.body().getRates().getHKD();
                        ratio[11] = response.body().getRates().getMYR();
                        ratio[12] = response.body().getRates().getTHB();

                    }
                    @Override
                    public void onFailure(Call<ValutaList> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "ошибка подключения к интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 3000);
    }


    @Override
    public void onClick(View v) {
        /*if (valueText.length()==0){
            //значит, ничего не введено (оповестим об этом человека)
            Toast.makeText(getApplicationContext(), "введите необходимое число для конвертации", Toast.LENGTH_SHORT).show();
        }*/

        flag=true;

        try {
            value = Double.parseDouble(valueText.getText().toString());
        } catch (NumberFormatException e){
            if (valueText.length()==0) {
                Toast.makeText(getApplicationContext(), "введите необходимое число для конвертации", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "введите число через точку", Toast.LENGTH_SHORT).show();
            }

            flag= false;
        }

        if (flag) {

            for (int i = 0; i < ratio.length; i++) {
                value = Double.parseDouble(valueText.getText().toString());
                converter[i] = String.format("%.3f", ratio[i] * value);
            }
            listCountry.setAdapter(adapter);

        }
    }


}
