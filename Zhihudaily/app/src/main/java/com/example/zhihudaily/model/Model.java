package com.example.zhihudaily.model;

import android.util.Log;

import com.example.zhihudaily.data.OneZhihu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/12/6 0006.
 */

public class Model implements IModel{

    private static final String zhihuUrl = "https://daily.zhihu.com";
    List<OneZhihu> list = new ArrayList<OneZhihu>();
    @Override
    public void getData(final loadDataCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.connect(zhihuUrl).get();
                    Elements links = doc.select("a.link-button");
                    for (Element e : links) {
                        String linkUrl = zhihuUrl + e.attr("href");
                        Element img = e.select("img").first();
                        String imgUrl = img.attr("src");
                        Element span = e.select("span").first();
                        String title = span.text();
                        Log.d(TAG, "run: "+ linkUrl);
                        Log.d(TAG, "run: "+ imgUrl);
                        Log.d(TAG, "run: "+ title);
                        OneZhihu oneZhihu = new OneZhihu(linkUrl, title, imgUrl);
                        list.add(oneZhihu);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    callback.returnData(list);
                }

            }
        }).start();
    }

    @Override
    public void getDetailData(loadDataCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public interface loadDataCallback{
        void returnData(List<OneZhihu> list);
        void returnDetailData(String data);
    }

}
