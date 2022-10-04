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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.git_test.Model.Data;
import com.example.git_test.Model.RecyclerAdaper;
import com.example.git_test.Model.RecyclerAdaper_food;
import com.example.git_test.Model.RecyclerAdaper_training;
import com.example.git_test.Model.columnAdapter;
import com.example.git_test.Model.columnVO;
import com.example.git_test.Model.trainingAdapter;
import com.example.git_test.Model.trainingData;
import com.example.git_test.Model.trainingVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TrainingFragment extends Fragment {

    ArrayList<trainingVO>data = new ArrayList<>();
    //    ArrayList<trainingVO>data = new ArrayList<trainingVO>();
    RecyclerView training_rv;
    TextView tv_training_t, tv_training_c;
    ImageView img_training;
    Context context;

    RequestQueue requestQueue;
    StringRequest request;

    JsonObjectRequest jsonObjectRequest;

    private RecyclerAdaper_training adapter_training;
    trainingData trainingData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        training_rv = view.findViewById(R.id.training_rv);
        tv_training_t = view.findViewById(R.id.tv_training_t);
        tv_training_c = view.findViewById(R.id.tv_training_c);
        img_training = view.findViewById(R.id.img_training);

        String url = "http://10.0.2.2:8000/info/train";

        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        init();

//        data.add(new trainingVO("근력운동","무리한 자세를 취하지 않는것이 중요하다.",R.drawable.img1));
//        data.add(new trainingVO("걷기","건강도 완화되어 활력이 증진된다.",R.drawable.img1));
//        data.add(new trainingVO("유산소운동","뇌에 들어오는 혈류 또한 촉진하는데 도움이 된다.",R.drawable.img1));
//        data.add(new trainingVO("손운동","집에서 간단하게 할 수 있는 것으로 잼잼 동작을 해보는 것이 좋다.",R.drawable.img1));

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            System.out.println("aa");
                            // 대괄호 담아둘수 있게하기
                            // 대괄호 안에 중괄호 데이터들을 읽어보기


                            Log.d("check", response.toString());

                            JSONObject json = new JSONObject(response.toString());
                          //  JSONArray json2 = json.getJSONArray("train_title");

                           // Object data = json.getJSONObject("train_title");

//                            for(int i = 0; i < json2.length(); i++){
//                                JSONObject jsonObject = json2.getJSONObject(i);
//                                Log.d("data", jsonObject+"");
//
//                            }


                            Log.d("data", json+"");


                            String training_t = json.getString("train_title");
                            String training_c = json.getString("train_info");
                            String training_img = json.getString("train_img");
                            getData(training_t,training_c,training_img);



                        }catch (Exception e){
                            Toast.makeText(getContext().getApplicationContext(), "오류 : "+e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("오류", "onErrorResponse: "+ e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error이거",error+"");
                        Toast.makeText(getContext().getApplicationContext(), error+"", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        return view;
    }
    private void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        training_rv.setLayoutManager(linearLayoutManager);

        adapter_training = new RecyclerAdaper_training();
        training_rv.setAdapter(adapter_training);
    }
    private void getData(String training_t,String training_c, String training_img){
        List<String> listtraining_t = Arrays.asList(training_t);
        List<String> listtraining_c = Arrays.asList(training_c);
        List<String> listtraining_img = Arrays.asList(training_img);

        for(int i =0; i<listtraining_t.size();i++){
            trainingData data = new trainingData();
            data.setTraining_t(listtraining_t.get(i));
            data.setTraining_c(listtraining_c.get(i));
            data.setTraining_url(listtraining_img.get(i));

            adapter_training.training_additem(trainingData);
        }
        adapter_training.notifyDataSetChanged();
    }
}