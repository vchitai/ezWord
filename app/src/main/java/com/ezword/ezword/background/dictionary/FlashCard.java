package com.ezword.ezword.background.dictionary;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import com.ezword.ezword.background.database.DictionaryDBHelper;
import com.ezword.ezword.background.database.FlashCardContract;

/**
 * Created by NVP1010 on 6/26/2018.
 */

public class FlashCard {
    private Word mWord;
    private int mCardID;
    private String mNote;
    private String mMemoPath;
    private Double mEF;
    private long mNextLearningPoint;
    private int mConsecutiveCorrect;

    public static final int ANSWER_QUALITY_BLACKOUT = 0;
    public static final int ANSWER_QUALITY_INCORRECT = 1;
    public static final int ANSWER_QUALITY_ALMOST_CORRECT = 2;
    public static final int ANSWER_QUALITY_CORRECT_VERY_SLOW = 3;
    public static final int ANSWER_QUALITY_CORRECT_SLOW = 4;
    public static final int ANSWER_QUALITY_PERFECT = 5;

    private final int FIRST_EF = 1;
    private final int SECOND_EF = 6;

    public FlashCard() {

    }

    public FlashCard(int cardID, int wordID, String note, double ef, long nextLearningPoint, int consecutiveCorrect, Context context) {
        mCardID = cardID;
        mNote = note;
        mEF = ef;
        mNextLearningPoint = nextLearningPoint;
        mConsecutiveCorrect = consecutiveCorrect;
        mWord = Dictionary.getInstance().getWordById(context, wordID);
    }

    public void updateEF(int answerQuality, Context context) {
        mEF = mEF + (0.1 - (5 - answerQuality) * (0.08 + (5 - answerQuality)* 0.02));
        this.updateEFInDatabase(context);
    }

    private void updateEFInDatabase(Context context) {
        DictionaryDBHelper.getInstance(context).open();
        ContentValues updateValues = new ContentValues();
        updateValues.put(FlashCardContract.FlashCardEntry.COLUMN_CARD_EF, mEF);
        context.getContentResolver().update(ContentUris.withAppendedId(FlashCardContract.FlashCardEntry.CONTENT_URI, mCardID),
                updateValues, null, null);
        DictionaryDBHelper.getInstance(context).close();
    }

    public void updateNextLearningPoint (Context context) {
        long currentTime = System.currentTimeMillis();
        long interval;
        if (mConsecutiveCorrect == 1) {
            interval = FIRST_EF * 1000 * 60 * 60;
        }
        else if (mConsecutiveCorrect == 2) {
            interval = SECOND_EF * 1000 * 60 * 60;
        }
        else {
            interval = (int)(Math.pow(mEF, mConsecutiveCorrect * 1.0 - 2) * SECOND_EF) * 1000 * 60 * 60;
        }
        mNextLearningPoint = currentTime + interval;
        updateDBNextLearningPoint(context);
    }

    private void updateDBNextLearningPoint(Context context) {
        DictionaryDBHelper.getInstance(context).open();
        ContentValues updateValues = new ContentValues();
        updateValues.put(FlashCardContract.FlashCardEntry.COLUMN_CARD_NEXT_LEARNING_POINT, mNextLearningPoint);
        context.getContentResolver().update(ContentUris.withAppendedId(FlashCardContract.FlashCardEntry.CONTENT_URI, mCardID),
                updateValues, null, null);
        DictionaryDBHelper.getInstance(context).close();
    }

    public void updateConsecutiveCorrect(int answerQuality, Context context) {
        if (answerQuality >= ANSWER_QUALITY_CORRECT_SLOW)
            mConsecutiveCorrect++;
        updateConsecutiveCorrectInDB(context);
    }

    private void updateConsecutiveCorrectInDB(Context context) {
        DictionaryDBHelper.getInstance(context).open();
        ContentValues updateValues = new ContentValues();
        updateValues.put(FlashCardContract.FlashCardEntry.COLUMN_CARD_CONSECUTIVE_CORRECT, mConsecutiveCorrect);
        context.getContentResolver().update(ContentUris.withAppendedId(FlashCardContract.FlashCardEntry.CONTENT_URI, mCardID),
                updateValues, null, null);
        DictionaryDBHelper.getInstance(context).close();
    }

    public void updateAfterAnswer(int answerQuality, Context context) {
        updateConsecutiveCorrect(answerQuality, context);
        updateEF(answerQuality, context);
        updateNextLearningPoint(context);
    }

    public String getQuestion() {
        return mWord.getData(Word.WORD_DEFINITION);
    }

    public String getAnswer() {
        return mWord.getData(Word.WORD_ENGLISH);
    }

    public String getNote() {
        return mNote;
    }

    public String getMemoPath() {
        return mMemoPath;
    }

    public Double getEF() {
        return mEF;
    }

    public long getNextLearningPoint() {
        return mNextLearningPoint;
    }

    public FlashCard(Word word) {
        mWord = word;
    }

}