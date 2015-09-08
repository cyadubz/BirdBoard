package com.cya.birdboard.tasks;
import android.content.Context;
import android.net.Uri;

import com.cya.birdboard.core.TwitterAuthListener;
import com.cya.birdboard.core.TwitterProvider;

public class RetrieveAccessTokenTask extends AbstractBirdyTask<Uri, Void, Void> {

    private TwitterAuthListener twitterAuthListener;

    public RetrieveAccessTokenTask(Context context, TwitterProvider provider) {
        super(context, provider);
    }

    public RetrieveAccessTokenTask addTwitterAuthListener(TwitterAuthListener twitterAuthListener) {
        this.twitterAuthListener = twitterAuthListener;
        return this;
    }

    @Override
    protected Void doInBackground(Uri... params) {
        Uri uri = params[0];
        getProvider().accessToken(uri);
        if (twitterAuthListener != null) {
            twitterAuthListener.onSuccess();
        }
        return null;
    }
}
