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

public class HelpDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);

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
                String url = "https://afternoon-chamber-99029.herokuapp.com/send?msg="+body.substring(31);

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

                String urlres = "https://afternoon-chamber-99029.herokuapp.com/recv";

                StringRequest request = new StringRequest(StringRequest.Method.GET, urlres, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                           // Log.d("RESPOBSE",response);
                            JSONObject jsonObject = new JSONObject(response);

                            String  dir = jsonObject.getString("content");

                            dir = dir.substring(3);

                            dir = dir.substring(0,dir.length()-4);

                            Log.d("ANS",dir);

                            String indi[] = dir.split("::");
                            Log.d("SIZE",String.valueOf(indi.length));

                            int charlen = 0;

                            int k=0;

                            String sms_res = "";
                            while(k<indi.length-1) {


                                int i = k;
                                for (i = k; i < indi.length; i++) {

                                    charlen += indi[i].length();
                                    if (charlen >= 155) {
                                        charlen -= indi[i].length();
                                        //i--;
                                       // Log.d("SMS1",sms_res);
                                        String phoneNo = phone.trim();


                                        Intent intent=new Intent(getApplicationContext(),Send_sms.class);
                                        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

//Get the SmsManager instance and call the sendTextMessage method to send message
                                        SmsManager sms=SmsManager.getDefault();
                                        sms.sendTextMessage(phoneNo, null, sms_res, pi,null);
                                        break;
                                    }
                                    sms_res += indi[i]+"\n";
                                    charlen++;

                                    if(i==indi.length-1){
                                        String phoneNo = phone.trim();


                                        Intent intent=new Intent(getApplicationContext(),Send_sms.class);
                                        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

//Get the SmsManager instance and call the sendTextMessage method to send message
                                        SmsManager sms=SmsManager.getDefault();
                                        sms.sendTextMessage(phoneNo, null, sms_res, pi,null);
                                    }

                                    Log.d("DEBUG",sms_res);
                                }
                                //Log.d("SMS",sms_res);
                                //SEND SMS


                                sms_res = "";
                                //int j;
                                charlen = 0;

                                k = i;
                            }

                        } catch (Exception e) {

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
