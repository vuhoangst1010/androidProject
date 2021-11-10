package com.example.covid_defender.common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiUtilities {
    public static Retrofit retrofit = null;
    public static Retrofit retrofitNews = null;

    public static ApiInterface getApiInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterface.class);
    }

    public static OkHttpClient getHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        //TODO : remove logging interceptors as it is to be used for development purpose
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300,TimeUnit.SECONDS).
                        addInterceptor(logging).
                        build();

        return client;
    }

    public static ApiInterface getApiInterfaceNews() {
        if (retrofitNews == null) {
            retrofitNews = new Retrofit.Builder().baseUrl(Constants.BASE_URL_NEWS).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitNews.create(ApiInterface.class);
    }
}
