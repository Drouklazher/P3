package com.josse.emile.p3.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.josse.emile.p3.R;
import com.josse.emile.p3.model.DAO;
import com.josse.emile.p3.model.MoodPojo;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ConstraintLayout mRoot;
    private DAO mDAO ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRoot = findViewById(R.id.history_cl_main);
        mDAO = new DAO(this);
        updateHistory(mDAO.retrieveSevenLastMoods());
    }

    private void updateHistory(List<MoodPojo> sevenMoods){
        for (int i = 0; i < mRoot.getChildCount() - 1 && i < sevenMoods.size(); i++) {//-1 because we don't want to iterate on the button wich is also a child of the constraint layout
            MoodPojo moodPojo;
                AppCompatTextView textView = (AppCompatTextView)mRoot.getChildAt(i);
                    moodPojo = sevenMoods.get(i);

                displayMood(moodPojo, textView);

        }
    }

    private void displayMood(@NonNull MoodPojo mood, TextView moodLine){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mRoot);
        if (mood.hasMessage()){
            moodLine.setCompoundDrawables(null,null, ContextCompat.getDrawable(moodLine.getContext(),R.drawable.ic_comment_black_48px),null);
        }
            moodLine.setBackgroundColor( getResources().getColor(mood.getDailyMood().getColorRes()));
            constraintSet.constrainPercentWidth(moodLine.getId(),mood.getDailyMood().getSizeRes());

        constraintSet.applyTo(mRoot);
    }
}