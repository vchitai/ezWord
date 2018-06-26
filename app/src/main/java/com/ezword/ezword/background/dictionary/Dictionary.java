package com.ezword.ezword.background.dictionary;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ezword.ezword.background.database.DictionaryContract;
import com.ezword.ezword.background.database.DictionaryContract.DictionaryEntry;
import com.ezword.ezword.background.database.DictionaryDBHelper;
import com.ezword.ezword.background.database.FlashCardContract;

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
        String []projection = new String[]{ DictionaryContract.DictionaryEntry._ID,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING};
        String []selectionArgs = new String[]{
                searchPhrase+"%"
        };
        String selection = DictionaryEntry.COLUMN_WORD_WORD_ENG + " LIKE ?";

        DictionaryDBHelper.getInstance(context).open();
        Cursor c = context.getContentResolver().query(DictionaryContract.DictionaryEntry.CONTENT_URI_WORD_ENG, projection, selection, selectionArgs, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int wordIDColumnIndex = c.getColumnIndex(DictionaryEntry._ID);
            int wordEngColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG);
            int wordTypeColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE);
            int wordDefColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION);
            int wordPronColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING);
            res = new Word(c.getInt(wordIDColumnIndex),
                    c.getString(wordEngColumnIndex),
                    c.getString(wordTypeColumnIndex),
                    c.getString(wordDefColumnIndex),
                    c.getString(wordPronColumnIndex));
            c.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        return res;
    }

    public Word getWordById(Context context, int id)
    {
        Word res = null;
        String []projection = new String[]{ DictionaryContract.DictionaryEntry._ID,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING};

        DictionaryDBHelper.getInstance(context).open();
        Cursor c = context.getContentResolver().query(ContentUris.withAppendedId(DictionaryEntry.CONTENT_URI, id), projection, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int wordIDColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry._ID);
            int wordEngColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG);
            int wordTypeColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE);
            int wordDefColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION);
            int wordPronColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING);
            res = new Word( c.getInt(wordIDColumnIndex),
                    c.getString(wordEngColumnIndex),
                    c.getString(wordTypeColumnIndex),
                    c.getString(wordDefColumnIndex),
                    c.getString(wordPronColumnIndex));
            c.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        return res;
    }

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

    public ArrayList<String> getRecommendations(Context context, String searchPhrase, int limit) {
        ArrayList<String> res = new ArrayList<>();
        String []projection = new String[]{DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG};
        String []selectionArgs = new String[]{
                searchPhrase + "%",
                String.valueOf(limit)
        };
        String selection = DictionaryEntry.COLUMN_WORD_WORD_ENG + " LIKE ?";
        String sortOrder = DictionaryEntry.COLUMN_WORD_WORD_ENG + " ASC LIMIT ?";

        DictionaryDBHelper.getInstance(context).open();
        Cursor c = context.getContentResolver().query(DictionaryContract.DictionaryEntry.CONTENT_URI_WORD_SUG, projection, selection, selectionArgs, sortOrder);
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
        return res;
    }

    public Word getRandomWord(Context context) {
        Word res = null;
        String []projection = new String[]{ DictionaryContract.DictionaryEntry._ID,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING};
        String sortOrder = "RANDOM() LIMIT 1";
        DictionaryDBHelper.getInstance(context).open();
        Cursor c = context.getContentResolver().query(DictionaryContract.DictionaryEntry.CONTENT_URI_WORD_ENG, projection, null, null, sortOrder);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int wordIDColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry._ID);
            int wordEngColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG);
            int wordTypeColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE);
            int wordDefColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION);
            int wordPronColumnIndex = c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING);
            res = new Word( c.getInt(wordIDColumnIndex),
                    c.getString(wordEngColumnIndex),
                    c.getString(wordTypeColumnIndex),
                    c.getString(wordDefColumnIndex),
                    c.getString(wordPronColumnIndex));
            c.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        return res;
    }

    public boolean checkFlashCardExist(Context context, int wordID) {
        boolean exist = false;
        String []projection = new String[] {
                FlashCardContract.FlashCardEntry.COLUMN_CARD_WORD_ID
        };
        String selection = "WordID = ?";
        String[] selectionArgs = new String[]{String.valueOf(wordID)};
        DictionaryDBHelper.getInstance(context).open();
        String sortOrders = " ASC LIMIT 1";
        Cursor c = context.getContentResolver().query(FlashCardContract.FlashCardEntry.CONTENT_URI, projection,
                selection, selectionArgs, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            exist = wordID == c.getInt(c.getColumnIndex(FlashCardContract.FlashCardEntry.COLUMN_CARD_WORD_ID));
            c.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        return exist;
    }

    public void addFlashCardToDatabase(Context context, int wordID) {
        if (checkFlashCardExist(context, wordID))
            return;
        DictionaryDBHelper.getInstance(context).open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("WordID", wordID);
        contentValues.put("ConsecutiveCorrect", 1);
        contentValues.put("NextLearningPoint", System.currentTimeMillis()+ 1000 * 60 * 60);
        contentValues.put("EF", 1);
        context.getContentResolver().insert(FlashCardContract.FlashCardEntry.CONTENT_URI, contentValues);
        DictionaryDBHelper.getInstance(context).close();
    }
}