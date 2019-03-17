package com.josse.emile.p3.controller;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.josse.emile.p3.R;
import com.josse.emile.p3.model.DAO;
import com.josse.emile.p3.model.MoodPojo;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ConstraintLayout mRoot;
    private DAO mDAO ;
    private List<String> mMoodMessageList;
    private List<Integer> mDaysDifList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRoot = findViewById(R.id.history_cl_main);
        mDAO = new DAO(this);
        mMoodMessageList = new ArrayList<>();
        mDaysDifList = new ArrayList<>();
        mDaysDifList = mDAO.retrieveSevenLastMoodsDayDif();
        updateHistory(mDAO.retrieveSevenLastMoods());
        final Button mButtonBack = findViewById(R.id.history_but_back);
        final Button mButtonFullHistory = findViewById(R.id.history_but_fullHistory);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent( HistoryActivity.this,MainActivity.class);
                startActivity(mainActivity);
            }
        });
        mButtonFullHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullHistoryActivity = new Intent( HistoryActivity.this,FullHistoryActivity.class);
                startActivity(fullHistoryActivity);
            }
        });

    }

    private void updateHistory(List<MoodPojo> sevenMoods){
        for (int i = 0; i < mRoot.getChildCount() - 2 && i < sevenMoods.size(); i++) {//-2 because we don't want to iterate on the buttonw wich are also childs of the constraint layout

            MoodPojo moodPojo;
            AppCompatTextView textView = (AppCompatTextView)mRoot.getChildAt(i);
            moodPojo = sevenMoods.get(i);
            mMoodMessageList.add(moodPojo.getMessage());
            displayMood(moodPojo, textView, i, mDaysDifList.get(i));

        }
    }

    private void displayMood(MoodPojo mood, TextView moodLine,final int messageIndex, int texNbDays){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mRoot);
            moodLine.setBackgroundColor( getResources().getColor(mood.getDailyMood().getColorRes()));
            constraintSet.constrainPercentWidth(moodLine.getId(),mood.getDailyMood().getSizeRes());
            moodLine.setText(String.format(getResources().getQuantityString(R.plurals.numbers_days,texNbDays),texNbDays));
        if (mood.hasMessage()){
            moodLine.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(moodLine.getContext(),R.drawable.ic_comment_black_48px),null);
            moodLine.setClickable(true);
            moodLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HistoryActivity.this, mMoodMessageList.get(messageIndex),Toast.LENGTH_SHORT).show();
                }
            });
        }

        constraintSet.applyTo(mRoot);
    }
}