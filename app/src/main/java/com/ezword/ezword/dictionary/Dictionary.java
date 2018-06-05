package com.ezword.ezword.dictionary;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.ezword.ezword.database.DictionaryContract;
import com.ezword.ezword.database.DictionaryProvider;

import java.util.ArrayList;

/**
 * Created by chita on 02/05/2018.
 */

public class Dictionary {
    private static Dictionary mInstance = null;
    private Dictionary() {

    }

    public static Dictionary getInstance() {
        if (mInstance == null) {
            mInstance = new Dictionary();
        }
        return mInstance;
    }

    public Word search(Context context, String searchPhrase)
    {
        DictionaryProvider dictionaryProvider = DictionaryProvider.getInstance();
        return dictionaryProvider.search(context, searchPhrase);
    };

    public ArrayList<String> getRecommendations(Context context, String searchPhrase, Integer limit) {
        DictionaryProvider dictionaryProvider = DictionaryProvider.getInstance();
        return dictionaryProvider.getRecommendations(context, searchPhrase, limit);
    }
}
