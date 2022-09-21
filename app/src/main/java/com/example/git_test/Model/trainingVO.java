package com.example.git_test.Model;

public class trainingVO {

    private String tv_training_t;
    private String tv_training_c;
    private int img_training;

    public trainingVO(String tv_training_t, String tv_training_c,int img_training) {
        this.tv_training_t = tv_training_t;
        this.tv_training_c = tv_training_c;
        this.img_training = img_training;
    }

    public String getTv_training_t() {
        return tv_training_t;
    }

    public void setTv_training_t(String tv_training_t) {
        this.tv_training_t = tv_training_t;
    }

    public String getTv_training_c() {
        return tv_training_c;
    }

    public void setTv_training_c(String tv_training_c) {
        this.tv_training_c = tv_training_c;
    }

    public int getImg_training() {
        return img_training;
    }

    public void setImg_training(int img_training) {
        this.img_training = img_training;
    }
}
