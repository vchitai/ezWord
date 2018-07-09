package com.ezword.ezword.background.user;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.dictionary.Dictionary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
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
    public final static String NOT_SIGNED_IN = "Not logged in";
    public final static String NOT_SYNC = "Data is not synced";
    public final static String SYNCED = "Data is synced";
    public final static String UNKNOWN = "Unknown";

    private final static String CONNECT_PROTOCOL = "http://";
    private final static String SERVER_IP = "192.168.1.3:5555/";
    private final static String API_PATH = "api/";
    private final static String SIGN_UP_PATH = "users";
    private final static String GET_TOKEN_PATH = "token";
    private final static String CHECK_TOKEN_PATH = "check_token";
    private final static String UPLOAD_PATH = "update_all";
    private final static String DOWNLOAD_PATH = "resource";

    private final static String FULL_SIGN_UP_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + SIGN_UP_PATH;
    private final static String FULL_GET_TOKEN_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + GET_TOKEN_PATH;
    private final static String FULL_CHECK_TOKEN_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + CHECK_TOKEN_PATH;
    private final static String FULL_UPLOAD_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + UPLOAD_PATH;
    private final static String FULL_DOWNLOAD_PATH = CONNECT_PROTOCOL + SERVER_IP + API_PATH + DOWNLOAD_PATH;

    private static final String HISTORY = "history";
    private static final String BOOKMARK = "bookmark";
    private static final String COUNT_DOWN_TIME = "count_down_time";
    private static final String NUM_OF_WORD_IN_SESSION = "num_of_word_in_session";
    private static final String FLASH_CARD = "flash_card";

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

    private String getListData(String type) {
        Set<String> data = null;
        if (type.equals(HISTORY)) {
            data = LocalData.getInstance(null).getHistory();
        }
        else if (type.equals(BOOKMARK)) {
            data = LocalData.getInstance(null).getBookmark();
        }
        StringBuilder res = new StringBuilder();
        if (data != null) {
            for (String s : data) {
                res.append(s).append(",");
            }
            res = new StringBuilder(res.substring(0, res.length() - 1));
            Log.d(type, String.valueOf(res));
            return res.toString();
        }
        return null;
    }

    private String getSettingData(String type) {
        int data = 0;
        if (type.equals(COUNT_DOWN_TIME)) {
            data = LocalData.getInstance(null).getCountDownTime();
        }
        else if (type.equals(NUM_OF_WORD_IN_SESSION)) {
            data = LocalData.getInstance(null).getNumOfWordInSession();
        }
        return String.valueOf(data);
    }

    public void uploadData() {
        String history = getListData(HISTORY);
        String bookmark = getListData(BOOKMARK);
        String countDownTimeSetting = getSettingData(COUNT_DOWN_TIME);
        String numWordInSessionSetting = getSettingData(NUM_OF_WORD_IN_SESSION);
        Log.d(HISTORY, history);
        Log.d(BOOKMARK, bookmark);
        Log.d(COUNT_DOWN_TIME, countDownTimeSetting);
        Log.d(NUM_OF_WORD_IN_SESSION, numWordInSessionSetting);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(HISTORY, history);
            jsonObject.put(BOOKMARK, bookmark);
            jsonObject.put(COUNT_DOWN_TIME, countDownTimeSetting);
            jsonObject.put(NUM_OF_WORD_IN_SESSION, numWordInSessionSetting);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        RequestBody body = RequestBody.create(JSON, json);

        String encoding = getBasicAuthenticationEncoding(mLoginToken, "unused");
        Request request = new Request.Builder()
                .url(FULL_UPLOAD_PATH)
                .addHeader("Authorization", "Basic " + encoding)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fail_up", "Fail to upload!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("upload", response.body().string());
                }
                else {
                    Log.d("upload", "no response");
                }
            }
        });
    }

    public void downloadData(final Context context) {
        String encoding = getBasicAuthenticationEncoding(mLoginToken, "unused");
        Request request = new Request.Builder().url(FULL_DOWNLOAD_PATH)
                .addHeader("Authorization", "Basic " + encoding).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("not_receive", "Cannot receive object");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String history = jsonObject.getString(HISTORY);
                        String bookmark = jsonObject.getString(BOOKMARK);
                        String countDownTime = jsonObject.getString(COUNT_DOWN_TIME);
                        String numWordInSession = jsonObject.getString(NUM_OF_WORD_IN_SESSION);
                        List<String> historyItems = Arrays.asList(history.split("\\s*,\\s*"));
                        List<String> bookmarkItems = Arrays.asList(bookmark.split("\\s*,\\s*"));
                        LocalData.getInstance(null).loadSyncDataFromServer(context, historyItems, bookmarkItems,
                                countDownTime, numWordInSession);
                        Log.d("download success", "Download Success!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("unsuccessful", "On response unsuccessful");
                }
            }
        });
    }
}