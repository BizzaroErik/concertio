package com.concertioApp.systemUtils;

import com.concertioApp.com.concertioApp.objectAdapter.ConcertItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class JSONtoConcertItems {

    public static LinkedList<ConcertItem> convertToConcertList(JSONObject jo, LinkedList<ConcertItem> nLL){
        // LinkedList<NewsItem> nLL = new LinkedList<>();
        try {
            JSONObject jEvents = jo.getJSONObject("events");
            JSONArray articleArray = jEvents.getJSONArray("event");

            for(int i = 0; i < 50; i++){
                JSONObject singleEvent = articleArray.getJSONObject(i);
                ConcertItem eventToAdd = new ConcertItem(singleEvent);
                nLL.add(eventToAdd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nLL;
    }

}