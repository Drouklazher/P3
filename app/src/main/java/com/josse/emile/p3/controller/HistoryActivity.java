package com.josse.emile.p3.controller;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.josse.emile.p3.R;
import com.josse.emile.p3.model.MoodPojo;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ConstraintLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRoot = findViewById(R.id.history_cl_main);
    }

    private void updateHistory(List<MoodPojo> sevenMoods){
        for (int i = 0; i < mRoot.getChildCount() - 1 ; i++) {
            AppCompatTextView textView = (AppCompatTextView)mRoot.getChildAt(i);
            MoodPojo moodPojo;
            if(i < sevenMoods.size()){
                moodPojo = sevenMoods.get(i);
            }else{
                moodPojo = null;
            }
            displayMood(moodPojo,textView);
        }
    }

    private void displayMood(@Nullable MoodPojo mood, TextView moodLine){
        if (mood != null){
            moodLine.setBackgroundColor( getResources().getColor(mood.getDailyMood().getColorRes()));
        }else{
            moodLine.setBackgroundColor(getResources().getColor(R.color.violet_purple));
        }
    }
}
