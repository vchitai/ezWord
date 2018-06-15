package com.ezword.ezword.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ezword.ezword.database.DictionaryContract.DictionaryEntry;

/**
 * Created by chita on 02/06/2018.
 */

public class DictionaryProvider extends ContentProvider {
    private static DictionaryProvider mInstance = null;
    public static final  String     TAG     = DictionaryProvider.class.getSimpleName();
    private static final int        WORD       = 100;
    private static final int        WORD_ID     = 101;
    private static final int WORD_ENG = 102;
    private static final int WORD_SUG = 103;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DictionaryDBHelper mDictDBHelper;

    public static DictionaryProvider getInstance() {
        if (mInstance == null) {
            mInstance = new DictionaryProvider();
        }
        return mInstance;
    }

    static {
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY, WORD);
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/#", WORD_ID);

        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/eng/", WORD_ENG);
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/eng/*", WORD_ENG);

        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/suggestion/", WORD_SUG);
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/suggestion/*", WORD_SUG);
    }

    @Override
    public boolean onCreate() {
        mDictDBHelper = DictionaryDBHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        SQLiteDatabase myDatabase = mDictDBHelper.getMyDatabase();
        switch (match) {
            case WORD:
                cursor = myDatabase.query(DictionaryEntry.TABLE_WORD, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORD_ID:
                selection = DictionaryEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                cursor = myDatabase.query(DictionaryEntry.TABLE_WORD, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORD_ENG:
                cursor = myDatabase.query(DictionaryEntry.TABLE_WORD, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORD_SUG:
                cursor = myDatabase.query(DictionaryEntry.TABLE_WORD, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORD:
            case WORD_SUG:
                return DictionaryEntry.CONTENT_LIST_TYPE;
            case WORD_ID:
            case WORD_ENG:
                return DictionaryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
/*
    public Word search(Context context, String searchPhrase)
    {
        //mDictDBHelper = new DictionaryDBHelper(getContext());
        //mDictDBHelper.open();
        //mDictDatabase = mDictDBHelper.getMyDatabase();
        Word res = null;
        String []projection = new String[]{DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING};
        String []selectionArgument = new String[]{ searchPhrase + "%" };
        String whereClause = DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " LIKE ?";
        //ContentResolver contentResolver = context.getContentResolver();
        Cursor c = this.query(DictionaryContract.DictionaryEntry.CONTENT_URI,
                projection, whereClause, selectionArgument, DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " ASC" + " LIMIT 1");
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            res = new Word(c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_TYPE)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_DEFINITION)),
                    c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_PHONETIC_SPELLING)));
            c.close();
        }
        mDictDBHelper.close();
        return res;
    }

    public ArrayList<String> getRecommendations(Context context, String searchPhrase, Integer limit) {
        //mDictDBHelper = new DictionaryDBHelper(getContext());
        mDictDBHelper.open();
        //mDictDatabase = mDictDBHelper.getMyDatabase();
        ArrayList<String> res = new ArrayList<>();
        String []projection = {DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG};
        String []selectionArgument = { searchPhrase + "%" };
        String whereClause = DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " LIKE ?";
        //ContentResolver contentResolver = context.getContentResolver();
        Cursor c = this.query(DictionaryContract.DictionaryEntry.CONTENT_URI,
                projection, whereClause, selectionArgument,
                DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG + " ASC " +"LIMIT " + limit);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                res.add(c.getString(c.getColumnIndex(DictionaryContract.DictionaryEntry.COLUMN_WORD_WORD_ENG)));
                c.moveToNext();
            }
            c.close();
        }
        mDictDBHelper.close();
        return res;
    }*/
}
