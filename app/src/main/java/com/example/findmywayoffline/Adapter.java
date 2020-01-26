package com.example.findmywayoffline;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    private Context mContext;
    private List<UserDetails> data;

    public Adapter(Context mContext, List<UserDetails> data) {
        this.mContext = mContext;
        this.data = data;
        Log.d("THOH",String.valueOf(this.data.size()));
    }

    @Override
    public Adapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter.myViewHolder myViewHolder, int i) {

        final UserDetails detail =data.get(i);
        // myViewHolder.publishedAt.setText(videoList.get(i).getPublishedAt());
        myViewHolder.body.setText(data.get(i).getBody());
//        myViewHolder.description.setText(videoList.get(i).getDescription());
        myViewHolder.phonum.setText(data.get(i).getNumber());
        myViewHolder.timest.setText(data.get(i).getTimest());


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, HelpDetail.class);
               i.putExtra("Phone", detail.getNumber());
               i.putExtra("Body",detail.getBody());
               i.putExtra("Lat",detail.getLat());
               i.putExtra("Longi",detail.getLongi());
               i.putExtra("TS",detail.getTimest());

                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        public TextView body, phonum, timest;

        public myViewHolder( View itemView) {
            super(itemView);

            body = (TextView) itemView.findViewById(R.id.lblMsg);
            phonum = (TextView) itemView.findViewById(R.id.lblNumber);
            timest = (TextView) itemView.findViewById(R.id.time);
        }
    }

}