package com.example.git_test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.git_test.Model.columnAdapter;
import com.example.git_test.Model.columnVO;
import com.example.git_test.Model.foodAdapter;
import com.example.git_test.Model.foodVO;

import java.util.ArrayList;


public class FoodFragment extends Fragment {

    ArrayList<foodVO> data = new ArrayList<>();
    ListView lv_food;
    TextView tv_food_t, tv_food_c;
    ImageView img_food;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        lv_food = view.findViewById(R.id.lv_food);
        tv_food_t = view.findViewById(R.id.tv_food_t);
        tv_food_c = view.findViewById(R.id.tv_food_c);
        img_food = view.findViewById(R.id.img_food);

        data.add(new foodVO("강황","강력한 항염증 작용을 하는 강황은 치매에 좋은 음식이다.",R.drawable.imgfood1));
        data.add(new foodVO("호박","호박에 들어있는 식물영양소는 우리뇌를 보호하는 역할을 한다.",R.drawable.imgfood2));
        data.add(new foodVO("은행","잎에서 추출한 성분으로 치매증상 완화에 사용할 수 있다.",R.drawable.imgfood1));
        data.add(new foodVO("생선 오메가3","생선에서 추출된 것이 인지장애 예방과 치매예방에 좋다.",R.drawable.imgfood1));
        data.add(new foodVO("강황","강력한 항염증 작용을 하는 강황은 치매에 좋은 음식이다.",R.drawable.imgfood1));
        data.add(new foodVO("강황","강력한 항염증 작용을 하는 강황은 치매에 좋은 음식이다.",R.drawable.imgfood1));

        foodAdapter adapter = new foodAdapter(getActivity().getApplicationContext(),R.layout.food_list,data);
        lv_food.setAdapter(adapter);

        return view;
    }
}