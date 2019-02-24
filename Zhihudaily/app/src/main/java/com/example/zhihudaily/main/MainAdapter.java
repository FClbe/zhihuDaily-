package com.example.zhihudaily.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.zhihudaily.R;
import com.example.zhihudaily.data.NetworkImageGetter;
import com.example.zhihudaily.data.OneZhihu;
import com.example.zhihudaily.data.ZhihuDailyApplication;
import com.example.zhihudaily.detail.DetailActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/12/6 0006.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements MainContract.View{

    private MainPresenter mPresenter  = new MainPresenter(MainAdapter.this);
    private List<OneZhihu> mList = new ArrayList<>();
    NetworkImageGetter mImageGetter = new NetworkImageGetter(this);

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView oneList;
        View oneListView;
        public ViewHolder(View view) {
            super(view);
            oneList = (TextView) view.findViewById(R.id.oneList);
            oneListView = view;
        }
    }

    public MainAdapter(List<OneZhihu> list) {
        mList = list;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final OneZhihu oneZhihu = mList.get(position);
        mImageGetter.setmTextView(holder.oneList);
        //final String ImgUrl = "<img src = '" + oneZhihu.getImg() + "'>";
        final String ImgUrl = oneZhihu.getImg();
        Drawable drawable = mImageGetter.getDrawable(ImgUrl);
        holder.oneList.setText(oneZhihu.getTitle());
        holder.oneList.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        //holder.oneList.setText(Html.fromHtml(ImgUrl, mImageGetter, null));
        holder.oneListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showDetail(oneZhihu.getUrl());
            }
        });

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
        if(mList.size() > 0)
            return mList.size();
        else
            return 0;
    }

    @Override
    public void openTaskForDetail(String url) {
        Intent intent = new Intent(ZhihuDailyApplication.getContext(), DetailActivity.class);
        intent.putExtra("linkUrl", url);
        ZhihuDailyApplication.getContext().startActivity(intent);
    }

    @Override
    public void setData(List<OneZhihu> list) {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void getNetworkImg(final String url, final TextView mTextView) {
        Log.d(TAG, "url:" + url);
        final String netPicName = url.replace(".jpg", ".png").replace("/", "1");
        RequestQueue queue = Volley.newRequestQueue(ZhihuDailyApplication.getContext());
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                saveMyBitmap(netPicName, bitmap);
                File file1 = new File(Environment.getExternalStorageDirectory(), netPicName);
                Drawable drawable = Drawable.createFromPath(file1.getAbsolutePath());
                drawable.setBounds(880, 0, 1080, 200);
                mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onError:" + error);
            }
        });
        queue.add(request);
    }

    @Override
    public void saveMyBitmap(String bitName, Bitmap bitmap) {
        File file = new File("/sdcard/" + bitName);
        Log.d(TAG, "saveMyBitmap: " + file);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Log.d(TAG, "saveMyBitmap: " + file);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            fOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

