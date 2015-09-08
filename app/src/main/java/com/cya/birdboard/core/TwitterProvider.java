package com.cya.birdboard.core;

import android.net.Uri;

import com.cya.birdboard.core.model.Tweet;
import com.cya.birdboard.ui.HomeActivity;

import java.util.List;

public interface TwitterProvider {
    void sendTweet(String message);
    void requestToken();
    void accessToken(Uri uri);
    String getAuthenticationUrl();

    List<Tweet> getFeed();

    void setTweetsListener(TweetsUpdateListener tweetsListener);
    void removeTweetsListener();
}
