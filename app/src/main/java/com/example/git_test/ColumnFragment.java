package com.example.git_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.git_test.Model.columnAdapter;
import com.example.git_test.Model.columnVO;

import java.util.ArrayList;


public class ColumnFragment extends Fragment {

    ArrayList<columnVO> data = new ArrayList<>();
    RecyclerView column_rv;
    TextView tv_column_t, tv_column_c;
    ImageView img_column;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_column, container, false);

        column_rv = view.findViewById(R.id.column_rv);
        tv_column_c = view.findViewById(R.id.tv_column_c);
        tv_column_t = view.findViewById(R.id.tv_column_t);
        img_column = view.findViewById(R.id.img_column);

        data.add(new columnVO("테스트","테스트",R.drawable.big_logo));
        data.add(new columnVO("테스트1","테스트1",R.drawable.img1));
        data.add(new columnVO("테스트2","테스트2",R.drawable.img2));
        data.add(new columnVO("테스트3","테스트3",R.drawable.img3));
        data.add(new columnVO("테스트4","테스트4",R.drawable.img4));

//        columnAdapter adapter = new columnAdapter(getActivity().getApplicationContext(), R.layout.column_list,data);
//        column_rv.setAdapter(adapter);

        return view;
    }
}