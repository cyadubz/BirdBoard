package com.cya.birdboard.tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.cya.birdboard.core.TwitterProvider;
import com.cya.birdboard.core.model.Tweet;

import java.util.List;

public class FeedTaskLoader extends AsyncTaskLoader<List<Tweet>> {

    private Context context;
    private TwitterProvider provider;

    public FeedTaskLoader(Context context, TwitterProvider provider) {
        super(context);
        this.context = context;
        this.provider = provider;
    }

    @Override
    public List<Tweet> loadInBackground() {
        return provider.getFeed();
    }
}
