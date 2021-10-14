package com.example.covid_19;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiUtilities {
    public static Retrofit retrofit = null;
    public static Retrofit retrofitNews = null;

//    public static ApiInterface getApiInterface() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//        }
//        return retrofit.create(ApiInterface.class);
//    }
    public static ApiInterface getApiInterfaceNews() {
        if (retrofitNews == null) {
            retrofitNews = new Retrofit.Builder().baseUrl(Constants.BASE_URL_NEWS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitNews.create(ApiInterface.class);
    }
}
