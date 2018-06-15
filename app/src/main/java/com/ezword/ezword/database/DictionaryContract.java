package com.ezword.ezword.database;

import android.net.Uri;

import android.content.ContentResolver;
import android.provider.BaseColumns;

public class DictionaryContract {
    public static final String CONTENT_AUTHORITY = "com.ezword.ezword";
    public static final Uri BASE_CONTENT_URI  = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DICTIONARY        = "Word";

    private DictionaryContract() {
    }

    public static final class DictionaryEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;

        public final static String TABLE_WORD = "Word";

        public final static String _ID                     = BaseColumns._ID;
        public final static String COLUMN_WORD_WORD_ENG       = "WordEnglish";
        public final static String COLUMN_WORD_TYPE      = "Type";
        public final static String COLUMN_WORD_DEFINITION = "Definition";
        public final static String COLUMN_WORD_PHONETIC_SPELLING      = "PhoneticSpelling";

        public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY);
        public static final Uri CONTENT_URI_WORD_ENG = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY + "/eng?=");
    }
}