package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recActivity);

        RecycleItem r1 = new RecycleItem(R.drawable.logo, "Data", "Covid data");
        RecycleItem r2 = new RecycleItem(R.drawable.logo, "News", "Covid news");

        List<RecycleItem> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);

        MyCustomAdapter adapter = new MyCustomAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in));
    }
}