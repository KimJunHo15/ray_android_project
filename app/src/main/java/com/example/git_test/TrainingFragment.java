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
import com.example.git_test.Model.trainingAdapter;
import com.example.git_test.Model.trainingVO;

import java.util.ArrayList;


public class TrainingFragment extends Fragment {

    ArrayList<trainingVO>data = new ArrayList<trainingVO>();
    ListView lv_training;
    TextView tv_training_t, tv_training_c;
    ImageView img_training;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        lv_training = view.findViewById(R.id.lv_training);
        tv_training_t = view.findViewById(R.id.tv_training_t);
        tv_training_c = view.findViewById(R.id.tv_training_c);
        img_training = view.findViewById(R.id.img_training);

        data.add(new trainingVO("근력운동","무리한 자세를 취하지 않는것이 중요하다.",R.drawable.img1));
        data.add(new trainingVO("걷기","건강도 완화되어 활력이 증진된다.",R.drawable.img1));
        data.add(new trainingVO("유산소운동","뇌에 들어오는 혈류 또한 촉진하는데 도움이 된다.",R.drawable.img1));
        data.add(new trainingVO("손운동","집에서 간단하게 할 수 있는 것으로 잼잼 동작을 해보는 것이 좋다.",R.drawable.img1));

        trainingAdapter adapter = new trainingAdapter(getActivity().getApplicationContext(),R.layout.training_list,data);
        lv_training.setAdapter(adapter);

        return view;
    }
}