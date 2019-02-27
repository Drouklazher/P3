package com.josse.emile.p3.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    private SharedPreferences mSharedPreferences;

    public DAO(Context context){
        mSharedPreferences = context.getSharedPreferences("SavedMood", Context.MODE_PRIVATE);
    }

    public void saveFirstDate(){
        mSharedPreferences.edit().putString("firstDate","" + LocalDate.now().getYear() + LocalDate.now().getDayOfYear()).apply();
    }

    public boolean firstDateExist(){
        return (mSharedPreferences.contains("firstDate"));
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

    public List<MoodPojo> retrieveWeekly(){
        List<MoodPojo> weeklyMood = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            weeklyMood.add(retrieveMoodPojo(i));
        }
        return weeklyMood;
    }
}
