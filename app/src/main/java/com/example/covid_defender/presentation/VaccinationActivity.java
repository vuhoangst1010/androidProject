package com.example.covid_defender.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_defender.R;
import com.example.covid_defender.model.entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VaccinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void register(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserInfor");
        String id = ((TextView)findViewById(R.id.idUser)).getText().toString();
        String username = ((TextView)findViewById(R.id.username)).getText().toString();
        String birthDate = ((TextView)findViewById(R.id.birthDate)).getText().toString();
        String address = ((TextView)findViewById(R.id.address)).getText().toString();
        String phoneNumber = ((TextView)findViewById(R.id.phoneNumber)).getText().toString();
        RadioGroup rg = (RadioGroup) findViewById(R.id.gender);
        String gender =((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        User infor = new User(username,phoneNumber,birthDate,gender,id,address);
        myRef.child(infor.getId()).setValue(infor);
//        ((TextView)findViewById(R.id.status)).setText("đăng kí thành công");

        Toast.makeText(VaccinationActivity.this, "Register successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
}