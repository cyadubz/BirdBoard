package com.cya.birdboard;

import android.app.Application;

import com.cya.birdboard.api.BirdyApi;
import com.cya.birdboard.core.TwitterProviderFactory;
import com.cya.birdboard.core.TwitterProviderImpl;

public class TheApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BirdyApi.init(getApplicationContext(), TwitterProviderFactory.getInstance(getApplicationContext()));
    }
}
