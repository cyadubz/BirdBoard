package com.cya.birdboard.core;

import android.content.Context;

public class TwitterProviderFactory {

    public static TwitterProvider getInstance(Context context) {
        // OnlyTwitter4J Implementation
        return new TwitterProviderImpl(context);
    }
}
