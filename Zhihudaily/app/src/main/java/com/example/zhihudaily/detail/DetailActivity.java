package com.example.zhihudaily.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.zhihudaily.R;
import com.example.zhihudaily.main.MainAdapter;
import com.example.zhihudaily.main.MainContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity  {
    private ArrayList<String> errorUrl = new ArrayList<>();
    private int number;
    private int count;
    private Toolbar toolbar;
    private String urlFromMain;
    private TextView mTextView;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private NetWorkImageGetter netWorkImageGetter = new NetWorkImageGetter();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(DetailActivity.this, "success", Toast.LENGTH_SHORT);
                    Log.d("data", "handleMessage: " + mTextView.getText());
                    addIntoTextview(urlFromMain);
                    //String nice = textView.getText().toString();
                    //String veryNice = nice.replaceAll("!!!", "\n");
                    //textView.setText(veryNice);
                    //textView.setText(Html.fromHtml(veryNice, netWorkImageGetter, null));

                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTextView = findViewById(R.id.detail_content);
        toolbar = findViewById(R.id.detail_toolbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        imageView = findViewById(R.id.zhihu_image_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        urlFromMain = intent.getStringExtra("linkUrl");
        Log.d(TAG, "onCreate: " + urlFromMain);
        getTheWeb(urlFromMain);

    }
    private void getTheWeb(final String urlFromMain){
        new Thread(){
            public void run(){
                super.run();
                number = count = 0;
                try {
                    Document doc = Jsoup.connect(urlFromMain).get();
                    Element element = doc.select("div.content").first();
                    Element firstHeadTitle = doc.select("div.img-wrap").first();
                    String headPicBeforeCut = firstHeadTitle.select("img").first().toString();
                    String headPic =
                            headPicBeforeCut.substring(headPicBeforeCut.indexOf("http"), headPicBeforeCut.indexOf(".jpg") + 4);
                    number++;
                    getPictures(headPic);
                    String authorPicBeforeCut = doc.select("img.avatar").first().toString();
                    String authorPic =
                            authorPicBeforeCut.substring(authorPicBeforeCut.indexOf("http"), authorPicBeforeCut.indexOf(".jpg") + 4);
                    number++;
                    getPictures(authorPic);
                    //Log.d("data", "run: sad" + doc.toString());
                    Elements p = element.select("img.content-image");
                    for (Element e:p) {
                        String beforeCut = e.toString();
                        Log.d("beforeCut", "run: " + beforeCut);
                        String url = beforeCut.substring(beforeCut.indexOf("http"), beforeCut.indexOf(".jpg") + 4);
                        Log.d("data", "run: " + url);
                        //String content = "<img src='" + url + "'>";
                        number++;
                        getPictures(url);

                    }


                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }.start();
    }

    private void addIntoTextview(final String urlFromMain){
        new Thread(){
            public void run(){
                super.run();
                try {
                    Document doc = Jsoup.connect(urlFromMain).get();
                    String title1 = doc.select("h1.headline-title").first().text();
                    Log.d(TAG, "addIntoTextview: " + title1);
                    addContent(title1, 6);
                    //textView.append(  title1 + "\n");
                    String title2 = doc.select("h2.question-title").first().text();
                    Log.d(TAG, "addIntoTextview: " + title2);
                    addContent(title2, 4);addContent(title2, 5);addContent(title2, 5);
                    Element firstHeadTitle = doc.select("div.img-wrap").first();
                    String headPicBeforeCut = firstHeadTitle.select("img").first().toString();
                    String headPic =
                            headPicBeforeCut.substring(headPicBeforeCut.indexOf("http"), headPicBeforeCut.indexOf(".jpg") + 4);
                    addContent(headPic, 7);
                    String authorPicBeforeCut = doc.select("img.avatar").first().toString();
                    String authorPic = "<img src='" +
                            authorPicBeforeCut.substring(authorPicBeforeCut.indexOf("http"), authorPicBeforeCut.indexOf(".jpg") + 4)
                            + "'>";
                    Log.d(TAG, "addIntoTextview: " + authorPic);
                    addContent(authorPic, 3);
                    String author = doc.select("span.author").first().text();
                    Log.d(TAG, "addIntoTextview: " + author);
                    addContent(author, 4);
                    if(doc.select("span.bio").first() != null){
                        String authorBio = doc.select("span.bio").first().text();
                        Log.d(TAG, "addIntoTextview: " + authorBio);
                        addContent(authorBio, 4);
                    }
                    addContent(author, 5);addContent(author, 5);
                    Element element = doc.select("div.content").first();
                    //Log.d("data", "run: sad" + doc.toString());
                    Elements p = element.select("p");
                    for (Element e:p) {
                        if(e.select("img").first() == null){
                            Log.d("data", "run: " + e.text());
                            addContent(e.text(), 1);
                        }
                        else{
                            String beforeCut = e.toString();
                            String url = beforeCut.substring(beforeCut.indexOf("http"), beforeCut.indexOf(".jpg") + 4);
                            Log.d("data", "run: " + url);
                            String content = "<img src='" + url + "'>";
                            addContent(content, 2);
                        }

                    }


                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }.start();
    }

    private void getPictures(final String url){
        final String name = url.replace('/', '1')
                .replaceAll(".jpg", ".png");
        Log.d("data", "getPictures: " + name);
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap bitmap) {
                count++;
                Log.d("data", "onResponse: " + count + "" + number);
                saveMyBitmap(name, bitmap);
                if(count == number){
                    Log.d("data", "onResponse: download successfully");
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                count++;
                errorUrl.add("<img src='" + url + "'>");
                if(count == number){
                    Log.d("data", "onResponse: download successfully");
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                Log.d("data", "onErrorResponse: " + url);
            }
        });
        queue.add(request);

    }

    private void saveMyBitmap(String name, Bitmap bitmap){
        File f = new File("/sdcard//"+ name);
        try{
            f.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try{
            fOut = new FileOutputStream(f);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try{
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

    private void addContent(final String content, final int temp){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (temp == 1)
                    mTextView.append("         " + content + "\n");
                else if(temp == 2){
                    if(checkErrorPic(content)){
                        mTextView.append(Html.fromHtml(content, netWorkImageGetter, null));
                        mTextView.append("\n");
                    }
                }
                else if(temp == 3){
                    if (checkErrorPic(content)){
                        mTextView.append(Html.fromHtml(content, new Html.ImageGetter() {
                            @Override
                            public Drawable getDrawable(String source) {
                                Drawable drawable = null;
                                String picName = source.replace('/', '1')
                                        .replaceAll(".jpg", ".png");
                                File file = new File(Environment.getExternalStorageDirectory(), picName);
                                drawable = Drawable.createFromPath(file.getAbsolutePath());
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 12
                                        , drawable.getIntrinsicHeight() * 12);
                                return drawable;
                            }
                        }, null));
                    }

                }
                else if(temp == 4){
                    mTextView.append(content);
                }
                else if(temp == 5){
                    mTextView.append("\n");
                }
                else if(temp == 6){
                    collapsingToolbar.setTitle(content);
                }
                else{
                    String PicName = content.replace('/', '1')
                            .replaceAll(".jpg", ".png");
                    File f = new File(Environment.getExternalStorageDirectory(),PicName);
                    Glide.with(DetailActivity.this).load(f.getAbsolutePath()).into(imageView);
                }

            }
        });
    }

    private boolean checkErrorPic(String pic){
        for (int i = 0; i < errorUrl.size(); i++) {
            if(pic.equals(errorUrl.get(i))){
                return false;
            }

        }
        return true;
    }

    class NetWorkImageGetter implements Html.ImageGetter{
        @Override
        public Drawable getDrawable(String source){
            Drawable drawable = null;
            String picName = source.replace('/', '1')
                    .replaceAll(".jpg", ".png");
            File file = new File(Environment.getExternalStorageDirectory(), picName);
            drawable = Drawable.createFromPath(file.getAbsolutePath());
            //int left = 720 - drawable.getIntrinsicWidth() * 2;
            //int right = 720 + drawable.getIntrinsicWidth() * 2;
            int height = drawable.getIntrinsicHeight() * (1440 / drawable.getIntrinsicWidth());
            drawable.setBounds(0, 0, 1440
                    , height);
            return drawable;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

