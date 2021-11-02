package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.covid_19.R;
import com.example.covid_19.model.entity.ModelClass;
import com.example.covid_19.model.response.NewsResponse;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

public class ChartActivity extends AppCompatActivity {

    TextView tvTotalToday, tvTotal, tvActive, tvActiveToday, tvRecovered, tvRecoveredToday, tvDeath, tvDeathsToday, tvCountry;

    ModelClass country;
    TextView filter;
    Spinner spinner;
    String[] types = {"cases", "deaths", "recovered", "active"};
    private List<ModelClass> modelClasses;
    private NewsResponse newsResponse;
    private List<ModelClass> modelClasses2;
    PieChart pieChart;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().hide();
        tvActiveToday = findViewById(R.id.activetoday);
        tvActive = findViewById(R.id.activecase);
        tvTotal = findViewById(R.id.totalcase);
        tvTotalToday = findViewById(R.id.totaltoday);
        tvDeath = findViewById(R.id.deathcase);
        tvDeathsToday = findViewById(R.id.deathtoday);
        tvRecovered = findViewById(R.id.recoveredtotal);
        tvRecoveredToday = findViewById(R.id.recoveredtoday);
        pieChart = findViewById(R.id.piechart);
        filter = findViewById(R.id.filter);
        tvCountry = findViewById(R.id.tvCountry);
        country = (ModelClass) getIntent().getExtras().getSerializable("country");
        fetchData();
    }

    private void fetchData() {
        int active, total, recovered, death, activeToday, recoveredToday, deathToday;
        active = Integer.parseInt(country.getActive());
        total = Integer.parseInt(country.getCases());
        recovered = Integer.parseInt(country.getRecovered());
        death = Integer.parseInt(country.getDeaths());
        activeToday = Integer.parseInt(country.getTodayCase());
        recoveredToday = Integer.parseInt(country.getTodayRecovered());
        deathToday = Integer.parseInt(country.getTodayDeaths());
        tvActive.setText((country.getActive()));
        tvTotal.setText((country.getCases()));
        tvDeath.setText((country.getDeaths()));
        tvRecovered.setText((country.getRecovered()));
        tvActiveToday.setText("+"+country.getTodayCase());
        tvDeathsToday.setText(("+"+country.getTodayDeaths()));
        tvRecoveredToday.setText(("+"+country.getTodayRecovered()));
        tvTotalToday.setText(("+"+(activeToday+recoveredToday+deathToday)));
        tvCountry.setText(country.getCountry());
        updateGraph(active, total, recovered, death);
    }


    private void updateGraph(int active, int total, int recovered, int deaths) {
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Total", total, Color.parseColor("#FFB701")));
        pieChart.addPieSlice(new PieModel("Active", active, Color.parseColor("#FF4CAF50")));
        pieChart.addPieSlice(new PieModel("Recovered", recovered, Color.parseColor("#38ACCD")));
        pieChart.addPieSlice(new PieModel("Deaths", deaths, Color.parseColor("#F55c47")));
        pieChart.startAnimation();
    }
}