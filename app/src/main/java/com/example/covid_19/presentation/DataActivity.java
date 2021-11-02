package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.R;
import com.example.covid_19.adapter.Adapter;
import com.example.covid_19.common.ApiUtilities;
import com.example.covid_19.model.entity.ModelClass;
import com.example.covid_19.model.response.NewsResponse;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CountryCodePicker countryCodePicker;
    TextView totalToday, total, active, activeToday, recovered, recoveredToday, deaths, deathsToday;

    String country;
    TextView filter;
    Spinner spinner;
    String[] types = {"cases", "deaths", "recovered", "active"};
    private List<ModelClass> modelClasses;
    private NewsResponse newsResponse;
    private List<ModelClass> modelClasses2;
    List<ModelClass> filterList;
    PieChart pieChart;
    private RecyclerView recyclerView;
    Adapter adapter;
    EditText edtCountry;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        getSupportActionBar().hide();
        countryCodePicker = findViewById(R.id.ccp);
//        activeToday = findViewById(R.id.activetoday);
//        active = findViewById(R.id.activecase);
//        total = findViewById(R.id.totalcase);
//        totalToday = findViewById(R.id.totaltoday);
//        deaths = findViewById(R.id.deathcase);
//        deathsToday = findViewById(R.id.deathtoday);
//        recovered = findViewById(R.id.recoveredtotal);
//        recoveredToday = findViewById(R.id.recoveredtoday);
//        pieChart = findViewById(R.id.piechart);
        edtCountry = findViewById(R.id.edtCountry);
        spinner = findViewById(R.id.dataSpinner);
        filter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerview);

        modelClasses = new ArrayList<>();
        modelClasses2 = new ArrayList<>();
        newsResponse = new NewsResponse();

        Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DataActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(DataActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long tenSecondsInMilis = 1000 * 20;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondsInMilis, pendingIntent);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClasses2.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
            }
        });
        filterList = modelClasses2;
        edtCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyword = edtCountry.getText().toString();
                adapter.countryFilter(keyword);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                keyword = edtCountry.getText().toString();
                adapter.countryFilter(keyword);
            }
        });
        adapter = new Adapter(getApplicationContext(), filterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

//        countryCodePicker.setDefaultCountryUsingNameCode("VN");
//        country = countryCodePicker.getSelectedCountryName();
//        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
//            @Override
//            public void onCountrySelected() {
//                country = countryCodePicker.getSelectedCountryName();
//                fetchData();
//            }
//        });
//
//        fetchData();

    }

    private void fetchData() {
        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClasses.addAll(response.body());
                for (int i = 0; i < modelClasses.size(); i++) {
                    if (modelClasses.get(i).getCountry().equals(country)) {
                        active.setText((modelClasses.get(i).getActive()));
                        deathsToday.setText((modelClasses.get(i).getTodayDeaths()));
                        recoveredToday.setText((modelClasses.get(i).getTodayRecovered()));
                        totalToday.setText((modelClasses.get(i).getTodayCase()));
                        total.setText((modelClasses.get(i).getCases()));
                        deaths.setText((modelClasses.get(i).getDeaths()));
                        recovered.setText((modelClasses.get(i).getRecovered()));
                        int active, total, recovered, deaths;
                        active = Integer.parseInt(modelClasses.get(i).getActive());
                        total = Integer.parseInt(modelClasses.get(i).getCases());
                        recovered = Integer.parseInt(modelClasses.get(i).getRecovered());
                        deaths = Integer.parseInt(modelClasses.get(i).getDeaths());
                        updateGraph(active, total, recovered, deaths);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
                Toast.makeText(DataActivity.this, "Call api error", Toast.LENGTH_SHORT).show();
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
        adapter.caseFilter(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onVietnam(View view) {
        edtCountry.setText("Vietnam");
    }

    public void onUS(View view) {
        edtCountry.setText("US");
    }

    public void onUK(View view) {
        edtCountry.setText("UK");
    }
}