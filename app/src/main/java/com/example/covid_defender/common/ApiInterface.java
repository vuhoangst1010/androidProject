package com.example.covid_defender.common;

import com.example.covid_defender.model.entity.Data;
import com.example.covid_defender.model.response.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("countries")
    Call<List<Data>> getCountryData();

    @GET("v1/news?access_key=7b21ba393e513b91cdc2e4fc512ab20a&country=vietnam&keywords=virus")
    Call<NewsResponse> getNews();

    @GET("v1/news?access_key=7b21ba393e513b91cdc2e4fc512ab20a&keywords=virus")
    Call<NewsResponse> getNewsByPage(@Query("limit") int limit, @Query("offset") int offset);

}
