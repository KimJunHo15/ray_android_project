package com.example.git_test.Model;

public class foodVO {

    private String tv_food_t;
    private String tv_food_c;
    private int img_food;

    public foodVO(String tv_food_t, String tv_food_c,int img_food) {
        this.tv_food_t = tv_food_t;
        this.tv_food_c = tv_food_c;
        this.img_food = img_food;
    }

    public String getTv_food_t() {
        return tv_food_t;
    }

    public void setTv_food_t(String tv_food_t) {
        this.tv_food_t = tv_food_t;
    }

    public String getTv_food_c() {
        return tv_food_c;
    }

    public void setTv_food_c(String tv_food_c) {
        this.tv_food_c = tv_food_c;
    }

    public int getImg_food() {
        return img_food;
    }

    public void setImg_food(int img_food) {
        this.img_food = img_food;
    }
}




