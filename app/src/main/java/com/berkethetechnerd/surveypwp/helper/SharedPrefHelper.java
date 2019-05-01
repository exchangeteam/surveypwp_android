package com.berkethetechnerd.surveypwp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class contains shortcut functions for shared preferences utilities.
 */
public class SharedPrefHelper {

    // Database table name used in shared preferences.
    private static final String PREFS_NAME = "com.berkethetechnerd.surveypwp";
    private static final String NUM_QUESTIONNAIRE = "num_questionnaire";

    public static int getNumQuestionnaire(Context context) {
        return getIntegerValue(context, NUM_QUESTIONNAIRE);
    }

    public static void setNumQuestionnaire(Context context, int value) {
        setIntegerValue(context, NUM_QUESTIONNAIRE, value);
    }

    private static int getIntegerValue(Context context, String key) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    private static void setIntegerValue(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}