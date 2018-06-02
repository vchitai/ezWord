package com.ezword.ezword.dictionary;

import java.util.ArrayList;

/**
 * Created by chita on 02/05/2018.
 */

public class Dictionary {
    private Dictionary mInstance = null;

    private Dictionary() {
        //Blah blah
    }

    public Dictionary getInstance() {
        if (mInstance != null) {
            mInstance = new Dictionary();
        }
        return mInstance;
    }

    public Word search(String searchPhrase) {
        return new Word();
    };

    public ArrayList<String> getRecommendations(String searchPhrase) {
        return new ArrayList<>();
    }
}
