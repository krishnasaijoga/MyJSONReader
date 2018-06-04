package com.example.myjsonreader;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by developer on 5/24/18.
 */

public class Util {

    public static List<GitHubUsersInfo> loadInfiniteFeedList(Context applicationContext, String text) {
        List<GitHubUsersInfo> feedList = new ArrayList<>();


        try {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(text);
            feedList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                GitHubUsersInfo feed = gson.fromJson(array.getString(i), GitHubUsersInfo.class);
                //Log.d("JSONARRAY OBJECTS",array.getString(i));
                feedList.add(feed);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedList;
    }
}
