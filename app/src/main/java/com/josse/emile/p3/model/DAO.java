package com.josse.emile.p3.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.threeten.bp.LocalDate;

public class DAO {
    private SharedPreferences mSharedPreferences;

    public DAO(Context context){
        mSharedPreferences = context.getSharedPreferences("SavedMood", Context.MODE_PRIVATE);
    }

    public void saveMood(MoodPojo mood){
        final Gson gson = new Gson();
        String moodPojoJsoon = gson.toJson(mood);
        mSharedPreferences.edit().putString("" + LocalDate.now().getYear() + LocalDate.now().getDayOfYear(), moodPojoJsoon).apply();
    }
    public MoodPojo retrieveMoodPojo(){
        return retrieveMoodPojo(0);
    }
    public MoodPojo retrieveMoodPojo(int nbDays){

        String moodPojoJson = mSharedPreferences.getString("" + LocalDate.now().minusDays(nbDays).getYear() +LocalDate.now().minusDays(nbDays).getDayOfYear(), null);
        return new Gson().fromJson(moodPojoJson,MoodPojo.class);
    }

}
