package com.ezword.ezword.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezword.ezword.R;
import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.quiz.QuizGenerator;
import com.ezword.ezword.ui.main_activities.QuizActivity;
import com.ezword.ezword.ui.main_activities.ReviseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<Long> timePoints = LocalData.getInstance(getContext()).getTimePoint();
        Integer[] wordOfDay = {0, 0, 0, 0, 0, 0, 0};
        Long[] weekDayPoints = new Long[8];
        weekDayPoints[7] = System.currentTimeMillis();
        for (int i = 6; i >= 0; i--) {
            weekDayPoints[i] = weekDayPoints[i + 1] - 24 * 60 * 60 * 1000;
        }
        for (int i = timePoints.size() - 1; i >= 0; i--) {
            for (int j = 6; j >= 0; j--) {
                if (timePoints.get(i) >= weekDayPoints[j] && timePoints.get(i) < weekDayPoints[j + 1]) {
                    wordOfDay[j]++;
                    break;
                }
            }
        }

        View view = inflater.inflate(R.layout.fragment_review, container, false);
        BarChart barChart = view.findViewById(R.id.review_bar_chart);
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, wordOfDay[i]));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Words Learned");
        BarData data = new BarData(dataSet);
        barChart.setData(data);

        barChart.invalidate();

        TextView textReviewWelcome = view.findViewById(R.id.review_welcome_text);
        int numWordNeedToBeReview = QuizGenerator.countWordNeedToBeReview(getContext());
        textReviewWelcome.setText("You may need to review " + numWordNeedToBeReview + " words!");

        view.findViewById(R.id.review_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            updateWordNeedToBeReview();
        }
    }

    public void updateWordNeedToBeReview() {
        View v = getView();
        TextView textReviewWelcome = v.findViewById(R.id.review_welcome_text);
        int numWordNeedToBeReview = QuizGenerator.countWordNeedToBeReview(getContext());
        textReviewWelcome.setText("You may need to review " + numWordNeedToBeReview + " words!");
    }
}
