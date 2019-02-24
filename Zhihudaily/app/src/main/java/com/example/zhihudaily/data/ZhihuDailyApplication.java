package com.example.zhihudaily.data;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class ZhihuDailyApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
