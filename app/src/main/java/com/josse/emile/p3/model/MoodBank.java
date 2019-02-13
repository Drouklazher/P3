package com.josse.emile.p3.model;

public class MoodBank {

    private Mood mMood;

    public MoodBank(){
        this.mMood = Mood.SUPER_HAPPY;
    }

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
