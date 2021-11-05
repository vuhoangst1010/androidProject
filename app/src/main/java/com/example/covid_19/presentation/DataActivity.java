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
import com.example.covid_19.adapter.DataAdapter;
import com.example.covid_19.common.ApiUtilities;
import com.example.covid_19.model.entity.Data;
import com.example.covid_19.model.response.NewsResponse;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author phuocNDT
 */
public class DataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CountryCodePicker countryCodePicker;
    TextView totalToday, total, active, activeToday, recovered, recoveredToday, deaths, deathsToday;

    String country;
    TextView filter;
    Spinner spinner;
    String[] types = {"cases", "deaths", "recovered", "active"};
    private List<Data> data;
    List<Data> filterList;
    PieChart pieChart;
    private RecyclerView recyclerView;
    DataAdapter dataAdapter;
    EditText edtCountry;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        getSupportActionBar().hide();
        edtCountry = findViewById(R.id.edtCountry);
        spinner = findViewById(R.id.dataSpinner);
        filter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerview);

        data = new ArrayList<>();

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

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                data.addAll(response.body());
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
            }
        });
        filterList = data;
        edtCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyword = edtCountry.getText().toString();
                dataAdapter.countryFilter(keyword);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                keyword = edtCountry.getText().toString();
                dataAdapter.countryFilter(keyword);
            }
        });
        dataAdapter = new DataAdapter(getApplicationContext(), filterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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