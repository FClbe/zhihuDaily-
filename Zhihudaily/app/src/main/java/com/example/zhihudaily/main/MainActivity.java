package com.example.zhihudaily.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.zhihudaily.R;
import com.example.zhihudaily.data.OneZhihu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private RecyclerView recyclerView;
    private MainAdapter mAdapter;
    private List<OneZhihu> mList = new ArrayList<>();
    private MainPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        openThePermission();

        init();
        mPresenter = new MainPresenter(MainActivity.this);
        mPresenter.loadData();

    }

    private void init(){
        recyclerView = (RecyclerView)findViewById(R.id.zhihu_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter(mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void showAdapter(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void openTaskForDetail(String url) {

    }

    @Override
    public void setData(List<OneZhihu> list) {
        mList.addAll(list);
        Log.d("data", mList.toString());
        showAdapter();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void getNetworkImg(String url, TextView textView) {

    }

    @Override
    public void saveMyBitmap(String url, Bitmap bitmap) {

    }

    private void openThePermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }
}
