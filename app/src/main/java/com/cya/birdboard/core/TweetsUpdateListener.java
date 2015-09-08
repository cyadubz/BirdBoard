package com.cya.birdboard.core;

import com.cya.birdboard.core.model.Tweet;

public interface TweetsUpdateListener {
    void onNewTweet(Tweet tweet);
}
