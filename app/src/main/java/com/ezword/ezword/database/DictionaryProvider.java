package com.ezword.ezword.database;

import com.ezword.ezword.database.DictionaryContract.*;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by chita on 02/06/2018.
 */

public class DictionaryProvider extends ContentProvider {
    public static final  String     LOG_TAG     = DictionaryProvider.class.getSimpleName();
    private static final int        WORD       = 100;
    private static final int        WORD_ID     = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DictionaryDBHelper mDictDBHelper;
    private SQLiteDatabase mDictDatabase;

    static {
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY, WORD);
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/#", WORD_ID);
    }

    @Override
    public boolean onCreate() {
        mDictDBHelper = new DictionaryDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        mDictDBHelper.open();
        mDictDatabase = mDictDBHelper.getMyDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case WORD:
                cursor = mDictDatabase.query(DictionaryEntry.TABLE_WORD, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORD_ID:
                selection = DictionaryEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                cursor = mDictDatabase.query(DictionaryEntry.TABLE_WORD, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        //mDictDBHelper.close();
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORD:
                return DictionaryEntry.CONTENT_LIST_TYPE;
            case WORD_ID:
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
}
