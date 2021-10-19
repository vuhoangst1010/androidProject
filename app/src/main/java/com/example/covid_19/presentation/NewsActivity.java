package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.covid_19.R;
import com.example.covid_19.adapter.NewsAdapter;
import com.example.covid_19.common.ApiUtilities;
import com.example.covid_19.model.entity.ModelClass;
import com.example.covid_19.model.entity.News;
import com.example.covid_19.model.response.NewsResponse;
import com.example.covid_19.model.response.ResponseData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author PhuocNDT
 */
public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<News> newsList;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataNews();
    }

    private void fetchDataNews() {
        ApiUtilities.getApiInterfaceNews().getNews().enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse newsResponse = response.body();
                newsList = newsResponse.getData();
                adapter = new NewsAdapter(newsList, NewsActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Call api error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}