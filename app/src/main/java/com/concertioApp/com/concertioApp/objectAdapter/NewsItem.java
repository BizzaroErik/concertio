package com.concertioApp.com.concertioApp.objectAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsItem {
    //json articles->title
    public String title;
    //json articles->description
    public String desc;
    //json articles->publishedAt
    public String age;
    //json articles->urlToImage
    public String urlToImage;
    //json articles->url
    public String url;

    //Holds info for recyclerview objects
    public NewsItem(JSONObject article) {
        try {
            this.title = article.getString("title");
            this.desc = article.getString("description");
            this.age = article.getString("publishedAt");
            this.urlToImage = article.getString("urlToImage");
            this.url = article.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}