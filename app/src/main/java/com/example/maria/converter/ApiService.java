package com.example.maria.converter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/latest")
    Call<ValutaList> getValuta();

}
