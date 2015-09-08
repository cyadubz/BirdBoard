package com.cya.birdboard.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cya.birdboard.R;
import com.cya.birdboard.api.BirdyApi;
import com.cya.birdboard.tasks.RetrieveAccessTokenTask;
import com.cya.birdboard.tasks.RetrieveRequestTokenTask;

import static com.cya.birdboard.Constants.CALLBACK_URL;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!BirdyApi.getInstance().isConnected()) {
            BirdyApi.getInstance().retrieveRequestToken();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
