package com.cya.birdboard.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.cya.birdboard.Constants;

import static com.cya.birdboard.Constants.*;


public final class PersistUtils {

    private Context context;
    private static PersistUtils instance;
    private SharedPreferences sharedPreferences;

    private PersistUtils(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static PersistUtils getInstance(Context context) {
        if (instance == null) {
            instance = new PersistUtils(context);
        }
        return instance;
    }

    public void persistAccessToken(String accessToken, String accessTokenSecret) {
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(PREF_KEY_TOKEN, accessToken);
        e.putString(PREF_KEY_SECRET, accessTokenSecret);
        e.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(PREF_KEY_TOKEN, null);
    }

    public String getAccessTokenSecret() {
        return sharedPreferences.getString(PREF_KEY_SECRET, null);
    }
}
