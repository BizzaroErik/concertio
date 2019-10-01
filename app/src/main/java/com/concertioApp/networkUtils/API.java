package com.concertioApp.networkUtils;

public abstract class API {
    public String buildDefaultURL(){
        return "";
    }
    abstract void attachDate();
    abstract void attachQueryParams();
    abstract void attachKey();
}
