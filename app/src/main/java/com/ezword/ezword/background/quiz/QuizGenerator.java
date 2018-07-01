package com.ezword.ezword.background.quiz;

import android.content.Context;
import android.database.Cursor;

import com.ezword.ezword.background.database.DictionaryDBHelper;
import com.ezword.ezword.background.database.FlashCardContract;
import com.ezword.ezword.background.dictionary.FlashCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chita on 05/06/2018.
 */

public class QuizGenerator {
    public static ArrayList<FlashCard> generateFlashCards(Context context, int limit) {
        ArrayList<FlashCard> flashCards = new ArrayList<>();
        DictionaryDBHelper.getInstance(context).open();
        String []projection = {FlashCardContract.FlashCardEntry._ID, FlashCardContract.FlashCardEntry.COLUMN_CARD_WORD_ID,
                FlashCardContract.FlashCardEntry.COLUMN_CARD_NOTE, FlashCardContract.FlashCardEntry.COLUMN_CARD_EF,
                FlashCardContract.FlashCardEntry.COLUMN_CARD_NEXT_LEARNING_POINT, FlashCardContract.FlashCardEntry.COLUMN_CARD_CONSECUTIVE_CORRECT};
        String sortOrder = FlashCardContract.FlashCardEntry.COLUMN_CARD_NEXT_LEARNING_POINT + " ASC LIMIT " + limit;
        Cursor c = context.getContentResolver().query(FlashCardContract.FlashCardEntry.CONTENT_URI, projection, null, null, sortOrder);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int cardIDIndex = c.getColumnIndex(FlashCardContract.FlashCardEntry._ID);
                int wordIDIndex = c.getColumnIndex(FlashCardContract.FlashCardEntry.COLUMN_CARD_WORD_ID);
                int noteIndex = c.getColumnIndex(FlashCardContract.FlashCardEntry.COLUMN_CARD_NOTE);
                int efIndex = c.getColumnIndex(FlashCardContract.FlashCardEntry.COLUMN_CARD_EF);
                int nextLearningPointIndex = c.getColumnIndex(FlashCardContract.FlashCardEntry.COLUMN_CARD_NEXT_LEARNING_POINT);
                int consecutiveCorrectIndex = c.getColumnIndex(FlashCardContract.FlashCardEntry.COLUMN_CARD_CONSECUTIVE_CORRECT);
                flashCards.add(new FlashCard(c.getInt(cardIDIndex), c.getInt(wordIDIndex), c.getString(noteIndex),
                        c.getDouble(efIndex), c.getLong(nextLearningPointIndex), c.getInt(consecutiveCorrectIndex), context));
                c.moveToNext();
            }
            c.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        Collections.shuffle(flashCards);
        return flashCards;
    }

    public static int countWordNeedToBeReview(Context context) {
        int count = 0;
        DictionaryDBHelper.getInstance(context).open();
        String[] projection = { "Count (*) as WordNum "};
        String selection = "NextLearningPoint < ?";
        String[] selectionArgs = new String[]{String.valueOf(System.currentTimeMillis())};
        Cursor countCursor = context.getContentResolver().query(FlashCardContract.FlashCardEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        if (countCursor != null) {
            countCursor.moveToFirst();
            count = countCursor.getInt(0);
            countCursor.close();
        }
        DictionaryDBHelper.getInstance(context).close();
        return count;
    }
}
