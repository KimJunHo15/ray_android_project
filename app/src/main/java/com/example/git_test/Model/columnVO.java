package com.example.git_test.Model;

public class columnVO {

    private String tv_column_t;
    private String tv_column_c;
    private int img_column;

    public columnVO(String tv_column_t, String tv_column_c, int img_column) {
        this.tv_column_t = tv_column_t;
        this.tv_column_c = tv_column_c;
        this.img_column = img_column;
    }

    public String getTv_column_t() {
        return tv_column_t;
    }

    public void setTv_column_t(String tv_column_t) {
        this.tv_column_t = tv_column_t;
    }

    public String getTv_column_c() {
        return tv_column_c;
    }

    public void setTv_column_c(String tv_column_c) {
        this.tv_column_c = tv_column_c;
    }

    public int getImg_column() {
        return img_column;
    }

    public void setImg_column(int img_column) {
        this.img_column = img_column;
    }
}
