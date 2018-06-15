package com.ezword.ezword.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.ezword.ezword.dictionary.Dictionary;

import java.util.ArrayList;

/**
 * Created by chita on 15/06/2018.
 */

public class CustomSearchAutoCompleteAdapter extends ArrayAdapter<String> {
    private ArrayList<String> mData = new ArrayList<>();
    public CustomSearchAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId, new ArrayList<String>());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return wordFilter;
    }

    private Filter wordFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return (String) resultValue;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                ArrayList<String> suggestions = Dictionary.getInstance().getRecommendations(getContext(), constraint.toString(), 5);
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results != null && results.count > 0) {
                mData = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
            else {
                mData.clear();
                notifyDataSetInvalidated();
            }
        }
    };
}
