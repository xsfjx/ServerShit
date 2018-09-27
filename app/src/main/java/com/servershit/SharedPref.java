package com.servershit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {

    /****************************   TOOLS   *************************/

    private static SharedPreferences.Editor getEditor() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
        return pref.edit();
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
    }

    /****************************   GETTER/SETTER   *************************/

    public static void setFName (String fName){
        SharedPreferences.Editor editor = getEditor();
        editor.putString(Tags.F_NAME , fName);
        editor.apply();
    }

    public static String getFName (){
        return getSharedPreferences().getString(Tags.F_NAME , "");
    }

    public static void setAge (int age){
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(Tags.AGE , age);
        editor.apply();
    }

    public static int getAge (){
        return getSharedPreferences().getInt(Tags.AGE , 0);
    }

    public static void setSingle(boolean single) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(Tags.SINGLE, single);
        editor.apply();
    }

    public static boolean isSingle() {
        return getSharedPreferences().getBoolean(Tags.SINGLE, false);

    }
}
