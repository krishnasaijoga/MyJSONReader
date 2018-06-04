package com.example.myjsonreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by developer on 5/24/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<GitHubUsersInfo> mFeedList;
    private Context mcxt;
    public static int mPosition,clickPos;

    public MyAdapter(Context cxt, List<GitHubUsersInfo> mFeedList) {
        this.mFeedList = mFeedList;
        this.mcxt = cxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcxt).inflate(R.layout.my_list_element_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        mPosition = position;
        holder.tv_login.setText(mFeedList.get(position).getLogin());
        holder.tv_id.setText(mFeedList.get(position).getId()+"");
        holder.tv_url.setText(mFeedList.get(position).getUrl());
        holder.tv_site_admin.setText(mFeedList.get(position).isSite_admin()+"");
        Glide.with(mcxt).load(mFeedList.get(position).getAvatar_url()).into(holder.iv_avatar);

    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_avatar;
        public TextView tv_login,tv_id,tv_url,tv_site_admin;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_login = itemView.findViewById(R.id.login_tv);
            tv_id = itemView.findViewById(R.id.id_tv);
            tv_url = itemView.findViewById(R.id.url_tv);
            tv_site_admin = itemView.findViewById(R.id.site_admin_tv);
            iv_avatar = itemView.findViewById(R.id.avatar_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickPos = MainActivity.rv.getChildLayoutPosition(v);
                    Intent intent = new Intent(mcxt,DetailsActivity.class);
                    mcxt.startActivity(intent);
                }
            });
        }
    }

}
