package com.example.zhihudaily.data;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.zhihudaily.detail.DetailContract;
import com.example.zhihudaily.main.MainContract;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class NetworkImageGetter implements Html.ImageGetter {

    private MainContract.View mView;
    private TextView mTextView;

    public NetworkImageGetter(MainContract.View view){
        mView = view;
    }
    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable = null;
        String netPicName = source.replace(".jpg", ".png").replace("/", "1");
        Log.d(TAG, "getDrawable: " + netPicName);

        File file = new File(Environment.getExternalStorageDirectory(), netPicName);
        Log.d(TAG, "getDrawable: " + Environment.getExternalStorageDirectory());
        if(file.exists()){
            drawable = Drawable.createFromPath(file.getAbsolutePath());
            drawable.setBounds(880, 0, 1080, 200);
        }else {
            mView.getNetworkImg(source, mTextView);
        }
        return drawable;
    }

    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }
}
