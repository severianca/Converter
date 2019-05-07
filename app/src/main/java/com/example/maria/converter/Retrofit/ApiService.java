package com.example.maria.converter.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/latest")
    Call<ValutaList> getValuta();

}
