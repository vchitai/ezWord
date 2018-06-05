package com.ezword.ezword.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ezword.ezword.dictionary.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by chita on 02/05/2018.
 */

public class DictionaryDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "DictionaryDatabase.db";
    private static final String DB_PATH = "data/data/com.ezword.ezword/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DictionaryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        boolean dbExist = checkDatabase();
        if (dbExist){

        }
        else {
            System.out.println("Database doesn't exist");
            createDatabase();
        }
    }

    private void createDatabase() {
        this.getReadableDatabase();
        try {
            copyDatabase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getMyDatabase() {
        return mDatabase;
    }

    public void open() {
        String dbPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            mContext.deleteDatabase(DB_NAME);
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabase() {
        boolean checkdb = false;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkdb = dbFile.exists();
        }
        catch (SQLiteException e)
        {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copyDatabase() throws IOException {
        AssetManager dirPath = mContext.getAssets();
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public Word getWord(String inputWord) {
        SQLiteDatabase db = getMyDatabase();
        String lowerCase = inputWord.toLowerCase();
        String query = "select w.WordID, w.WordEnglish, w.Type, w.Definition, w.PhoneticSpelling" +
                "from Word w where w.WordEnglish like '" + lowerCase + "%'" + "LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Word res = new Word(c.getString(c.getColumnIndex("WordEnglish")),
                c.getString(c.getColumnIndex("Type")), c.getString(c.getColumnIndex("Definition")),
                c.getString(c.getColumnIndex("PhoneticSpelling")));
        c.close();
        return res;
    }

    public ArrayList<Word> getListOfWord(String inputWord, int limit) {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getMyDatabase();
        String lowerCase = inputWord.toLowerCase();
        String query = "select w.WordID, w.WordEnglish, w.Type, w.Definition, w.PhoneticSpelling" +
                "from Word w where w.WordEnglish like '" + lowerCase + "%'" + "LIMIT " + limit;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Word res = new Word(c.getString(c.getColumnIndex("WordEnglish")),
                    c.getString(c.getColumnIndex("Type")), c.getString(c.getColumnIndex("Definition")),
                    c.getString(c.getColumnIndex("PhoneticSpelling")));
            words.add(res);
            c.moveToNext();
        }
        c.close();
        return words;
    }
}
