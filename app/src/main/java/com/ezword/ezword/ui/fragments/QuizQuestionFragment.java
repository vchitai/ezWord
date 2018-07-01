package com.ezword.ezword.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezword.ezword.R;
import com.ezword.ezword.background.dictionary.FlashCard;

import org.w3c.dom.Text;

/**
 * Created by NVP1010 on 6/26/2018.
 */

public class QuizQuestionFragment extends android.support.v4.app.Fragment {
    public QuizQuestionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_question, container, false);
        TextView textView = (TextView)v.findViewById(R.id.text_review_question);
        textView.setText("Click Next Question Button To Begin Review Session");
        return v;
    }

    public void updateQuizViewQuestion(FlashCard flashCard) {
        View v = this.getView();
        if (v != null) {
            TextView textReviewQuestion = (TextView) v.findViewById(R.id.text_review_question);
            textReviewQuestion.setText(flashCard.getQuestion());
        }
    }

    public void updateQuizViewAnswer(FlashCard flashCard) {
        View v = this.getView();
        String definition = "Definition: " + flashCard.getQuestion();
        String note = "Note: " + flashCard.getNote();
        if (v != null) {
            TextView textReviewAnswer = (TextView) v.findViewById(R.id.text_review_question);
            textReviewAnswer.setText(definition + "\n" + note);
        }
    }
}