package com.ezword.ezword.background.user;

import com.ezword.ezword.background.dictionary.Word;

import java.util.ArrayList;

/**
 * Created by chita on 02/05/2018.
 */

public class User {
    private String mName;
    private ArrayList<Word> mDiscoveredList;
    private ArrayList<Word> mReviseList;
    private String mLoginToken;

    public boolean isLoggedIn() {
        boolean res = true;

        return res;
    }

    public boolean checkDataSync() {
        boolean res = true;

        return res;
    }

    public boolean syncData() {
        boolean res = true;

        return res;
    }
}
