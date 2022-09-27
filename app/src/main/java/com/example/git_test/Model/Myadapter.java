//package com.example.git_test.Model;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.git_test.R;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//
//import retrofit2.http.Url;
//
//public class Myadapter extends RecyclerView.Adapter<ViewHolder> {
//
//    private ArrayList<MyVO> data = new ArrayList<>();
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //Context context = parent.getContext();
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list, parent, false);
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String text = arrayList.get(position);
//        holder.imgurl.
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
