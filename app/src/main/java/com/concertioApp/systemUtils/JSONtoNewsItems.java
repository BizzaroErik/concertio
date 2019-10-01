package com.concertioApp.systemUtils;

import com.concertioApp.com.concertioApp.objectAdapter.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONtoNewsItems {

    public static ArrayList<NewsItem> convertToNewsList(JSONObject jo, ArrayList<NewsItem> nLL){
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
