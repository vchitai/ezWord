package com.ezword.ezword.Dictionary;

/**
 * Created by chita on 02/05/2018.
 */

public class Word {
    private String mEnglish;
    private String mVietnamese;
    private String mPronunciation;

    public String getData(String tag) {
        if (tag.equals("English")) {
            return mEnglish;
        }
        return null;

    };
}
