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

public class RecyclerAdaper_training extends RecyclerView.Adapter<RecyclerAdaper_training.ItemViewHolder_training> {

    private ArrayList<trainingData> trainingData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerAdaper_training.ItemViewHolder_training onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_list, parent, false);

        return new RecyclerAdaper_training.ItemViewHolder_training(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaper_training.ItemViewHolder_training holder, int position) {
        holder.onBind(trainingData.get(position));
    }

    @Override
    public int getItemCount() {
        return trainingData.size();
    }

    public void training_additem(trainingData Data){
        trainingData.add(Data);
    }

class ItemViewHolder_training extends RecyclerView.ViewHolder{

    private TextView training_t;
    private TextView training_c;
    private ImageView training_url;

    public ItemViewHolder_training(@NonNull View itemView) {
        super(itemView);

        training_t = itemView.findViewById(R.id.tv_training_t);
        training_c = itemView.findViewById(R.id.tv_training_c);
        training_url = itemView.findViewById(R.id.img_training);
    }

    public void onBind(trainingData data) {
        training_t.setText(data.getTraining_t());
        training_c.setText(data.getTraining_c());
        Log.d("data.getImgurl()",data.getTraining_url());
        Glide.with(itemView.getContext()).load(data.getTraining_url()).into(training_url);
        //imgurl.setImageURI(data.getImgurl());
    }
}
}

