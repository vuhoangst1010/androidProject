package com.example.covid_19;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {
    @GET("countries")
    Call<List<ModelClass>> getCountryData();

    @GET("v1/news?access_key=3710a5ea7af49fc91f0a7a046ad875c3")
    Call<NewsClass> getNews();
}
