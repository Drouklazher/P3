package com.josse.emile.p3.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.josse.emile.p3.R;

public enum Mood {
    SUPER_HAPPY(R.color.banana_yellow, R.drawable.smiley_super_happy, R.string.super_happy, 1),
    HAPPY(R.color.light_sage, R.drawable.smiley_happy, R.string.happy, 0.82f),
    NORMAL(R.color.warm_grey, R.drawable.smiley_normal, R.string.normal, 0.64f),
    DISAPPOINTED(R.color.cornflower_blue_65, R.drawable.smiley_disappointed, R.string.disappointed, 0.46f),
    SAD(R.color.faded_red, R.drawable.smiley_sad, R.string.sad, 0.28f);
    @ColorRes
    int colorRes;
    @DrawableRes
    int smileyRes;
    float sizeRes;
    @StringRes
    int name;

    Mood(@ColorRes int colorRes, @DrawableRes int smileyRes, @StringRes int name, float sizeRes) {
        this.colorRes = colorRes;
        this.smileyRes = smileyRes;
        this.sizeRes = sizeRes;
        this.name = name;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }

    @StringRes
    public int getName() {
        return name;
    }

    @DrawableRes
    public int getSmileyRes() {
        return smileyRes;
    }

    public float getSizeRes() {
        return sizeRes;
    }

    public Mood moveMoodScreen(boolean wayUp) {
        int actualOrd = this.ordinal();

        if (wayUp) {
            actualOrd++;
        } else {
            actualOrd--;
        }

        if (actualOrd < 0) {
            actualOrd = 0;
        } else if (actualOrd > Mood.values().length - 1) {
            actualOrd = Mood.values().length - 1;
        }

        return (Mood.values()[actualOrd]);
    }

}
