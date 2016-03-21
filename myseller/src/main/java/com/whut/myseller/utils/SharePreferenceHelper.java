package com.whut.myseller.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yubo on 2016/3/17.
 */
public class SharePreferenceHelper {

    private SharedPreferences mSharedPreferences;

    public SharePreferenceHelper(Context context, String name, int mode) {
        mSharedPreferences = context.getSharedPreferences(name, mode);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }


    public String getString(String key, String defval) {
        String result = mSharedPreferences.getString(key, defval);
        return result;
    }

    public String getString(String key) {
        return getString(key, null);
    }


}
