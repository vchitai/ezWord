package com.ezword.ezword.dictionary;

/**
 * Created by chita on 02/05/2018.
 */

public class Word {
    private String mEnglish;
    private String mType;
    private String mDefinition;
    private String mPhoneticSpelling;

    public Word() {

    }

    public Word(String wordEnglish, String type, String definition, String phoneticSpelling) {
        mEnglish = wordEnglish;
        mType = type;
        mDefinition = definition;
        mPhoneticSpelling = phoneticSpelling;
    }

    public String getData(String tag) {
        if (tag.equals("English")) {
            return mEnglish;
        }
        else if (tag.equals("Type")) {
            return mType;
        }
        else if (tag.equals("Definition")) {
            return mDefinition;
        }
        else if (tag.equals("PhoneticSpelling")) {
            return mPhoneticSpelling;
        }
        return null;
    };
}
