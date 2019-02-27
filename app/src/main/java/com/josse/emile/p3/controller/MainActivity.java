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
import android.widget.Toast;
import com.josse.emile.p3.R;
import com.josse.emile.p3.model.DAO;
import com.josse.emile.p3.model.Mood;
import com.josse.emile.p3.model.MoodPojo;

import org.threeten.bp.LocalDateTime;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String SPLIT_CHAR = "T";
    GestureDetectorCompat mGestureDetector;
    Mood mMood ;
    DAO saveObj ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveObj = new DAO(this);
        //todo pauser ça dans le onResume
        if (saveObj.retrieveMoodPojo() == null){
            mMood = Mood.SUPER_HAPPY;
        }else{
            mMood = saveObj.retrieveMoodPojo().getDailyMood();
        }

        View layout = findViewById(R.id.main);

        mGestureDetector = new GestureDetectorCompat(this,this);
        final ImageView smiley = findViewById(R.id.main_iv_smiley);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        smiley.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, LocalDateTime.now().toString().split(SPLIT_CHAR)[0], Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        final ImageView note = findViewById(R.id.main_iv_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, LocalDateTime.now().toString().split(SPLIT_CHAR)[0], Toast.LENGTH_SHORT).show();

                final EditText input = new EditText(MainActivity.this);
                final MoodPojo dailyMood = saveObj.retrieveMoodPojo();
                if (!saveObj.firstDateExist()){
                    saveObj.saveFirstDate();
                }
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
        });

        final ImageView history = findViewById(R.id.main_iv_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
                Toast.makeText(MainActivity.this, "clicked history", Toast.LENGTH_SHORT).show();
            }
        });


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
        if (velocityY > 0){
            Toast.makeText(MainActivity.this, mMood.toString(), Toast.LENGTH_SHORT).show();
            mMood = mMood.moveMoodScreen(false);
            updateScreen(mMood);
            return true;
        }else if (velocityY < 0){
            Toast.makeText(MainActivity.this, mMood.toString(), Toast.LENGTH_SHORT).show();
            mMood =mMood.moveMoodScreen(true);
            updateScreen(mMood);
            return true;
        }
        updateScreen(mMood);
        final MoodPojo dailyMood = saveObj.retrieveMoodPojo();
        dailyMood.setDailyMood(mMood);
        saveObj.saveMood(dailyMood);


        return false;
    }

    public void updateScreen(@NonNull Mood mood){
        final ConstraintLayout root = findViewById(R.id.main);
        final ImageView smiley = findViewById(R.id.main_iv_smiley);

        root.setBackgroundColor(getResources().getColor(mood.getColorRes()));
        smiley.setImageResource(mood.getSmileyRes());

    }
}
