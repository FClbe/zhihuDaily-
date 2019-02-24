package com.example.zhihudaily.model;



/**
 * Created by Administrator on 2018/12/6 0006.
 */

public interface IModel {
    void getData(Model.loadDataCallback callback);
    void getDetailData(Model.loadDataCallback callback);
}
