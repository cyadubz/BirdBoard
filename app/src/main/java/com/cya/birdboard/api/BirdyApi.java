package com.cya.birdboard.api;
import android.content.Context;
import android.net.Uri;

import com.cya.birdboard.core.TweetsUpdateListener;
import com.cya.birdboard.core.TwitterAuthListener;
import com.cya.birdboard.core.TwitterProvider;
import com.cya.birdboard.tasks.PostTweetTask;
import com.cya.birdboard.tasks.RetrieveAccessTokenTask;
import com.cya.birdboard.tasks.RetrieveRequestTokenTask;
import com.cya.birdboard.ui.HomeActivity;
import com.cya.birdboard.utils.PersistUtils;

public class BirdyApi {

    private static BirdyApi instance;
    private Context context;
    private TwitterProvider provider;
    private TweetsUpdateListener tweetsListener;

    private BirdyApi(Context context, TwitterProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    public static void init(Context context, TwitterProvider provider) {
        instance = new BirdyApi(context, provider);
    }

    public static BirdyApi getInstance() {
        //TODO: Exception if not initialised
        return instance;
    }

    public boolean isConnected() {
        return (PersistUtils.getInstance(context).getAccessToken() != null);
    }

    public void retrieveRequestToken() {
        new RetrieveRequestTokenTask(context, provider).execute();
    }

    public void retrieveAccessToken(Uri uri, TwitterAuthListener twitterAuthListener) {
        new RetrieveAccessTokenTask(context, provider)
                .addTwitterAuthListener(twitterAuthListener)
                .execute(uri);
    }

    public void postTweet(String message) {
        new PostTweetTask(context, provider).execute(message);
    }

    public void setTweetsListener(HomeActivity tweetsListener) {
        this.tweetsListener = tweetsListener;
        provider.setTweetsListener(tweetsListener);
    }

    public void removeTweetsListener() {
        this.tweetsListener = null;
    }
}
