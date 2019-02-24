package com.example.zhihudaily.data;

/**
 * Created by Administrator on 2018/12/17 0017.
 */

public class OneZhihu {
    private String title;
    private String url;
    private String img;

    public OneZhihu(String url, String title, String img) {
        this.url = url;
        this.title = title;
        this.img = img;
    }
    public OneZhihu(){
        this.url = "";
        this.title = "";
        this.img = "";
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
