package com.ezword.ezword.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezword.ezword.R;
import com.ezword.ezword.database.LocalData;
import com.ezword.ezword.dictionary.Word;

import java.util.ArrayList;

/**
 * Created by chita on 03/06/2018.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private ArrayList<Word> recentWord = null;
    public final static int TYPE_HISTORY = 1;
    public final static int TYPE_BOOKMARK = 2;
    private int mType;

    public WordListAdapter(Context context, int type) {
        super();
        switch (type) {
            case TYPE_HISTORY:
                recentWord = LocalData.getInstance(context).getHistoryW();
                break;
            case TYPE_BOOKMARK:
                recentWord = LocalData.getInstance(context).getBookmarkW();
                break;
            default:
                break;
        }
    }

    public void refreshData() {
        switch (mType) {
            case TYPE_HISTORY:
                recentWord = LocalData.getInstance(null).getHistoryW();
                break;
            case TYPE_BOOKMARK:
                recentWord = LocalData.getInstance(null).getBookmarkW();
                break;
            default:
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.word_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word currentWord = recentWord.get(position);

        holder.mWordWord.setText(currentWord.getData(Word.WORD_ENGLISH));
        holder.mWordType.setText(currentWord.getData(Word.WORD_TYPE));
        holder.mWordDefinition.setText(currentWord.getData(Word.WORD_DEFINITION));
    }

    @Override
    public int getItemCount() {
        return recentWord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mWordWord;
        public TextView mWordType;
        public TextView mWordDefinition;
        public View mWordView;

        public ViewHolder(View view) {
            super(view);
            mWordWord = view.findViewById(R.id.word_item_word);
            mWordType = view.findViewById(R.id.word_item_type);
            mWordDefinition = view.findViewById(R.id.word_item_def);
            mWordView = view;
        }
    }
}
