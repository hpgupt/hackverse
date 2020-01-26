package com.example.findmywayoffline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HelpDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);

        Intent i =getIntent();
        String phone = i.getStringExtra("Phone");
        String body = i.getStringExtra("Body");
        String lat = i.getStringExtra("Lat");
        String longi = i.getStringExtra("Longi");
        String ts = i.getStringExtra("TS");

        TextView phoneNum,tex,lati,lgi,TS;
        phoneNum = findViewById(R.id.Phone);
        tex  = findViewById(R.id.Body);
        lati= findViewById(R.id.Lat);
        lgi = findViewById(R.id.Longi);
        TS = findViewById(R.id.TS);
        Button getgps = findViewById(R.id.sendgps);

        phoneNum.setText(phone);
        tex.setText(body);
        lati.setText(lat);
        lgi.setText(longi);
        TS.setText(ts);
    }
}
