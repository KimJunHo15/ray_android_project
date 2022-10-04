package com.example.git_test;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.git_test.Model.RecyclerAdaper_column;
import com.example.git_test.Model.RecyclerAdaper_training;
import com.example.git_test.Model.columnAdapter;
import com.example.git_test.Model.columnData;
import com.example.git_test.Model.columnVO;
import com.example.git_test.Model.trainingData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ColumnFragment extends Fragment {

    ArrayList<columnVO> data = new ArrayList<>();
    RecyclerView column_rv;
    TextView tv_column_t, tv_column_c;
    ImageView img_column;
    Context context;

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
        init();

        String url = "http://10.0.2.2:8000/info/column";

        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<org.json.JSONArray>() {
            @Override
            public void onResponse(org.json.JSONArray response) {
                for(int i =0; i<response.length();i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        String column_title = jsonObject.getString("column_title");
                        String column_content = jsonObject.getString("column_content");
                        String column_img = jsonObject.getString("column_img");
                        column_img = "http://10.0.2.2:8000"+column_img;
                        getData(column_title, column_content, column_img);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_response",error+"");
                Toast.makeText(getContext().getApplicationContext(), error+"", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(JsonArrayRequest);
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

            adapter_column.column_additem(data);
        }
        adapter_column.notifyDataSetChanged();
    }
}