package com.example.myjsonreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    static Activity context;
    TextView tv_login,tv_id,tv_url,tv_site_admin;
    ImageView iv_avatar_url;
    List<GitHubUsersInfo> feedListInfo = new ArrayList<>();
    static String text = "";
    ProgressDialog pd;
    static RecyclerView rv;
    String api_url="";
    static int count=0;
    static LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //instantiating views
        tv_login = findViewById(R.id.login_tv);
        tv_id = findViewById(R.id.id_tv);
        tv_url = findViewById(R.id.url_tv);
        tv_site_admin = findViewById(R.id.site_admin_tv);
        iv_avatar_url = findViewById(R.id.avatar_iv);
        rv = findViewById(R.id.list_rv);

        api_url = "https://api.github.com/users?since=0&per_page=10";


    }


    @Override
    protected void onStart() {
        super.onStart();
        BackTask bt = new BackTask();
        bt.execute(api_url);


        //Log.d("INSIDE TEXT",text);

    }


    private class BackTask extends AsyncTask<String, Integer, Void>
    {
        List<GitHubUsersInfo> feedList;

        public int getCount()
        {
            return count;
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(String... strings) {
            URL url;
            try
            {
                url = new URL(strings[0]);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                while((line=br.readLine())!=null)
                {
                    text += line;
                }


            } catch (Exception e)
            {
                e.printStackTrace();
                if(pd!=null)
                    pd.dismiss();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setTitle("Reading the text file");
            pd.setMessage("Please wait.");
            pd.setCancelable(true);
            pd.setIndeterminate(false);
            pd.show();
            text="";
            count++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {//thread has already returned to main thread
            super.onPostExecute(aVoid);
            if(pd!=null)
                pd.dismiss();

            //set adapter
            feedListInfo = Util.loadInfiniteFeedList(context.getApplicationContext(),text);
            //displayContents();

            MyAdapter adapter = new MyAdapter(context,feedListInfo);
            rv.setAdapter(adapter);

            llm = new LinearLayoutManager(context);
            rv.setLayoutManager(llm);
            rv.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));

            rv.scrollBy(0,50);//For getting scroll up without necessarily scrolling down to get previous feeds

            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Log.d("dy=",""+dy);
                    if(dy>0)
                    {
                        int lastVisibleItem,totalitemCount=9;
                        lastVisibleItem = llm.findLastVisibleItemPosition();

                        int firstVisibleItem = llm.findFirstCompletelyVisibleItemPosition();
                        Log.d("firstVisibleItem",""+firstVisibleItem);
                        Log.d("lastvisibleitem",""+lastVisibleItem);
                        Log.d("childcount",""+llm.getChildCount());
                        Log.d("Thread Count",""+getCount());
                        if((lastVisibleItem) >= totalitemCount  && getCount()==0)
                        {
                            api_url = loadNextSet(api_url,10);
                            BackTask backTask = new BackTask();
                            //Log.d("new api url", api_url);
                            backTask.execute(api_url);
                        }
                    }
                    else if(dy<0)// TODO : have to fix scroll up
                    {
                        int lastVisibleItem,totalitemCount=9,firstVisibleitem;
                        firstVisibleitem = llm.findFirstVisibleItemPosition();
                        lastVisibleItem = llm.findLastVisibleItemPosition();
                        Log.d("firstVisibleItem",""+firstVisibleitem);
                        int num = getNumFromUrl(api_url);
                        if ((num > 0)) {
                            Log.d("count", "" + getCount());
                            if ((firstVisibleitem == 0) && getCount() == 0) {
                                api_url = loadNextSet(api_url, -10);
                                BackTask backTask = new BackTask();
                                //Log.d("new api url", api_url);
                                backTask.execute(api_url);
                            }
                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);


                }

                private int getNumFromUrl(String api_url) {
                    int start_ind = api_url.indexOf("since=");
                    int end_ind = api_url.indexOf("&per_");
                    String temp = api_url.substring(start_ind+"since=".length(),end_ind);
                    return Integer.parseInt(temp);
                }

                private String loadNextSet(String api_url,int set_length) {
                    String res="";
                    int prev_num = getNumFromUrl(api_url);
                    int new_num = prev_num+set_length;
                    res = api_url.replace("since="+prev_num,"since="+new_num);
                    return res;
                }
            });
            count--;
        }

    }
}

// TODO : replace paging with permanent storage in case of scroll up