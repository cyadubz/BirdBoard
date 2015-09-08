package com.cya.birdboard.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cya.birdboard.R;
import com.cya.birdboard.core.model.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetViewHolder> {

    private List<Tweet> tweets;

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = getItem(position);
        holder.setName(tweet.getUsername());
        holder.setDate(tweet.getCreatedAt().toString());
        holder.setMessage(tweet.getText());
    }

    private Tweet getItem(int position) {
        return tweets.get(position);
    }

    @Override
    public int getItemCount() {
        if (tweets != null) {
            return tweets.size();
        }
        return 0;
    }

    public class TweetViewHolder extends RecyclerView.ViewHolder{

        private TextView textName;
        private TextView textDate;
        private TextView textMessage;

        public TweetViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textViewName);
            textDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
        }

        public void setName(String name) {
            textName.setText(name);
        }

        public void setDate(String date) {
            textDate.setText(date);
        }

        public void setMessage(String message) {
            textMessage.setText(message);
        }
    }
}
