package com.example.git_test.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.git_test.R;

import java.util.ArrayList;

public class trainingAdapter extends BaseAdapter {

    Context context;
    Context context2;
    int training_list;
    ArrayList<trainingVO> data;
    LayoutInflater inflater;

    public trainingAdapter(Context context,int training_list, ArrayList<trainingVO> data) {
        this.context = context;
        this.training_list = training_list;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=inflater.inflate(training_list,viewGroup,false);
        }
        TextView tv_training_t =  view.findViewById(R.id.tv_training_t);
        tv_training_t.setText(data.get(i).getTv_training_t());
        TextView tv_training_c = view.findViewById(R.id.tv_training_c);
        tv_training_c.setText(data.get(i).getTv_training_c());
        ImageView img_training = view.findViewById(R.id.img_training);
        img_training.setImageResource(data.get(i).getImg_training());

        return view;
    }


}