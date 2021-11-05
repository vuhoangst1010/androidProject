package com.example.covid_19.common;

import com.example.covid_19.model.entity.Data;
import com.example.covid_19.model.response.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("countries")
    Call<List<Data>> getCountryData();

    @GET("v1/news?access_key=3710a5ea7af49fc91f0a7a046ad875c3&country=us&categories=health&keywords=virus")
//    @GET("v1/news?access_key=3710a5ea7af49fc91f0a7a046ad875c3-fake_access_key&country=us&categories=health&keywords=virus")
    Call<NewsResponse> getNews();

}
