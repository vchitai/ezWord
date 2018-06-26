package com.ezword.ezword.background.database;

import android.content.Context;

import com.ezword.ezword.background.dictionary.Dictionary;
import com.ezword.ezword.background.dictionary.Word;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by chita on 05/06/2018.
 */

public class LocalData {
    public static final String TODAY_WORD = "todayWord";
    private static final String HISTORY = "history";
    private static final String BOOKMARK = "bookmark";
    private static final String LOGIN_TOKEN = "login_token";
    private static LocalData mInstance;
    private TinyDB mTinyDB;
    private Set<String> mHistory;
    private Set<String> mBookmark;
    private ArrayList<Word> mHistoryW;
    private ArrayList<Word> mBookmarkW;

    private LocalData(Context context) {
        mTinyDB = new TinyDB(context);
        mHistory = new LinkedHashSet<>(mTinyDB.getListString(HISTORY));
        mHistoryW = new ArrayList<>();
        for (String s: mHistory)
            mHistoryW.add(Dictionary.getInstance().search(context, s));
        mBookmarkW = new ArrayList<>();
        mBookmark = new LinkedHashSet<>(mTinyDB.getListString(BOOKMARK));
        for (String s: mBookmark)
            mBookmarkW.add(Dictionary.getInstance().search(context, s));
    }

    public static LocalData getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocalData(context);
        }
        return mInstance;
    }

    public Set<String> getHistory() {
        return mHistory;
    }

    public Set<String> getBookmark() {
        return mHistory;
    }

    public ArrayList<Word> getHistoryW() {
        return mHistoryW;
    }

    public ArrayList<Word> getBookmarkW() {
        return mBookmarkW;
    }

    public Word getLastLookUp() {
        if (mHistoryW.size() > 0) {
            return mHistoryW.get(mHistoryW.size() - 1);
        }
        return null;
    }
    public void addHistory(Word w) {
        if (w!=null) {
            mHistoryW.remove(w);
            mHistory.remove(w.getData(Word.WORD_ENGLISH));
            mHistoryW.add(w);
            mHistory.add(w.getData(Word.WORD_ENGLISH));
            mTinyDB.putListString(HISTORY, new ArrayList<>(mHistory));
        }
    }

    public void addHistory(String s) {
        if (mHistory.add(s)) {
            mHistoryW.add(Dictionary.getInstance().search(null, s));
            mTinyDB.putListString(HISTORY, new ArrayList<>(mHistory));
        }
    }

    public boolean removeHistory(String s) {
        boolean res =  mHistory.remove(s);
        if (res) {
            mTinyDB.putListString(HISTORY, new ArrayList<>(mHistory));
            for (Word w: mHistoryW) {
                if (w.equal(s))
                    mHistoryW.remove(w);
            }
        }
        return res;
    }


    public boolean addBookmark(Context context, Word w) {
        boolean res = mBookmark.add(w.getData(Word.WORD_ENGLISH));
        if (res) {
            mBookmarkW.add(w);
            Dictionary.getInstance().addFlashCardToDatabase(context, w.getWordID());
            mTinyDB.putListString(HISTORY, new ArrayList<>(mBookmark));
        }
        return res;
    }

    public boolean addBookmark(String s) {
        boolean res = mBookmark.add(s);
        if (res) {
            mBookmarkW.add(Dictionary.getInstance().search(null, s));
            mTinyDB.putListString(HISTORY, new ArrayList<>(mBookmark));
        }
        return res;
    }

    public boolean removeBookmark(String s) {
        boolean res =  mBookmark.remove(s);
        if (res)
            mTinyDB.putListString(HISTORY, new ArrayList<>(mHistory));
        return res;
    }

    public void storeToken(String token) {
        mTinyDB.putString(LOGIN_TOKEN, token);
    }

    public String getToken() {
        return mTinyDB.getString(LOGIN_TOKEN);
    }

    public boolean checkSynced(String username) {
        return Objects.equals(mTinyDB.getString("synced-" + username), "synced");
    }

    public void registerSynced(String username) {
        mTinyDB.putString("synced-"+username, "synced");
    }

    public void registerLastSignIn(String username) {
        mTinyDB.putString("lastSignIn", username);
    }

    public String getLastSignIn() {
        return mTinyDB.getString("lastSignIn");
    }

    public boolean getDailyWordEnable() { return mTinyDB.getBoolean("daily-word-enable");}

    public void setDailyWordEnable(boolean dailyWordEnable) {
        mTinyDB.putBoolean("daily-word-enable", dailyWordEnable);
    }
}
