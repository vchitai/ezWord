package com.ezword.ezword.ui.main_activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.background.dictionary.FlashCard;
import com.ezword.ezword.background.quiz.QuizGenerator;
import com.ezword.ezword.ui.fragments.QuizQuestionFragment;
import com.ezword.ezword.ui.fragments.WordMatchingFragment;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private FrameLayout mAnswerContainer;
    private FrameLayout mQuestionContainer;
    //private QuizGenerator mQuizGenerator;
    private Button mNextQuestion;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private WordMatchingFragment mWordMatchingFragment;
    private QuizQuestionFragment mQuizQuestionFragment;
    private ArrayList<FlashCard> mFlashCards;
    private EditText textAnswer;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setUpFragment();
        mFlashCards = QuizGenerator.generateFlashCards(this, 100);
        i = -1;

        mNextQuestion = (Button)findViewById(R.id.quiz_next_question);
        setOnClickButtonListener();
    }

    private void setUpFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mWordMatchingFragment = new WordMatchingFragment();
        mQuizQuestionFragment = new QuizQuestionFragment();

        mFragmentTransaction.replace(R.id.quiz_question_container, mQuizQuestionFragment);
        mFragmentTransaction.addToBackStack(mQuizQuestionFragment.toString());
        mFragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        mFragmentTransaction.replace(R.id.quiz_answer_container, mWordMatchingFragment);
        mFragmentTransaction.addToBackStack(mWordMatchingFragment.toString());
        mFragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        mFragmentTransaction.commit();
    }

    public void setOnClickButtonListener() {
        mNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textAnswer = (EditText) findViewById(R.id.word_matching_answer);
                if (i >= mFlashCards.size()) {
                    return;
                }
                if (i >= 0) {
                    checkAnswer(String.valueOf(textAnswer.getText()), mFlashCards.get(i).getAnswer());
                }
                i++;
                if (i >= mFlashCards.size()) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                    return;
                }
                mQuizQuestionFragment.updateQuizView(mFlashCards.get(i));
                textAnswer.setText("");
            }
        });
    }

    private void checkAnswer(String textAnswer, String answer) {
        Random rand = new Random();
        int answerQuality;
        if (textAnswer.equalsIgnoreCase(answer)) {
            answerQuality = rand.nextInt(5 - 3) + 2;
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }
        else {
            answerQuality = rand.nextInt(2);
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
        mFlashCards.get(i).updateAfterAnswer(answerQuality, this);

    }
}