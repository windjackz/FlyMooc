package com.recker.flymooc.datas;

/**
 * Created by recker on 16/5/23.
 */
public class BaseData {

    private int img;

    private String title;

    public BaseData(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
