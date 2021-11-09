package com.example.covid_defender.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_defender.adapter.DataAdapter;
import com.example.covid_defender.common.ApiUtilities;
import com.example.covid_defender.model.response.NewsResponse;
import com.example.covid_defender.R;
import com.example.covid_defender.model.entity.Data;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CountryCodePicker countryCodePicker;
    TextView totalToday, total, active, activeToday, recovered, recoveredToday, deaths, deathsToday;

    String country;
    TextView filter;
    Spinner spinner;
    String[] types = {"cases", "deaths", "recovered", "active"};
    private List<Data> data;
    private NewsResponse newsResponse;
    private List<Data> modelClasses2;
    PieChart pieChart;
    private RecyclerView recyclerView;
    DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        countryCodePicker = findViewById(R.id.ccp);
        activeToday = findViewById(R.id.activetoday);
        active = findViewById(R.id.activecase);
        total = findViewById(R.id.totalcase);
        totalToday = findViewById(R.id.totaltoday);
        deaths = findViewById(R.id.deathcase);
        deathsToday = findViewById(R.id.deathtoday);
        recovered = findViewById(R.id.recoveredtotal);
        recoveredToday = findViewById(R.id.recoveredtoday);
        pieChart = findViewById(R.id.piechart);
        spinner = findViewById(R.id.dataSpinner);
        filter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerview);

        data = new ArrayList<>();
        modelClasses2 = new ArrayList<>();
        newsResponse = new NewsResponse();

        Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long tenSecondsInMilis = 1000 * 20;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondsInMilis, pendingIntent);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                modelClasses2.addAll(response.body());
                dataAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
            }
        });
        dataAdapter = new DataAdapter(getApplicationContext(), modelClasses2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);

        countryCodePicker.setDefaultCountryUsingNameCode("VN");
        country = countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchData();
            }
        });

        fetchData();

    }

    private void fetchData() {
        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                data.addAll(response.body());
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getCountry().equals(country)) {
                        active.setText((data.get(i).getActive()));
                        deathsToday.setText((data.get(i).getTodayDeaths()));
                        recoveredToday.setText((data.get(i).getTodayRecovered()));
                        totalToday.setText((data.get(i).getTodayCase()));
                        total.setText((data.get(i).getCases()));
                        deaths.setText((data.get(i).getDeaths()));
                        recovered.setText((data.get(i).getRecovered()));
                        int active, total, recovered, deaths;
                        active = Integer.parseInt(data.get(i).getActive());
                        total = Integer.parseInt(data.get(i).getCases());
                        recovered = Integer.parseInt(data.get(i).getRecovered());
                        deaths = Integer.parseInt(data.get(i).getDeaths());
                        updateGraph(active, total, recovered, deaths);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call api error", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void updateGraph(int active, int total, int recovered, int deaths) {
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Confirm", total, Color.parseColor("#FFB701")));
        pieChart.addPieSlice(new PieModel("Active", active, Color.parseColor("#FF4CAF50")));
        pieChart.addPieSlice(new PieModel("Recovered", recovered, Color.parseColor("#38ACCD")));
        pieChart.addPieSlice(new PieModel("Deaths", deaths, Color.parseColor("#F55c47")));
        pieChart.startAnimation();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = types[position];
        filter.setText(item);
        dataAdapter.caseFilter(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}