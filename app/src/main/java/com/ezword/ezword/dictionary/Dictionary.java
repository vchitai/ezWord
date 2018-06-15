package com.ezword.ezword.dictionary;

import android.content.Context;
import android.database.Cursor;

import com.ezword.ezword.database.DictionaryContract;
import com.ezword.ezword.database.DictionaryDBHelper;

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
        Word res = null;
        String []projection = new String[]{DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING};
        String []selectionArgs = new String[]{
                searchPhrase
        };
        DictionaryDBHelper.getInstance(context).open();
        Cursor c = context.getContentResolver().query(DictionaryContract.DictionaryEntry.CONTENT_URI_WORD_ENG, projection, null, selectionArgs, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            res = new Word(c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING)));
            c.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        return res;
    };

    public String[] getAllWords(Context context) {
        ArrayList<String> res = new ArrayList<>();
        String []projection = new String[]{DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG};
        DictionaryDBHelper.getInstance(context).open();
        Cursor c = context.getContentResolver().query(DictionaryContract.DictionaryEntry.CONTENT_URI, projection, null, null, null);
        if (c!=null) {
            try {
                int wordEngColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG);
                while (c.moveToNext()) {
                    res.add(c.getString(wordEngColumnIndex));
                }
            } finally {
                c.close();
            }
        }
        DictionaryDBHelper.getInstance(context).close();
        String []finalRes = new String[res.size()];
        finalRes = res.toArray(finalRes);
        return finalRes;
    }
/*
    public ArrayList<String> getRecommendations(Context context, String searchPhrase, Integer limit) {
        DictionaryProvider dictionaryProvider = DictionaryProvider.getInstance();
        return dictionaryProvider.getRecommendations(context, searchPhrase, limit);
    }*/
}
