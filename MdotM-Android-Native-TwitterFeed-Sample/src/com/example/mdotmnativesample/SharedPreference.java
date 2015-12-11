package com.example.mdotmnativesample;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SharedPreference {
 
    public static final String PREFS_NAME = "FEED_RESPONSE";
    public static final String PREFS_KEY = "PREF_KEY";
    
    public SharedPreference() {
        super();
    }
 
    public void saveFeedResponse(Context context, boolean flag) {
        SharedPreferences settings;
        Editor editor;
        
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
 
        editor.putBoolean(PREFS_KEY, flag); //3
 
        editor.commit(); //4
    }
 
    public boolean getFeedResponse(Context context) {
        SharedPreferences settings;
        boolean flag;
 
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        flag = settings.getBoolean(PREFS_KEY, false);
        return flag;
    }
       
}
