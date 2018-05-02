package com.ezword.ezword.userProfile;

import com.ezword.ezword.dictionary.Word;

import java.util.ArrayList;

/**
 * Created by chita on 02/05/2018.
 */

public class UserProfile {

    private String mName;
    private ArrayList<Word> mDiscoveredList;
    private ArrayList<Word> mReviseList;

    public float getUserProgress() {
        return 0;
    };

    public ArrayList<String> getUserReviseList() {
        return new ArrayList<>();
    }

    public ArrayList<String> getUserDiscoveredList() {
        return new ArrayList<>();
    }

    public boolean transferDicoveredToReviseList(int posInDiscovered, int posInReviseList) {
        return true;
    }
}
