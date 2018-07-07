package com.ezword.ezword.background.services;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CustomSearchEngine {
    private final static String SearchAPI = "https://www.googleapis.com/customsearch/v1";
    private final static String APIkey = "key=AIzaSyB2X_OdDC_Ti5SghFT6bbckLqbVEciws_k";
    private final static String CustomSearchEngineID = "cx=008138058657872763058:lh1xelbuwm4";
    private final static String SearchType = "searchType=image";
    private final static int NumberOfResults = 4;
    private final static String Num = "num="+String.valueOf(NumberOfResults);

    public String requestLinkBuilder(String request) {
        return SearchAPI + "?" + APIkey + "&" + CustomSearchEngineID + "&" + SearchType + "&" + Num + "&q="+ request;
    }

    public ArrayList<String> getLastSearchResults() {
        return new ArrayList<>();
    };

    public boolean startSearch(Callback callback, String req) {
        OkHttpClient client = new OkHttpClient();
        if (req.equals(""))
            return false;
        Request request = new Request.Builder()
                .url(requestLinkBuilder(req))
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return true;
    }
}
