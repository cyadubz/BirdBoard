package com.cya.birdboard.utils;

import com.cya.birdboard.core.model.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;

public final class ObjectConvertor {

    private ObjectConvertor() {

    }

    public static Tweet toTweet(final Status status) {
        Tweet tweet = new Tweet() {
            @Override
            public Date getCreatedAt() {
                return status.getCreatedAt();
            }

            @Override
            public long getId() {
                return status.getId();
            }

            @Override
            public String getText() {
                return status.getText();
            }

            @Override
            public String getUsername() {
                return status.getUser().getScreenName();
            }
        };

        return tweet;
    }

    public static List<Tweet> toTweets(List<Status> statuses) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Status status : statuses) {
            tweets.add(toTweet(status));
        }

        return tweets;
    }
}
