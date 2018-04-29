package com.robin994.streamsoccer.Connection;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RssGetter {


    private static String stream = null;
    private Context context;
    private String url;



    public RssGetter(Context context) {
        this.context = context;
    }

    public RssGetter() {
        new RssGetter(null);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url.contains(".rss"))
            this.url = url;
        else
            this.url = url + ".rss?sort=new";
    }

    public String GetRssData(String urlString) {
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("test RSS", stream);
        return stream;
    }


}