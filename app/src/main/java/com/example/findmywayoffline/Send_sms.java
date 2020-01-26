package com.example.findmywayoffline;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Send_sms extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snd_msg);
        getSupportActionBar().hide();
        final EditText source = findViewById(R.id.source);
        final EditText dest = findViewById(R.id.dest);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Send_sms.this,
                    Manifest.permission.READ_SMS))
            {
                ActivityCompat.requestPermissions(Send_sms.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }
            else
            {
                ActivityCompat.requestPermissions(Send_sms.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }

        }
        else if (checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED) {

            Log.d("permission", "permission denied to SEND_SMS - requesting it");
            String[] permissions = {Manifest.permission.SEND_SMS};

            requestPermissions(permissions, 1);

        }

            /* do nothing */
            /* permission is granted */


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //TODO
        }


        final EditText phonenum = findViewById(R.id.Phonenum);
        Button submit = findViewById(R.id.Submit);
        final String smsNumber = "9870053278";
        Button msg = findViewById(R.id.Msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Send_sms.this,MainActivity.class);
                startActivity(i);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = "+91" + phonenum.getText().toString().trim();
                String message = "Please send me directions from " + source.getText().toString().trim() + "-" + dest.getText().toString().trim();

                Intent intent=new Intent(getApplicationContext(),Send_sms.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

//Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(phoneNo, null, message, pi,null);
            }
        });
    }
}
