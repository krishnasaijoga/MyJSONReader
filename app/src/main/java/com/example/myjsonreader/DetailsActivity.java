package com.example.myjsonreader;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


// TODO : recognize the list element which has been clicked and in new activity display the details corresponding to list element
public class DetailsActivity extends AppCompatActivity {

    Context context;
    ImageView iv_disp_img;
    TextView tv_html_url,tv_gists_url,tv_repos_url,tv_events_url,tv_login_name;
    int position;
    String text;
    GitUserExtraDetails feed;
    RecyclerView mRv;
    //public static List<GitUserExtraDetails> feedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = this.getApplicationContext();

        mRv = MainActivity.rv;
        iv_disp_img = findViewById(R.id.disp_img_iv);
        tv_login_name = findViewById(R.id.login_name_tv);
        tv_html_url =findViewById(R.id.html_url_tv);
        tv_gists_url =findViewById(R.id.gists_url_tv);
        tv_repos_url =findViewById(R.id.repos_url_tv);
        tv_events_url =findViewById(R.id.events_url_tv);


        position = MyAdapter.clickPos;
        text = MainActivity.text;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            JSONArray array = new JSONArray(text);
            feed = gson.fromJson(array.getString(position), GitUserExtraDetails.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_login_name.setText("Login name: "+feed.getLogin());
        tv_html_url.setText("html_url:  "+feed.getHtml_url());
        tv_events_url.setText("events_url: "+feed.getEvents_url());
        tv_gists_url.setText("gists_url: "+feed.getGists_url());
        tv_repos_url.setText("repos_url: "+feed.getRepos_url());
        Glide.with(context).load(feed.getAvatar_url()).into(iv_disp_img);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
