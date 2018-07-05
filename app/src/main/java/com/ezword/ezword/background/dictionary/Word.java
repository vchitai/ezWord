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

    public Word(Word word) {
        mWordID = word.mWordID;
        mEnglish = word.mEnglish;
        mType = word.mType;
        mDefinition = word.mDefinition;
        mPhoneticSpelling = word.mPhoneticSpelling;
    }

    public static final int WORD_ENGLISH = 0;
    public static final int WORD_TYPE = 1;
    public static final int WORD_DEFINITION = 2;
    public static final int WORD_DEFINITION_HTML = 4;
    public static final int WORD_PHONETIC = 3;
    public static final int WORD_PHONETIC_HTML = 5;

    public Word(int wordID, String wordEnglish, String type, String definition, String phoneticSpelling) {
        mWordID = wordID;
        mEnglish = wordEnglish;
        mType = type;
        mDefinition = definition;
        mPhoneticSpelling = phoneticSpelling;
        mDefinition = mDefinition.replace("||", "\n");
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
            case WORD_DEFINITION_HTML:
                return mDefinition.replace("(", "<br><br><font color=\"#ffb606\">(").replace(".)", ".)</font><br>").replaceFirst("<br><br>", "");
            case WORD_PHONETIC:
                return mPhoneticSpelling;
            case WORD_PHONETIC_HTML:
                return "/"+mPhoneticSpelling+"/";
        }
        return null;
    };
    public int getWordID() {
        return mWordID;
    }
}