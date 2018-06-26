package com.ezword.ezword.background.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by NVP1010 on 6/26/2018.
 */

public class FlashCardContract {
    public static final String CONTENT_AUTHORITY = "com.ezword.ezword";
    public static final Uri BASE_CONTENT_URI  = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DICTIONARY        = "FlashCard";

    private FlashCardContract() {
    }

    public static final class FlashCardEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;

        public final static String TABLE_FLASH_CARD = "FlashCard";

        public final static String _ID = "CardID";
        public final static String COLUMN_CARD_WORD_ID = "WordID";
        public final static String COLUMN_CARD_NOTE = "Note";
        public final static String COLUMN_CARD_EF = "EF";
        public final static String COLUMN_CARD_NEXT_LEARNING_POINT = "NextLearningPoint";
        public final static String COLUMN_CARD_CONSECUTIVE_CORRECT = "ConsecutiveCorrect";


        public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY);
        public static final Uri CONTENT_CARD_NOTE = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY + "/note?=");
        public static final Uri CONTENT_CARD_WORD_ID = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY + "/wordid?=");
        public static final Uri CONTENT_CARD_EF = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY + "/ef?=");
        public static final Uri CONTENT_CARD_NEXT_LEARNING_POINT = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY + "/nextlearningpoint=?");
        public static final Uri CONTENT_CARD_CONSECUTIVE_CORRECT = Uri.parse(BASE_CONTENT_URI + "/" + PATH_DICTIONARY + "/consecutivecorrect=?");
    }
}
