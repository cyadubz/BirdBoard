package com.cya.birdboard.core;

import android.content.Context;
import android.net.Uri;

import com.cya.birdboard.Constants;
import com.cya.birdboard.core.model.Tweet;
import com.cya.birdboard.utils.ObjectConvertor;
import com.cya.birdboard.utils.PersistUtils;

import java.util.List;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static com.cya.birdboard.Constants.CALLBACK_URL;
import static com.cya.birdboard.Constants.CONSUMER_KEY;
import static com.cya.birdboard.Constants.CONSUMER_SECRET;
import static com.cya.birdboard.Constants.INTENT_OAUTH_VERIFIER;

/**
 * This class implements Twitter4j lib behaviour. http://twitter4j.org
 */
public class TwitterProviderImpl implements TwitterProvider {

    private Context context;
    private Twitter twitter;
    private RequestToken requestToken;
    private TweetsUpdateListener tweetsUpdateListener;
    TwitterStream twitterStream;

    TwitterProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public void sendTweet(String message) {
        initTwitter();
        try {
            twitter.updateStatus(message);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAuthenticationUrl() {
        if (requestToken != null) {
            return requestToken.getAuthenticationURL();
        }
        return null;
    }

    @Override
    public void requestToken() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        Configuration configuration = configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .build();

        TwitterFactory twitterFactory = new TwitterFactory(configuration);
        twitter = twitterFactory.getInstance();

        //TODO:
        try {
            requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accessToken(Uri uri) {
        if (requestToken != null) {
            String verifier = uri.getQueryParameter(INTENT_OAUTH_VERIFIER);
            try {
                AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                PersistUtils.getInstance(context).persistAccessToken(accessToken.getToken(), accessToken.getTokenSecret());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Tweet> getFeed() {
        List<Tweet> tweets = ObjectConvertor.toTweets(getHomeTimeline());
        return tweets;
    }

    @Override
    public void setTweetsListener(TweetsUpdateListener tweetsListener) {
        this.tweetsUpdateListener = tweetsListener;
        String accessToken = PersistUtils.getInstance(context).getAccessToken();
        String accessTokenSecret = PersistUtils.getInstance(context).getAccessTokenSecret();

        Configuration configuration = new ConfigurationBuilder()
                .setOAuthConsumerKey(Constants.CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                .build();

        twitterStream = new TwitterStreamFactory(configuration).getInstance();
        twitterStream.addListener(statusListener);
        twitterStream.user();
    }

    @Override
    public void removeTweetsListener() {
        twitterStream.removeListener(statusListener);
        this.tweetsUpdateListener = null;
    }

    private List<Status> getHomeTimeline() {
        List<Status> statuses = null;
        initTwitter();
        try {
            statuses = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    private void initTwitter() {
        String accessToken = getAccessToken();
        String accessTokenSecret = getAccessTokenSecret();

        Configuration configuration = new ConfigurationBuilder()
                .setOAuthConsumerKey(Constants.CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                .build();

        twitter = new TwitterFactory(configuration).getInstance();
    }

    private String getAccessToken() {
        if (context != null) {
            return PersistUtils.getInstance(context).getAccessToken();
        }
        return "";
    }

    private String getAccessTokenSecret() {
        if (context != null) {
            return PersistUtils.getInstance(context).getAccessTokenSecret();
        }
        return "";

    }

    StatusListener statusListener = new StatusListener() {
        @Override
        public void onStatus(Status status) {
            if (tweetsUpdateListener != null) {
                tweetsUpdateListener.onNewTweet(ObjectConvertor.toTweet(status));
            }
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int i) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

        }

        @Override
        public void onException(Exception e) {

        }
    };
}
