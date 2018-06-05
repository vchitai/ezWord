package com.ezword.ezword.dictionary;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.ezword.ezword.database.DictionaryContract;

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
        String []selectionArgument = new String[]{ searchPhrase + "%" };
        String whereClause = DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " LIKE ?";
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = contentResolver.query(DictionaryContract.DictionaryEntry.CONTENT_URI,
                projection, whereClause, selectionArgument, DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " ASC" + " LIMIT 1");
        if (c != null) {
            c.moveToFirst();
            res = new Word(c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING)));
            c.close();
        }
        return res;
    };

    public ArrayList<String> getRecommendations(Context context, String searchPhrase, Integer limit) {
        ArrayList<String> res = new ArrayList<>();
        String []projection = {DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG};
        String []selectionArgument = { searchPhrase + "%" };
        String whereClause = DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " LIKE ?";
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = contentResolver.query(DictionaryContract.DictionaryEntry.CONTENT_URI,
                projection, whereClause, selectionArgument,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " ASC " +"LIMIT " + limit);
        if (c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                res.add(c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG)));
                c.moveToNext();
            }
            c.close();
        }
        return res;
    }
}
