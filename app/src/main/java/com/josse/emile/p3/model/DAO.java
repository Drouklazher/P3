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

    public String retrieveFirstDate(){
        if (firstDateExist()) {
            return mSharedPreferences.getString("firstDate", null);
        }else{
            return "" + LocalDate.now().getYear() + LocalDate.now().getDayOfYear();
        }
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

    public List<MoodPojo> retrieveSevenLastMoods(){
        List<MoodPojo> sevenLastMood = new ArrayList<>();
        String firstDate =  retrieveFirstDate();
        String currentDate = "" + LocalDate.now().getYear() + LocalDate.now().getDayOfYear();
        int i = 0;

        while(!(currentDate.equals(firstDate))&&(sevenLastMood.size()<6 )){
            currentDate = "" + LocalDate.now().minusDays(i).getYear() +LocalDate.now().minusDays(i).getDayOfYear();
            if (mSharedPreferences.contains(currentDate)){
                sevenLastMood.add(retrieveMoodPojo(i));
            }
            i++;
        }
        return sevenLastMood;
    }
}
