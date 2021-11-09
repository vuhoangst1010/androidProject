package com.example.covid_defender.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.covid_defender.R;
import com.example.covid_defender.adapter.NewsAdapter;
import com.example.covid_defender.common.ApiUtilities;
import com.example.covid_defender.model.entity.News;
import com.example.covid_defender.model.response.NewsResponse;

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
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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