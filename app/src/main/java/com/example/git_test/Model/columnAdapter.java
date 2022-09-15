package com.example.git_test.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.git_test.R;

import java.util.ArrayList;

public class columnAdapter extends BaseAdapter {

    Context context;
    Context context2;
    int column_list;
    ArrayList<columnVO> data;
    LayoutInflater inflater;

        public columnAdapter(Context context, int column_list, ArrayList<columnVO> data){
        this.context = context;
        this.column_list = column_list;
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
            view=inflater.inflate(column_list,viewGroup,false);
        }
        TextView tv_column_t =  view.findViewById(R.id.tv_column_t);
        tv_column_t.setText(data.get(i).getTv_column_t());
        TextView tv_column_c = view.findViewById(R.id.tv_column_c);
        tv_column_c.setText(data.get(i).getTv_column_c());
        ImageView img_column = view.findViewById(R.id.img_column);

        return view;
    }






}
