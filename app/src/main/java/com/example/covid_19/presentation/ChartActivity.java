package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.covid_19.R;
import com.example.covid_19.model.entity.Data;
import com.example.covid_19.model.response.NewsResponse;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

/**
 * @author phuocNDT
 */
public class ChartActivity extends AppCompatActivity {

    TextView tvTotalToday, tvTotal, tvActive, tvActiveToday,
            tvRecovered, tvRecoveredToday, tvDeath, tvDeathsToday, tvCountry;
    Data country;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
//        getSupportActionBar().hide();
        tvActiveToday = findViewById(R.id.activetoday);
        tvActive = findViewById(R.id.activecase);
        tvTotal = findViewById(R.id.totalcase);
        tvTotalToday = findViewById(R.id.totaltoday);
        tvDeath = findViewById(R.id.deathcase);
        tvDeathsToday = findViewById(R.id.deathtoday);
        tvRecovered = findViewById(R.id.recoveredtotal);
        tvRecoveredToday = findViewById(R.id.recoveredtoday);
        pieChart = findViewById(R.id.piechart);
        tvCountry = findViewById(R.id.tvCountry);
        country = (Data) getIntent().getExtras().getSerializable("country");
        fetchData();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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