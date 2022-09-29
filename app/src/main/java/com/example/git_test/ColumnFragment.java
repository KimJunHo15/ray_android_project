package com.example.git_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.git_test.Model.RecyclerAdaper_column;
import com.example.git_test.Model.RecyclerAdaper_training;
import com.example.git_test.Model.columnAdapter;
import com.example.git_test.Model.columnData;
import com.example.git_test.Model.columnVO;
import com.example.git_test.Model.trainingData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ColumnFragment extends Fragment {

    ArrayList<columnVO> data = new ArrayList<>();
    RecyclerView column_rv;
    TextView tv_column_t, tv_column_c;
    ImageView img_column;

    RequestQueue requestQueue;
    StringRequest request;
    private RecyclerAdaper_column adapter_column;
    columnData columnData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_column, container, false);

        column_rv = view.findViewById(R.id.column_rv);
        tv_column_c = view.findViewById(R.id.tv_column_c);
        tv_column_t = view.findViewById(R.id.tv_column_t);
        img_column = view.findViewById(R.id.img_column);

        String url = "http://10.0.2.2:8000/mobile/showmember";

        // init();

//        data.add(new columnVO("테스트","테스트",R.drawable.big_logo));
//        data.add(new columnVO("테스트1","테스트1",R.drawable.img1));
//        data.add(new columnVO("테스트2","테스트2",R.drawable.img2));
//        data.add(new columnVO("테스트3","테스트3",R.drawable.img3));
//        data.add(new columnVO("테스트4","테스트4",R.drawable.img4));

        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject json = new JSONObject(response);

                            String column_t = json.getString("column_t");
                            String column_c = json.getString("column_c");
                            String column_img = json.getString("column_img");
                            getData(column_t,column_c,column_img);

                        }catch (Exception e){
                            Toast.makeText(getContext().getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext().getApplicationContext(), error+"", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);
//        columnAdapter adapter = new columnAdapter(getActivity().getApplicationContext(), R.layout.column_list,data);
//        column_rv.setAdapter(adapter);

        return view;
    }
    private void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        column_rv.setLayoutManager(linearLayoutManager);

        adapter_column = new RecyclerAdaper_column();
        column_rv.setAdapter(adapter_column);
    }
    private void getData(String column_t,String column_c, String column_img){
        List<String> listcolumn_t = Arrays.asList(column_t);
        List<String> listcolumn_c = Arrays.asList(column_c);
        List<String> listcolumn_img = Arrays.asList(column_img);

        for(int i =0; i<listcolumn_t.size();i++){
            columnData data = new columnData();
            data.setColumn_t(listcolumn_t.get(i));
            data.setColumn_c(listcolumn_c.get(i));
            data.setColumn_url(listcolumn_img.get(i));

            adapter_column.column_additem(columnData);
        }
        adapter_column.notifyDataSetChanged();
    }
}