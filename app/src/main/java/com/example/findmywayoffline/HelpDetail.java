package com.example.findmywayoffline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class HelpDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);
        getSupportActionBar().hide();

        Intent i =getIntent();
        final String phone = i.getStringExtra("Phone");
        final String body = i.getStringExtra("Body");
        String lat = i.getStringExtra("Lat");
        String longi = i.getStringExtra("Longi");
        String ts = i.getStringExtra("TS");

        TextView phoneNum,tex;
        phoneNum = findViewById(R.id.Phone);
        tex  = findViewById(R.id.Body);

        Button getgps = findViewById(R.id.sendgps);

        phoneNum.setText(phone);
        tex.setText(body);


        //31-;
        getgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://immense-sierra-43855.herokuapp.com/send?msg="+body.substring(31)+"-laptop";

                Log.d("URL",url);

                StringRequest request1 = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
Log.d("POST",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                final int[] c = {0};
                String urlres = "https://immense-sierra-43855.herokuapp.com/recv";

                final StringRequest request = new StringRequest(StringRequest.Method.GET, urlres, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                           // Log.d("RESPOBSE",response);
                            JSONObject jsonObject = new JSONObject(response);

                            String  dir = jsonObject.getString("directions");

                            dir = dir.substring(3);

                            dir = dir.substring(0,dir.length()-4);


                            Log.d("ANS",dir);

                            String indi[] = {dir};
                            Log.d("SIZE",String.valueOf(indi.length));

                            int charlen = 0;

                            int k=0;

                            int len = dir.length();

                            int lo = len/155;
                            int mo = len-lo*155;
                            String sms_res = "";
                            //int k =0;
                            while(k<lo) {
                                    sms_res = "";
                                        String phoneNo = phone.trim();
                                sms_res += dir.substring(k*155,(k+1)*155);

                                        Intent intent=new Intent(getApplicationContext(),Send_sms.class);
                                        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

//Get the SmsManager instance and call the sendTextMessage method to send message
                                        SmsManager sms=SmsManager.getDefault();
                                        sms.sendTextMessage(phoneNo, null, sms_res, pi,null);
                                        k++;


                                    }

                                    sms_res = "";
                            String phoneNo = phone.trim();
                            sms_res += dir.substring(k*155,dir.length()-1);

                            Intent intent=new Intent(getApplicationContext(),Send_sms.class);
                            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

//Get the SmsManager instance and call the sendTextMessage method to send message
                            SmsManager sms=SmsManager.getDefault();
                            sms.sendTextMessage(phoneNo, null, sms_res, pi,null);


                            //Log.d("SMS",sms_res);
                                //SEND SMS

                        } catch (Exception e) {
                            c[0] =1;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(HelpDetail.this);
                requestQueue.add(request1);
                requestQueue.add(request);

            }
        });
    }
}
