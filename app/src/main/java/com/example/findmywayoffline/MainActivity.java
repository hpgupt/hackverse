package com.example.findmywayoffline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button btnSent, btnInbox, btnDraft;
    TextView lblMsg, lblNo;
    RecyclerView lvMsg;
    int val = 1;
    // Cursor Adapter
    SimpleCursorAdapter adapter;
    Adapter ad;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_SMS))
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }

        }
        else
        {
            /* do nothing */
            /* permission is granted */
        }

        // Init GUI Widget

        btnSent = (Button) findViewById(R.id.btnSentBox);
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = 1;

                onClicker(view);
            }
        });

        btnDraft = (Button) findViewById(R.id.btnDraft);
        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val=2;
                onClicker(view);
            }
        });

        lvMsg = (RecyclerView) findViewById(R.id.lvMsg);
        lvMsg.setLayoutManager(new LinearLayoutManager(this));


    }
    public void onClicker(View v) {

        if (val == 1) {

            // Create Inbox box URI
            Uri inboxURI = Uri.parse("content://sms/inbox");

            // List required columns
            String[] reqCols = new String[] { "_id", "address", "body" };

            // Get Content Resolver object, which will deal with Content
            // Provider
            ContentResolver cr = getContentResolver();

            // Fetch Inbox SMS Message from Built-in Content Provider
            Cursor c = cr.query(inboxURI, reqCols, null, null, null);
           // Log.d("COUNT",String.valueOf(c.getCount()));


            ArrayList<UserDetails> data = new ArrayList<UserDetails>();
            //UserDetails data = null;

            while (c.moveToNext()){

                int index;

                index = c.getColumnIndexOrThrow("address");
                String address = c.getString(index);

                index = c.getColumnIndexOrThrow("body");
                String body = c.getString(index);

//                index = c.getColumnIndexOrThrow("id");
//                long id = c.getLong(index);

                if(body.trim().contains("Please send me directions from "))
                {
                    String tm = "2:30";
                    String longi = "2";
                    String lat = "2";
                    UserDetails temp = new UserDetails(lat,longi,tm,body,address);
                    data.add(temp);
                    if (ad!=null)
                        ad.notifyDataSetChanged();
                }

            }
            Log.d("SIZE",String.valueOf(data.size()));

            //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.activity_list_item,android.R.id.text1,data);
            // Attached Cursor with adapter and display in listview
            Adapter ad = new Adapter(getApplicationContext(),data);
            lvMsg.setAdapter(ad);


        }

        if (val == 2) {

            // Create Sent box URI
            Uri sentURI = Uri.parse("content://sms/sent");

            // List required columns
            String[] reqCols = new String[] { "_id", "address", "body" };

            // Get Content Resolver object, which will deal with Content
            // Provider
            ContentResolver cr = getContentResolver();

            // Fetch Sent SMS Message from Built-in Content Provider
            Cursor c = cr.query(sentURI, reqCols, null, null, null);

            ArrayList<UserDetails> data = new ArrayList<UserDetails>();
//UserDetails data = null;
            while (c.moveToNext()){

                int index;

                index = c.getColumnIndexOrThrow("address");
                String address = c.getString(index);

                index = c.getColumnIndexOrThrow("body");
                String body = c.getString(index);

//                index = c.getColumnIndexOrThrow("id");
//                long id = c.getLong(index);
                if(body.trim().contains("Please send me directions from "))
                {
                    String tm = "2:30";
                    String longi = "2";
                    String lat = "2";
                    UserDetails temp = new UserDetails(lat,longi,tm,body,address);
                    data.add(temp);
                    if (ad!=null)
                    ad.notifyDataSetChanged();
                }

            }

            //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.activity_list_item,android.R.id.text1,data);
            // Attached Cursor with adapter and display in listview
            Adapter ad = new Adapter(getApplicationContext(),data);
            lvMsg.setAdapter(ad);



        }

        if (val == 3) {
            // Create Draft box URI
            Uri draftURI = Uri.parse("content://sms/draft");

            // List required columns
            String[] reqCols = new String[] { "_id", "address", "body" };

            // Get Content Resolver object, which will deal with Content
            // Provider
            ContentResolver cr = getContentResolver();

            // Fetch Sent SMS Message from Built-in Content Provider
            Cursor c = cr.query(draftURI, reqCols, null, null, null);

            ArrayList<UserDetails> data = new ArrayList<UserDetails>();
//UserDetails data = null;
            while (c.moveToNext()){

                int index;

                index = c.getColumnIndexOrThrow("address");
                String address = c.getString(index);

                index = c.getColumnIndexOrThrow("body");
                String body = c.getString(index);

//                index = c.getColumnIndexOrThrow("id");
//                long id = c.getLong(index);
                if(body.trim().contains("Please send me directions from "))
                {
                    String tm = "2:30";
                    String longi = "2";
                    String lat = "2";
                    UserDetails temp = new UserDetails(lat,longi,tm,body,address);
                    data.add(temp);
                    if(ad!=null)
                    ad.notifyDataSetChanged();
                }

            }

            //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.activity_list_item,android.R.id.text1,data);
            // Attached Cursor with adapter and display in listview
            ad = new Adapter(getApplicationContext(),data);
            lvMsg.setAdapter(ad);




        }

    }
}
