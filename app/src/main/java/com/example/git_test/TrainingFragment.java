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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.git_test.Model.RecyclerAdaper_training;
import com.example.git_test.Model.trainingData;
import com.example.git_test.Model.trainingVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TrainingFragment extends Fragment {

    ArrayList<trainingVO> data = new ArrayList<>();
    RecyclerView training_rv;
    TextView tv_training_t, tv_training_c;
    ImageView img_training;

    RequestQueue requestQueue;

    private RecyclerAdaper_training adapter_training;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);
        training_rv = (RecyclerView) view.findViewById(R.id.training_rv);
        tv_training_t = view.findViewById(R.id.tv_training_t);
        tv_training_c = view.findViewById(R.id.tv_training_c);
        img_training = view.findViewById(R.id.img_training);


        init();
//        String url = "http://10.0.2.2:8000/info/train";
        String url = "http://172.30.1.28:8000/info/train";

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
                        String training_t = jsonObject.getString("train_title");
                        String training_c = jsonObject.getString("train_info");
                        String training_url = jsonObject.getString("train_img");
//                        training_url = "http://10.0.2.2:8000"+training_url;
                        training_url = "http://172.30.1.28:8000"+training_url;
                        getData(training_t, training_c, training_url);
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

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        training_rv.setLayoutManager(linearLayoutManager);

        adapter_training = new RecyclerAdaper_training();
        training_rv.setAdapter(adapter_training);
    }

    private void getData(String training_t, String training_c, String training_url) {
        List<String> listTraining_t = Arrays.asList(training_t);
        List<String> listTraining_c = Arrays.asList(training_c);
        List<String> listTraining_url = Arrays.asList(training_url);

        for (int i = 0; i < listTraining_t.size(); i++) {
            trainingData data = new trainingData();
            data.setTraining_t(listTraining_t.get(i));
            data.setTraining_c(listTraining_c.get(i));
            data.setTraining_url(listTraining_url.get(i));

            adapter_training.training_additem(data);
        }
        adapter_training.notifyDataSetChanged();
    }
}