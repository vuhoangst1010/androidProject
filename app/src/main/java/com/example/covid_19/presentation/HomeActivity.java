package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.example.covid_19.adapter.HomeAdapter;
import com.example.covid_19.R;
import com.example.covid_19.model.entity.RecycleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PhuocNDT
 */
public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recActivity);

        RecycleItem r1 = new RecycleItem(R.drawable.statistics, "Data", "Covid data");
        RecycleItem r2 = new RecycleItem(R.drawable.news, "News", "Covid news");

        List<RecycleItem> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);

        HomeAdapter adapter = new HomeAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in));
    }
}