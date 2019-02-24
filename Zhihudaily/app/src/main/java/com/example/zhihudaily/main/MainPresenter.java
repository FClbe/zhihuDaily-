package com.example.zhihudaily.main;

import com.example.zhihudaily.data.OneZhihu;
import com.example.zhihudaily.model.Model;

import java.util.List;

/**
 * Created by Administrator on 2018/12/6 0006.
 */

public class MainPresenter implements MainContract.Presenter, Model.loadDataCallback {

    private MainContract.View mView;
    private Model mModel;

    public MainPresenter(MainContract.View view)
    {
        mView = view;
        mModel = new Model();
    }

    @Override
    public void showDetail(String url) {
        mView.openTaskForDetail(url);
    }

    @Override
    public void loadData() {
        mModel.getData(MainPresenter.this);
    }

    @Override
    public void returnData(List<OneZhihu> list) {
        mView.setData(list);
    }

    @Override
    public void returnDetailData(String data) {

    }

    @Override
    public void start() {}
}
