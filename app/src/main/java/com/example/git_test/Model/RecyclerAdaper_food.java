package com.example.git_test.Model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.git_test.R;

import java.util.ArrayList;

public class RecyclerAdaper_food extends RecyclerView.Adapter<RecyclerAdaper_food.ItemViewHolder_food> {

    private ArrayList<foodData> foodData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder_food onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list, parent, false);

        return new ItemViewHolder_food(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder_food holder, int position) {
        holder.onBind(foodData.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void food_additem(foodData Data){
        foodData.add(Data);
    }

    class ItemViewHolder_food extends RecyclerView.ViewHolder{

        private TextView food_t;
        private TextView food_c;
        private ImageView food_url;

        public ItemViewHolder_food(@NonNull View itemView) {
            super(itemView);

            food_t = itemView.findViewById(R.id.tv_food_t);
            food_c = itemView.findViewById(R.id.tv_food_c);
            food_url = itemView.findViewById(R.id.img_food);
        }

        public void onBind(foodData data) {
            food_t.setText(data.getFood_t());
            food_c.setText(data.getFood_c());
            Log.d("data.getImgurl()",data.getFood_url());
            Glide.with(itemView.getContext()).load(data.getFood_url()).into(food_url);
            //imgurl.setImageURI(data.getImgurl());
        }
    }
}
