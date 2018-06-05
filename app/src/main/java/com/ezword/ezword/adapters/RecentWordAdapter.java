package com.ezword.ezword.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezword.ezword.R;
import com.ezword.ezword.dictionary.Word;

import java.util.ArrayList;

/**
 * Created by chita on 03/06/2018.
 */

public class RecentWordAdapter extends RecyclerView.Adapter<RecentWordAdapter.ViewHolder> {
    ArrayList<Word> recentWord = null;

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
        return 0;
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
