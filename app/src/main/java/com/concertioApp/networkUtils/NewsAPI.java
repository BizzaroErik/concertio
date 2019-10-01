package com.concertioApp.networkUtils;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import com.concertioApp.concertio.BuildConfig;
import java.util.Date;

public class NewsAPI extends API {
    private static String baseNewsURL = "https://newsapi.org/v2/everything?";
    private static String newsKey = BuildConfig.NEWS_API_KEY;
    String defaultURL;

    public NewsAPI(){
        defaultURL = baseNewsURL;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        defaultURL += "&from="+dateFormat.format(date);
    }

    @Override
    public void attachQueryParams(){
        defaultURL += "q=Music";
    }

    @Override
    public void attachKey(){
        defaultURL += "&apiKey="+newsKey;
    }
}
