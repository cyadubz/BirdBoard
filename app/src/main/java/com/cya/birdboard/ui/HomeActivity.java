package com.cya.birdboard.ui;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cya.birdboard.Constants;
import com.cya.birdboard.R;
import com.cya.birdboard.api.BirdyApi;
import com.cya.birdboard.core.TweetsUpdateListener;
import com.cya.birdboard.core.TwitterProviderFactory;
import com.cya.birdboard.core.model.Tweet;
import com.cya.birdboard.tasks.FeedTaskLoader;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements TweetsUpdateListener, LoaderManager.LoaderCallbacks<List<Tweet>> {

    private Toolbar toolbar;
    private List<Tweet> tweets;
    private TweetsAdapter tweetsAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getLoaderManager().initLoader(Constants.LOADER_FEED_ID, Bundle.EMPTY, this);
        initToolbar();
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyceView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tweetsAdapter = new TweetsAdapter();
        recyclerView.setAdapter(tweetsAdapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        getLoaderManager().getLoader(Constants.LOADER_FEED_ID).forceLoad();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BirdyApi.getInstance().setTweetsListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BirdyApi.getInstance().removeTweetsListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Tweet>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case Constants.LOADER_FEED_ID:
                return new FeedTaskLoader(this, TwitterProviderFactory.getInstance(this));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Tweet>> loader, List<Tweet> data) {
        if (data != null) {
            updateTweets(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Tweet>> loader) {

    }

    @Override
    public void onNewTweet(Tweet tweet) {
        if (tweets != null) {
            tweets.add(0, tweet);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    tweetsAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void updateTweets(List<Tweet> tweets) {
        this.tweets = tweets;
        tweetsAdapter.setTweets(tweets);
        tweetsAdapter.notifyDataSetChanged();
    }
}
