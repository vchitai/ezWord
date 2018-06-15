package com.ezword.ezword.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ezword.ezword.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chita on 02/05/2018.
 */

public class DictionaryDBHelper extends SQLiteOpenHelper {
    private final String TAG = this.getClass().getName();
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "DictionaryDatabase.db";
    private static final String DB_PATH = "data/data/com.ezword.ezword/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static DictionaryDBHelper mInstance = null;

    private DictionaryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        boolean dbExist = checkDatabaseExist();
        if (!dbExist) {
            Log.e(TAG, mContext.getString(R.string.data_not_exist));
            createDatabase();
        }
    }

    public static DictionaryDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DictionaryDBHelper(context);
        }
        return mInstance;
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

    private boolean checkDatabaseExist() {
        boolean checkDBExist = false;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkDBExist = dbFile.exists();
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, mContext.getString(R.string.data_not_exist));
        }
        return checkDBExist;
    }

    private void copyDatabase() throws IOException {
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
}
