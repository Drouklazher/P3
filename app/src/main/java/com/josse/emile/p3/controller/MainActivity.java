package com.josse.emile.p3.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.josse.emile.p3.R;
import com.josse.emile.p3.model.DAO;
import com.josse.emile.p3.model.Mood;
import com.josse.emile.p3.model.MoodPojo;



public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String SPLIT_CHAR = "T";
    GestureDetectorCompat mGestureDetector;
    Mood mMood;
    DAO saveObj;
    private ConstraintLayout mRoot;
    private ImageView mSmiley;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot = findViewById(R.id.main);
        mSmiley = findViewById(R.id.main_iv_smiley);
        saveObj = new DAO(this);
        if (!saveObj.firstDateExist()) {
            saveObj.saveFirstDate();
        }

        mGestureDetector = new GestureDetectorCompat(this, this);
        mRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        mSmiley.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        final ImageView note = findViewById(R.id.main_iv_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final MoodPojo dailyMood = saveObj.retrieveMoodPojo();
                final EditText input = new EditText(MainActivity.this);
                input.setText(dailyMood.getMessage());

                displayAlertdialogMessage(dailyMood);

            }
        });

        final ImageView history = findViewById(R.id.main_iv_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });


    }

    private void displayAlertdialogMessage( final MoodPojo dailyMood) {
        final EditText input = new EditText(MainActivity.this);
        input.setText(dailyMood.getMessage());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setMessage(R.string.comment_confirmation_message);
        alertDialogBuilder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dailyMood.setMessage(input.getText().toString());
                        saveObj.saveMood(dailyMood);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (saveObj.retrieveMoodPojo() == null) {
            mMood = Mood.SUPER_HAPPY;
            saveObj.saveMood(new MoodPojo(mMood,""));
        } else {
            mMood = saveObj.retrieveMoodPojo().getDailyMood();
        }
        updateScreen(mMood);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent previousEvent, MotionEvent currentEvent, float velocityX, float velocityY) {
        if (velocityY > 0) {
            mMood = mMood.moveMoodScreen(false);
            updateScreen(mMood);
        } else if (velocityY < 0) {
            mMood = mMood.moveMoodScreen(true);
            updateScreen(mMood);
        }
        final MoodPojo dailyMood = new MoodPojo(mMood,"");
        if(saveObj.retrieveMoodPojo().hasMessage()){
            dailyMood.setMessage(saveObj.retrieveMoodPojo().getMessage());
        }
        saveObj.saveMood(dailyMood);
        return true;
    }

    public void updateScreen(@NonNull Mood mood) {
        mRoot.setBackgroundColor(getResources().getColor(mood.getColorRes()));
        mSmiley.setImageResource(mood.getSmileyRes());
    }
}
