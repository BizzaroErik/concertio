package com.concertioApp.com.concertioApp.objectAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class ConcertItem {

    public String title;
    public String location;
    public String date;
    public String urlToTickets;
    public String venueUrl;
    public String desc;
    public ConcertItem(JSONObject event){
        try {
            this.title      =       event.getString("title");
            this.location   =       event.getString("venue_name");
            this.date       =       event.getString("start_time");
            this.urlToTickets =     event.getString("url");
            this.venueUrl   =       event.getString("venue_url");
            this.desc       =       event.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
