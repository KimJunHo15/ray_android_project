package com.example.git_test.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.git_test.R;

public class ViewHolder_food extends RecyclerView.ViewHolder {

    public TextView food_t;
    public TextView food_c;
    public ImageView food_url;

    public ViewHolder_food(Context context, View itemView) {
        super(itemView);
        food_t = itemView.findViewById(R.id.tv_food_t);
        food_c = itemView.findViewById(R.id.tv_food_c);
        food_url = itemView.findViewById(R.id.img_food);
    }
}
