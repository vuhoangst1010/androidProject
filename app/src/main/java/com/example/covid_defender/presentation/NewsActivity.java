package com.example.covid_defender.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.covid_defender.R;
import com.example.covid_defender.adapter.NewsAdapter;
import com.example.covid_defender.common.ApiUtilities;
import com.example.covid_defender.common.Constants;
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
    Button btnPrevious, btnNext, btnPage;
    int page = 1, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnPage = findViewById(R.id.btnPage);
        btnPage.setText(String.valueOf(page));
        btnPrevious.setClickable(false);
        btnPage.setClickable(false);
        btnPrevious.setAlpha((float) 0.4);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataNews(Constants.PAGE_SIZE, 0);
    }

    private void fetchDataNews(int limit, int offset) {
        ApiUtilities.getApiInterfaceNews().getNewsByPage(limit, offset).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse newsResponse = response.body();
                newsList = newsResponse.getData();
                adapter = new NewsAdapter(newsList, NewsActivity.this);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Call api error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onPrevious(View view) {
        page -=1;
        if(page==1){
            btnPrevious.setClickable(false);
            btnPrevious.setAlpha((float) 0.4);
        }

        fetchDataNews(Constants.PAGE_SIZE, (page-1)*Constants.PAGE_SIZE);
        btnPage.setText(String.valueOf(page));
    }

    public void onNext(View view) {
        page +=1;
        if(page>1){
            btnPrevious.setClickable(true);
            btnPrevious.setAlpha(1);
        }
        if(page==Constants.TOTAL/Constants.PAGE_SIZE){
            btnNext.setClickable(true);
            btnNext.setAlpha(1);
        }

        fetchDataNews(Constants.PAGE_SIZE, (page-1)*Constants.PAGE_SIZE);
        btnPage.setText(String.valueOf(page));
    }
}