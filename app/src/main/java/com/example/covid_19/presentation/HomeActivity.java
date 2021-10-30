package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

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
        createNotificationChannel();

        RecycleItem r1 = new RecycleItem(R.drawable.statistics, "Data", "Covid data");
        RecycleItem r2 = new RecycleItem(R.drawable.news, "News", "Covid news");

        List<RecycleItem> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);

        HomeAdapter adapter = new HomeAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in));

        recyclerView.setOnClickListener(v -> {
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long timeAtButtonClick = System.currentTimeMillis();
            long tenSecondsInMilis = 1000 * 20;
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondsInMilis, pendingIntent);
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SonNguyenChannel";
            String description = "Channel for Son Ng";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifySon", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}