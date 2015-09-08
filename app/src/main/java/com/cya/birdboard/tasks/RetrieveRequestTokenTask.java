package com.cya.birdboard.tasks;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import com.cya.birdboard.api.BirdyApi;
import com.cya.birdboard.core.TwitterProvider;

public class RetrieveRequestTokenTask extends AbstractBirdyTask<Void, Void, Void> {

    public RetrieveRequestTokenTask(Context context, TwitterProvider provider) {
        super(context, provider);
    }

    @Override
    protected Void doInBackground(Void... params) {
        getProvider().requestToken();
        String authenticateUrl = getProvider().getAuthenticationUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authenticateUrl));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
        return null;
    }
}
