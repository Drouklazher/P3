package com.josse.emile.p3.model;

public class MoodPojo {
    private Mood dailyMood;
    private String message;

    public void setDailyMood(Mood dailyMood) {
        this.dailyMood = dailyMood;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MoodPojo(Mood dailyMood, String message) {
        this.dailyMood = dailyMood;
        this.message = message;
    }

    public Mood getDailyMood() {
        return dailyMood;
    }

    public String getMessage() {
        return message;
    }


}
