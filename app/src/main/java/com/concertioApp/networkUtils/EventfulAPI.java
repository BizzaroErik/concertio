package com.concertioApp.networkUtils;

import com.concertioApp.concertio.BuildConfig;

public class EventfulAPI extends API {
    String baseEventURL ="https://api.eventful.com/json/events/search?";
    String eventKey = BuildConfig.EVENTS_API_KEY;
    String defaultURL;

    public EventfulAPI(){
        defaultURL = baseEventURL;
    }

    @Override
    public String buildDefaultURL(){
        this.attachQueryParams();
        this.attachDate();
        this.attachKey();
        return defaultURL;
    }

    @Override
    public void attachDate() {
    }

    @Override
    public void attachQueryParams(){
        defaultURL += "q=music&sort_order=date&l=Chicago";
    }

    @Override
    public void attachKey() {
        defaultURL += "&app_key="+eventKey;
    }
}
