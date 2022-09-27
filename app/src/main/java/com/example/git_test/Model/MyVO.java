package com.example.git_test.Model;

import android.net.Uri;

public class MyVO {

    private String imgurl;
    private String date;
    private String score;

    public MyVO(String imgurl, String date, String score) {
        this.imgurl = imgurl;
        this.date = date;
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
