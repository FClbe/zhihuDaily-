package com.example.zhihudaily.main;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.example.zhihudaily.BasePresenter;
import com.example.zhihudaily.BaseView;
import com.example.zhihudaily.data.OneZhihu;

import java.util.List;

/**
 * Created by Administrator on 2018/12/6 0006.
 */

public interface MainContract {

    interface View extends BaseView<Presenter>{
        void openTaskForDetail(String url);
        void setData(List<OneZhihu> list);
        void getNetworkImg(String url, TextView textView);
        void saveMyBitmap(String url, Bitmap bitmap);
    }

    interface Presenter extends BasePresenter{
        void showDetail(String url);
        void loadData();
    }
}
