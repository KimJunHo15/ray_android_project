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

public class RecyclerAdaper extends RecyclerView.Adapter<RecyclerAdaper.ItemViewHolder> {

    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void additem(Data data){
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView score;
        private TextView date;
        private ImageView imgurl;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            score = itemView.findViewById(R.id.score);
            date = itemView.findViewById(R.id.date);
            imgurl = itemView.findViewById(R.id.imgurl);
        }

        public void onBind(Data data) {
            score.setText(data.getScore());
            date.setText(data.getDate());
            Log.d("data.getImgurl()",data.getImgurl());
            Glide.with(itemView.getContext()).load(data.getImgurl()).into(imgurl);
            //imgurl.setImageURI(data.getImgurl());
        }
    }
}
