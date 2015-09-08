package com.cya.birdboard.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cya.birdboard.R;
import com.cya.birdboard.core.TwitterAuthListener;
import com.cya.birdboard.api.BirdyApi;

import static com.cya.birdboard.Constants.CALLBACK_URL;

public class OAuthLogin extends Activity implements TwitterAuthListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_login);
    }

    @Override
    protected void onResume() {
        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
            BirdyApi.getInstance().retrieveAccessToken(uri, this);
        }
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onError() {

    }
}
