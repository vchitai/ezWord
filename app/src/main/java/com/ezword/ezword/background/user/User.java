package com.ezword.ezword.background.user;

import android.util.Base64;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chita on 02/05/2018.
 */

public class User {
    private static User mInstance;
    private String mUserName;
    private int mSyncStatus;
    private String mLoginToken;
    private OkHttpClient client;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public final static int CD_NOT_SIGNED_IN = 1;
    public final static int CD_NOT_SYNC = 2;
    public final static int CD_SYNCED = 3;
    private final static String NOT_SIGNED_IN = "Not logged in";
    private final static String NOT_SYNC = "Data is not synced";
    private final static String SYNCED = "Data is synced";
    private final static String UNKNOWN = "Unknown";

    private User() {
        client = new OkHttpClient();
    }

    public static User getInstance() {
        if (mInstance == null)
            mInstance = new User();
        return mInstance;
    }


    public boolean isLoggedIn() throws IOException {
        if (mLoginToken == null)
            return false;

        String encoding = getBasicAuthenticationEncoding(mLoginToken, "unused");
        Request request = new Request.Builder()
                .url("http://192.168.1.4:5555/api/token")
                .addHeader("Authorization", "Basic " + encoding)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() == null)
            return false;
        String s =  response.body().string();
        return true;
    }

    private String getBasicAuthenticationEncoding(String username, String password) {
        String userPassword = username + ":" + password;
        return Base64.encodeToString(userPassword.getBytes(),Base64.NO_WRAP);
    }

    public boolean signUp(String userName, String password) throws IOException {
        String json = "{\"username\":\""+userName+"\",\"password\":\""+password+"\"}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://192.168.1.4:5555/api/users")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String s =  response.body().string();
        return true;
    }

    public boolean logIn(String userName, String password) {
        boolean res = true;


        return res;
    }

    public boolean syncData() {
        boolean res = true;

        return res;
    }

    public int getSyncStatus() {
        try {
            if (!isLoggedIn()) {
                mSyncStatus = CD_NOT_SIGNED_IN;
                return CD_NOT_SIGNED_IN;
            }
            else
                return -1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getLastSyncStatus() {
        switch (mSyncStatus) {
            case CD_NOT_SIGNED_IN:
                return NOT_SIGNED_IN;
            case CD_NOT_SYNC:
                return NOT_SYNC;
            case CD_SYNCED:
                return SYNCED;
            default:
                return UNKNOWN;
        }
    }

}