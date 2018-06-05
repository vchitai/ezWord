package com.ezword.ezword.user_profile;

/**
 * Created by chita on 02/05/2018.
 */

public class Settings {
    private int mReviewNum;
    private int mReviewLevel;
    private int mSoundLevel;
    private boolean mReminder;

    public void turnOnReminder() {
        mReminder = true;
    }

    public void turnOffReminder() {
        mReminder = false;
    }
}
