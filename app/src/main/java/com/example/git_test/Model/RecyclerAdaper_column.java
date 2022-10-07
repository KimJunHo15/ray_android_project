package com.example.git_test.Model;

import android.text.method.ScrollingMovementMethod;
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

public class RecyclerAdaper_column extends RecyclerView.Adapter<RecyclerAdaper_column.ItemViewHolder_column>{
    private ArrayList<columnData> columnData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerAdaper_column.ItemViewHolder_column onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_list, parent, false);

        return new RecyclerAdaper_column.ItemViewHolder_column(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaper_column.ItemViewHolder_column holder, int position) {
        holder.onBind(columnData.get(position));
    }

    @Override
    public int getItemCount() {
        return columnData.size();
    }

    public void column_additem(columnData Data){
        columnData.add(Data);
    }

    class ItemViewHolder_column extends RecyclerView.ViewHolder{

        private TextView column_t;
        private TextView column_c;
        private ImageView column_url;

        public ItemViewHolder_column(@NonNull View itemView) {
            super(itemView);

            column_t = itemView.findViewById(R.id.tv_column_t);
            column_c = itemView.findViewById(R.id.tv_column_c);
            column_c.setMovementMethod(new ScrollingMovementMethod());
            column_url = itemView.findViewById(R.id.img_column);
        }

        public void onBind(columnData data) {
            column_t.setText(data.getColumn_t());
            column_c.setText(data.getColumn_c());
            Log.d("data.getImgurl()",data.getColumn_url());
            Glide.with(itemView.getContext()).load(data.getColumn_url()).into(column_url);
            //imgurl.setImageURI(data.getImgurl());
        }
    }
}
