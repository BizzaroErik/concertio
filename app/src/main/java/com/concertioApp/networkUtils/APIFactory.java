package com.concertioApp.networkUtils;

import android.util.Log;

public class APIFactory {

    //"https://newsapi.org/v2/everything?q=Music&from=2019-09-20&apiKey="
    //"https://api.eventful.com/json/events/search?q=music&sort_order=date&l=Chicago&app_key="

    public APIFactory(){
    }

    public API getApi(String apiType){
        API api = null;
        switch (apiType){
            case "NEWS":
                api = new NewsAPI();
                break;
            case "EVENTS":
                api = new EventfulAPI();
                break;
            default:
                Log.d("ERROR", "Wrong type input");
        }
        return api;
    }

}
