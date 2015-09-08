package com.cya.birdboard.tasks;

import android.content.Context;

import com.cya.birdboard.core.TwitterProvider;

public class PostTweetTask extends AbstractBirdyTask<String, Void, Void> {

    public PostTweetTask(Context context, TwitterProvider provider) {
        super(context, provider);
    }

    @Override
    protected Void doInBackground(String... params) {
        String message = params[0];
        getProvider().sendTweet(message);
        return null;
    }
}
