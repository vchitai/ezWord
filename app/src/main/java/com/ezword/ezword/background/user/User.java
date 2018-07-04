package com.ezword.ezword.background.user;

import android.util.Base64;

import com.ezword.ezword.background.database.LocalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
    public final static String NOT_SIGNED_IN = "Not logged in";
    public final static String NOT_SYNC = "Data is not synced";
    public final static String SYNCED = "Data is synced";
    public final static String UNKNOWN = "Unknown";

    private final static String CONNECT_PROTOCOL = "http://";
    private final static String SERVER_IP = "192.168.1.77:5555/";
    private final static String API_PATH = "api/";
    private final static String SIGN_UP_PATH = "users";
    private final static String GET_TOKEN_PATH = "token";
    private final static String CHECK_TOKEN_PATH = "check_token";

    private final static String FULL_SIGN_UP_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + SIGN_UP_PATH;
    private final static String FULL_GET_TOKEN_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + GET_TOKEN_PATH;
    private final static String FULL_CHECK_TOKEN_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + CHECK_TOKEN_PATH;
    private User() {
        client = new OkHttpClient();
        mLoginToken = LocalData.getInstance(null).getToken();
        mUserName = LocalData.getInstance(null).getLastSignIn();
    }

    public static User getInstance() {
        if (mInstance == null)
            mInstance = new User();
        return mInstance;
    }


    public void isLoggedIn(Callback callback) throws IOException {
        if (mLoginToken == null)
            return;

        String encoding = getBasicAuthenticationEncoding(mLoginToken, "unused");
        Request request = new Request.Builder()
                .url(FULL_CHECK_TOKEN_PATH)
                .addHeader("Authorization", "Basic " + encoding)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    private String getBasicAuthenticationEncoding(String username, String password) {
        String userPassword = username + ":" + password;
        return Base64.encodeToString(userPassword.getBytes(),Base64.NO_WRAP);
    }

    public void signUp(String userName, String password, Callback callback) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(FULL_SIGN_UP_PATH)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void logIn(String userName, String password, Callback callback) throws IOException {
        String encoding = getBasicAuthenticationEncoding(userName, password);
        Request request = new Request.Builder()
                .url(FULL_GET_TOKEN_PATH)
                .addHeader("Authorization", "Basic " + encoding)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public boolean syncData() {
        boolean res = true;

        return res;
    }

    public void setToken(String token) {
        mLoginToken = token;
        LocalData.getInstance(null).storeToken(token);
    }

    public void setLastSignInUsername(String username) {
        mUserName = username;
        LocalData.getInstance(null).registerLastSignIn(username);
    }

    public boolean checkSynced() {
        return LocalData.getInstance(null).checkSynced(mUserName);
    };

    public int getSyncStatus() {
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

    public void uploadData() {
    }

    public void downloadData() {

    }
}