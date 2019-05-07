package com.example.maria.converter.Retrofit;

import com.example.maria.converter.Retrofit.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App {

    private static final String ROOT_URL = "https://api.exchangeratesapi.io";

    private static Retrofit gerRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return gerRetrofitInstance().create(ApiService.class);
    }
}
