package com.ezword.ezword.background.dictionary;

/**
 * Created by chita on 02/05/2018.
 */

public class Word {
    private int mWordID;
    private String mEnglish;
    private String mType;
    private String mDefinition;
    private String mPhoneticSpelling;

    public Word() {

    }

    public static final int WORD_ENGLISH = 0;
    public static final int WORD_TYPE = 1;
    public static final int WORD_DEFINITION = 2;
    public static final int WORD_PHONETIC = 3;

    public Word(int wordID, String wordEnglish, String type, String definition, String phoneticSpelling) {
        mEnglish = wordEnglish;
        mType = type;
        mDefinition = definition;
        mPhoneticSpelling = phoneticSpelling;
    }

    public boolean equal(String s) {
        return mEnglish.equals(s);
    }
    public String getData(int tag) {
        switch (tag) {
            case WORD_ENGLISH:
                return mEnglish;
            case WORD_TYPE:
                return mType;
            case WORD_DEFINITION:
                return mDefinition;
            case WORD_PHONETIC:
                return mPhoneticSpelling;
        }
        return null;
    };

    public int getWordID() {
        return mWordID;
    }
}
