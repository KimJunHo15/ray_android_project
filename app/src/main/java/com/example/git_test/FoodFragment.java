package com.example.git_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.git_test.Model.Data;
import com.example.git_test.Model.RecyclerAdaper_food;
import com.example.git_test.Model.foodData;
import com.example.git_test.Model.foodVO;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FoodFragment extends Fragment {

    ArrayList<foodVO> data = new ArrayList<>();
    RecyclerView food_rv;
    TextView tv_food_t, tv_food_c;
    ImageView img_food;

    RequestQueue requestQueue;
    StringRequest request;
    private RecyclerAdaper_food adapter_food;
    foodData foodData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        food_rv =view.findViewById(R.id.food_rv);
        tv_food_t = view.findViewById(R.id.tv_food_t);
        tv_food_c = view.findViewById(R.id.tv_food_c);
        img_food = view.findViewById(R.id.img_food);

        String url = "http://10.0.2.2:8000/info/food";

        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        init();

        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);

                            String food_t = json.getString("food_t");
                            String food_c = json.getString("food_info");
                            String food_img = json.getString("food_img");
                            getData_food(food_t,food_c,food_img);
                            
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

        return view;
    }
    private void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        food_rv.setLayoutManager(linearLayoutManager);

        adapter_food= new RecyclerAdaper_food();
        food_rv.setAdapter(adapter_food);
    }
    private void getData_food(String food_t,String food_c, String food_img){
        List<String> listfood_t = Arrays.asList(food_t);
        List<String> listfood_c = Arrays.asList(food_c);
        List<String> listfood_img = Arrays.asList(food_img);

        for(int i =0; i<listfood_t.size();i++){
            foodData foodData = new foodData();
            foodData.setFood_t(listfood_t.get(i));
            foodData.setFood_c(listfood_c.get(i));
            foodData.setFood_url(listfood_img.get(i));

            adapter_food.food_additem(foodData);
        }
        adapter_food.notifyDataSetChanged();
    }
}