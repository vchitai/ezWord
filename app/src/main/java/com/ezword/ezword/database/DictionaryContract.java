package com.ezword.ezword.database;

import android.net.Uri;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DictionaryContract {
    public static final String CONTENT_AUTHORITY = "com.example.readingJournal";
    public static final Uri BASE_CONTENT_URI  = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DICTIONARY        = "dictionary";

    private DictionaryContract() {
    }

    public static final class DictionaryEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;

        public final static String TABLE_NAME = "dictionary";

        public final static String _ID                     = BaseColumns._ID;
        public final static String COLUMN_WORD_WORD       = "word";
        public final static String COLUMN_WORD_TYPE      = "type";
        public final static String COLUMN_WORD_DEFINITION = "definition";
        public final static String COLUMN_WORD_PRONUNCIATION      = "pronunciation";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DICTIONARY);

    }
}