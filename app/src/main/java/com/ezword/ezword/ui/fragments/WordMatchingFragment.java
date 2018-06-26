package com.ezword.ezword.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezword.ezword.R;

/**
 * Created by NVP1010 on 6/26/2018.
 */

public class WordMatchingFragment extends android.support.v4.app.Fragment {
    public WordMatchingFragment() {
        //Nothing
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View wordMatchingView = inflater.inflate(R.layout.fragment_word_matching, container, false);
        return wordMatchingView;
    }
}