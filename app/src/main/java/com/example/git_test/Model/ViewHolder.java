package com.example.git_test.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.git_test.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgurl;
    public TextView score;
    public TextView date;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        imgurl = itemView.findViewById(R.id.imgurl);
        score = itemView.findViewById(R.id.score);
        date = itemView.findViewById(R.id.date);
    }
}
