package com.concertioApp.systemUtils;

import com.concertioApp.com.concertioApp.objectAdapter.ConcertItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONtoConcertItems {

    public static ArrayList<ConcertItem> convertToConcertList(JSONObject jo, ArrayList<ConcertItem> nLL){
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