package com.ezword.ezword.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezword.ezword.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

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
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        BarChart barChart = view.findViewById(R.id.review_bar_chart);
        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0, 1));
        entries.add(new BarEntry(1, 1));
        entries.add(new BarEntry(2, 3));
        entries.add(new BarEntry(3, 2));
        entries.add(new BarEntry(4, 1));
        entries.add(new BarEntry(5, 5));
        entries.add(new BarEntry(6, 4));
        BarDataSet dataSet = new BarDataSet(entries, "Discover Progress");
        BarData data = new BarData(dataSet);
        barChart.setData(data);

        barChart.invalidate();
        return view;
    }

}
