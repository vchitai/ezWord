package com.ezword.ezword.ui.main_activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
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
    private WordMatchingFragment mWordMatchingFragment;
    private QuizQuestionFragment mQuizQuestionFragment;
    private ArrayList<FlashCard> mFlashCards;
    private EditText textAnswer;
    private int i;
    private TextView mTextCountDown;
    private CountDownTimer mCountDownTimer;
    private long mCountDownTime;
    private long timeUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mFragmentManager = getSupportFragmentManager();

        setUpQuestionFragment();
        mFlashCards = QuizGenerator.generateFlashCards(this, 100);
        i = -1;

        mNextQuestion = (Button)findViewById(R.id.quiz_next_question);
        setOnClickButtonListener();

        mTextCountDown = (TextView)findViewById(R.id.text_countdown_timer);
        mCountDownTime = 15;
        mTextCountDown.setText(String.valueOf(mCountDownTime));
    }

    private void startCountDown() {
        mTextCountDown.setText(String.valueOf(mCountDownTime));
        mCountDownTimer = new CountDownTimer(mCountDownTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextCountDown.setText(String.valueOf(millisUntilFinished / 1000));
                timeUsed = mCountDownTime - millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                mTextCountDown.setText("");
                mNextQuestion.performClick();
            }
        }.start();
    }

    private void setUpQuestionFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        mQuizQuestionFragment = new QuizQuestionFragment();

        fragmentTransaction.replace(R.id.quiz_question_container, mQuizQuestionFragment);
        fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.commit();
    }

    private void setUpWordMatchingAnswerFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        mWordMatchingFragment = new WordMatchingFragment();

        fragmentTransaction.replace(R.id.quiz_answer_container, mWordMatchingFragment);
        fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.commit();
    }

    public void setOnClickButtonListener() {
        mNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                    mCountDownTimer = null;
                }
                textAnswer = (EditText)findViewById(R.id.word_matching_answer);
                if (i == 0) {
                    setUpWordMatchingAnswerFragment();
                    mQuizQuestionFragment.updateQuizView(mFlashCards.get(i));
                    startCountDown();
                }
                if (i > 0) {
                    checkAnswer(String.valueOf(textAnswer.getText()), i - 1, timeUsed);
                    textAnswer.setText("");
                    if (i < mFlashCards.size()) {
                        mQuizQuestionFragment.updateQuizView(mFlashCards.get(i));
                        startCountDown();
                    }
                    else if (i == mFlashCards.size()) {
                        setResult(Activity.RESULT_OK, null);
                        finish();
                    }
                }
            }
        });
    }

    private void checkAnswer(String textAnswer, int num, long timeUsed) {
        Random rand = new Random();
        int answerQuality;
        if (textAnswer.equalsIgnoreCase(mFlashCards.get(num).getAnswer())) {
            if (timeUsed < mCountDownTime / 3)
                answerQuality = FlashCard.ANSWER_QUALITY_PERFECT;
            else if (timeUsed < mCountDownTime / 2)
                answerQuality = FlashCard.ANSWER_QUALITY_CORRECT_SLOW;
            else
                answerQuality = FlashCard.ANSWER_QUALITY_CORRECT_VERY_SLOW;
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }
        else {
            if (timeUsed < mCountDownTime / 2)
                answerQuality = FlashCard.ANSWER_QUALITY_INCORRECT;
            else
                answerQuality = FlashCard.ANSWER_QUALITY_BLACKOUT;
            answerQuality = rand.nextInt(2);
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
        mFlashCards.get(num).updateAfterAnswer(answerQuality, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        setResult(Activity.RESULT_OK, null);
        finish();
    }
}