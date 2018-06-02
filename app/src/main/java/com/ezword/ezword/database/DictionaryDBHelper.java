package com.ezword.ezword.database;

import com.ezword.ezword.database.DictionaryContract.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chita on 02/05/2018.
 */

public class DictionaryDBHelper extends SQLiteOpenHelper {
    public static final  String LOG_TAG          = DictionaryDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME    = "dictionary.db";
    private static final int    DATABASE_VERSION = 1;


    public DictionaryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DICTIONARY_TABLE = "CREATE TABLE " + DictionaryEntry.TABLE_NAME + " ("
                + DictionaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DictionaryEntry.COLUMN_WORD_WORD + " TEXT NOT NULL, "
                + DictionaryEntry.COLUMN_WORD_TYPE + " TEXT, "
                + DictionaryEntry.COLUMN_WORD_DEFINITION + " TEXT, "
                + DictionaryEntry.COLUMN_WORD_PRONUNCIATION + " TEXT); ";

        db.execSQL(SQL_CREATE_DICTIONARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
