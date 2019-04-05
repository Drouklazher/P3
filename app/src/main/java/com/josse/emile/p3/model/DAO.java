package com.josse.emile.p3.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.threeten.bp.LocalDate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DAO {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences firstDatePreferences;

    private static final String KEY_SHARED_PREFERENCES_FIRSTDATE = "KEY_SHARED_PREFERENCES_FIRSTDATE";
    private static  final String KEY_FIRSTDATE = "KEY_FIRSTDATE";
    private static final String KEY_SHARED_PREFERENCES = "SavedMood";

    public DAO(Context context){
        mSharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        firstDatePreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_FIRSTDATE, Context.MODE_PRIVATE);
    }

    public void saveFirstDate(){
        firstDatePreferences.edit().putString(KEY_FIRSTDATE,"" + LocalDate.now().getYear() + LocalDate.now().getDayOfYear()).apply();
    }


    public boolean firstDateExist(){
        return (firstDatePreferences.contains(KEY_FIRSTDATE));
    }

    private String retrieveFirstDate(){
        if (firstDateExist()) {
            return firstDatePreferences.getString(KEY_FIRSTDATE, null);
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

    private MoodPojo retrieveMoodPojo(int nbDays){

        String moodPojoJson = mSharedPreferences.getString("" + LocalDate.now().minusDays(nbDays).getYear() +LocalDate.now().minusDays(nbDays).getDayOfYear(), null);
        return new Gson().fromJson(moodPojoJson,MoodPojo.class);
    }

    //this method return a treemap to return the mood linked with the day difference compared to today
    public TreeMap<Integer,MoodPojo> retrieveSevenLastMoods(){
        //this comparator can reverse the order of the moods in history
        TreeMap<Integer,MoodPojo> sevenLastMood = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        String firstDate =  retrieveFirstDate();
        String currentDate = "" + LocalDate.now().getYear() + LocalDate.now().getDayOfYear();
        int i = 1;

        while(!(currentDate.equals(firstDate))&&(sevenLastMood.size()<7 )){
            currentDate = "" + LocalDate.now().minusDays(i).getYear() + LocalDate.now().minusDays(i).getDayOfYear();
            if (mSharedPreferences.contains(currentDate)){
                sevenLastMood.put(i,retrieveMoodPojo(i));
            }
            i++;
        }
        return sevenLastMood;

    }

    //this method return all the moods used and their proportions
    public HashMap<Mood,Integer> retieveAllProportionMap(){
        Map<String,?> allMood = mSharedPreferences.getAll();
        HashMap<Mood,Integer> MoodMap = new HashMap<>();
        Mood iterateMood;
        for (Map.Entry entry: allMood.entrySet()){
            iterateMood = (new Gson().fromJson((String)entry.getValue(),MoodPojo.class)).getDailyMood();
            Integer moodCount = MoodMap.get(iterateMood);
            if (moodCount == null){
                moodCount = 0;
            }
            MoodMap.put(iterateMood,moodCount+1);
        }
        return MoodMap;
    }
}
