package com.testapp.twittertestapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.testapp.twittertestapp.R;
import com.testapp.twittertestapp.models.Tweets;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private Tweets mTweets;
    private Context mContext;

    public TweetsAdapter(Context context) {
        mContext = context;
    }

    public void setTweetDataSet(Tweets tweets) {
        mTweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getNameView().setText(mTweets.getTweetList().get(position).getUser().getName());
        viewHolder.getScreenNameView().setText("@" + mTweets.getTweetList().get(position).getUser().getScreen_name());
        Picasso.with(mContext)
                .load(mTweets.getTweetList().get(position).getUser().getProfile_image_url())
                .into(viewHolder.getProfileImageView());


        viewHolder.getTweetTextView().setText(Html.fromHtml(mTweets.getTweetList().get(position).getText()));
    }

    @Override
    public int getItemCount() {
        return (null != mTweets ? mTweets.getTweetList().size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView screen_name;
        private final ImageView profile_image;
        private final TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            screen_name = (TextView) itemView.findViewById(R.id.screen_name);
            profile_image = (ImageView) itemView.findViewById(R.id.profile_pic);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public TextView getNameView() {
            return name;
        }

        public TextView getScreenNameView() {
            return screen_name;
        }

        public ImageView getProfileImageView() {
            return profile_image;
        }

        public TextView getTweetTextView() {
            return text;
        }
    }
}
