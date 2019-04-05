package com.josse.emile.p3.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.josse.emile.p3.R;
import com.josse.emile.p3.model.DAO;
import com.josse.emile.p3.model.MoodPojo;

import java.util.Map;
import java.util.TreeMap;

public class HistoryActivity extends AppCompatActivity {
    private ConstraintLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRoot = findViewById(R.id.history_cl_main);
        DAO mDAO = new DAO(this);
        TreeMap<Integer,MoodPojo> sevenLastMoods = mDAO.retrieveSevenLastMoods();
        updateHistory(sevenLastMoods);
        final Button mButtonBack = findViewById(R.id.history_but_back);
        final Button mButtonFullHistory = findViewById(R.id.history_but_fullHistory);
        //the back button is here just to allow the user without extra button to go back
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

    //the moods are display in the order of the treeMap
    private void updateHistory(TreeMap<Integer,MoodPojo> sevenMoods){
        int i = 0;
        for (Map.Entry<Integer,MoodPojo> entry:sevenMoods.entrySet()){
            if (i >= mRoot.getChildCount()-2){
                break;
            }
            AppCompatTextView textView = (AppCompatTextView)mRoot.getChildAt(i);
            displayMood(entry.getValue(),textView,entry.getKey());
            i++;
        }
    }

    private void displayMood(final MoodPojo mood, TextView moodLine, int texNbDays){
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
                    Toast.makeText(HistoryActivity.this, mood.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        constraintSet.applyTo(mRoot);
    }
}