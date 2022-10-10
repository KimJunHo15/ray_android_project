package com.example.git_test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class MainFragment extends Fragment {

//    public static MainFragment newInstance(){
//        return new MainFragment();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "cc4OJzq0XUM";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        ImageView imgc,imgf,imgt;
        imgc = view.findViewById(R.id.imgc);
        imgf = view.findViewById(R.id.imgf);
        imgt = view.findViewById(R.id.imgt);

        imgc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((InfoActivity)getActivity()).replaceFragment(ColumnFragment.newInstance());
            }
        });

        imgf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((InfoActivity)getActivity()).replaceFragment(ColumnFragment.newInstance());
            }
        });

        imgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        return view;
    }
}