package com.ezword.ezword.user;

import com.ezword.ezword.dictionary.Word;

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

    public float getUserProgress() {
        return 0;
    };

    public ArrayList<String> getUserReviseList() {
        return new ArrayList<>();
    }

    public ArrayList<String> getUserDiscoveredList() {
        return new ArrayList<>();
    }

    public boolean transferDiscoveredToReviseList(int posInDiscovered, int posInReviseList) {
        return true;
    }

    public void updateDailyProgress() {

    }
}
