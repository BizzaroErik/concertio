package com.concertioApp.network.com.conertioApp.network.networkUtils;

import com.concertioApp.concertio.BuildConfig;

public class NewsAPIBuilder {
    private static String defaultNewsURL= "https://newsapi.org/v2/everything?q=Music&from=2019-09-20&apiKey=" + BuildConfig.NEWS_API_KEY;
    public static String getDefaultNewsURL(){
        return defaultNewsURL;
    }
}
