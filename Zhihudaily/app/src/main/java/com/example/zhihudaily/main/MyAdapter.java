package com.example.zhihudaily.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhihudaily.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/17 0017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> list = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.oneList);
        }
    }

    public MyAdapter(List<String> list){
        this.list = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zhihu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
