package com.cya.birdboard.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.cya.birdboard.core.TwitterProvider;

public abstract class AbstractBirdyTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Context context;
    private TwitterProvider provider;

    public AbstractBirdyTask(Context context, TwitterProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    public Context getContext() {
        return context;
    }

    public TwitterProvider getProvider() {
        return provider;
    }
}
