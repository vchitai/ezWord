package com.ezword.ezword.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.main_activities.SingleWordActivity;
import com.ezword.ezword.adapters.CustomSearchAutoCompleteAdapter;
import com.ezword.ezword.adapters.WordListAdapter;
import com.ezword.ezword.database.LocalData;
import com.ezword.ezword.database.TinyDB;
import com.ezword.ezword.dictionary.Dictionary;
import com.ezword.ezword.dictionary.Word;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupSearchArea(view);
        setupTodayWord(view);
        setupRecentlySearch(view);
        return view;
    }

    private void setupRecentlySearch(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.home_recycler_view);
        WordListAdapter wordListAdapter = new WordListAdapter(getContext(), WordListAdapter.TYPE_HISTORY);
        recyclerView.setAdapter(wordListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupTodayWord(View view) {
        TinyDB tinyDB = new TinyDB(getContext());
        final String todayWord = tinyDB.getString(LocalData.TODAY_WORD);
        Word todayWordW = Dictionary.getInstance().search(getContext(),todayWord);
        View cardView = view.findViewById(R.id.home_today_word);
        ((TextView)cardView.findViewById(R.id.word_item_word)).setText(todayWordW.getData(Word.WORD_ENGLISH));
        ((TextView)cardView.findViewById(R.id.word_item_type)).setText(todayWordW.getData(Word.WORD_TYPE));
        ((TextView)cardView.findViewById(R.id.word_item_def)).setText(todayWordW.getData(Word.WORD_DEFINITION));
        view.findViewById(R.id.home_today_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SingleWordActivity.class);
                intent.putExtra(SingleWordActivity.SEARCH_PHRASE, todayWord);
                startActivity(intent);
            }
        });
    }
    private void setupSearchArea(View view) {
        SearchView searchView = view.findViewById(R.id.home_search_view);

        setupSearchView(searchView);
        setupAutoCompleteSearchView(searchView);
    }

    private void setupSearchView(SearchView searchView) {
        EditText searchPlate = searchView.findViewById(R.id.search_src_text);
        searchPlate.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchPlate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void setupAutoCompleteSearchView(SearchView searchView) {

        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        CustomSearchAutoCompleteAdapter newsAdapter = new CustomSearchAutoCompleteAdapter(getContext(), android.R.layout.simple_dropdown_item_1line);
        searchAutoComplete.setAdapter(newsAdapter);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String searchPhrase = (String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(searchPhrase);
                Word w = Dictionary.getInstance().search(getContext(), searchPhrase);
                if (w!=null) {
                    LocalData.getInstance(getContext()).addHistory(w);
                    Intent intent = new Intent(getActivity(), SingleWordActivity.class);
                    intent.putExtra(SingleWordActivity.SEARCH_PHRASE, searchPhrase);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), R.string.IF_word_not_found, Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Word w = Dictionary.getInstance().search(getContext(), query);
                if (w!=null) {
                    LocalData.getInstance(getContext()).addHistory(w);
                    Intent intent = new Intent(getActivity(), SingleWordActivity.class);
                    intent.putExtra(SingleWordActivity.SEARCH_PHRASE, query);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), R.string.IF_word_not_found, Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



}
