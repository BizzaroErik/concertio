package com.concertioApp.systemUtils;

import com.concertioApp.com.concertioApp.objectAdapter.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class JSONtoNewsItems {

    public static LinkedList<NewsItem> convertToNewsList(JSONObject jo, LinkedList<NewsItem> nLL){
       // LinkedList<NewsItem> nLL = new LinkedList<>();
        try {
            JSONArray articleArray = jo.getJSONArray("articles");

            for(int i = 0; i < 50; i++){
                JSONObject singleArticle = articleArray.getJSONObject(i);
                NewsItem articleToAdd = new NewsItem(singleArticle);
                nLL.add(articleToAdd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nLL;
    }

}
