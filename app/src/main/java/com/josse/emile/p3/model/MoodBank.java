package com.josse.emile.p3.model;

public class MoodBank {

    private Mood mMood;
    private int mMoodScreen;

    public MoodBank(){
        this.mMood = Mood.SUPER_HAPPY;
        this.mMoodScreen = 1;
    }

    public void setMood(Mood mood) {
        this.mMood = mood;
    }

    /*public void setMoodScreen(int moodScreen) {
        this.mMoodScreen = moodScreen;
    }*/

    public Mood getMood() {
        return this.mMood;
    }

    public void moveMoodScreen(boolean wayUp){
        int actualOrd =  mMood.ordinal();

        if (wayUp){
            actualOrd++;
        }else {
            actualOrd--;
        }

        if (actualOrd < 0){
            actualOrd = 0;
        }else if (actualOrd > Mood.values().length - 1){
            actualOrd = Mood.values().length - 1;
        }

        mMood = Mood.values()[actualOrd];
    }
}
