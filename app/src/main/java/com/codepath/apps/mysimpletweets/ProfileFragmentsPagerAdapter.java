package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.mysimpletweets.fragments.FavoritesTweetsFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;

/**
 * Created by gauravb on 3/30/17.
 */

public class ProfileFragmentsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"TWEETS", "FAVORITES"};
    private Context context;
    private TweetsListFragment tweetsListFragments[] = new TweetsListFragment[]{null,null};
    final User user;

    public ProfileFragmentsPagerAdapter(FragmentManager fm, Context context, User user) {
        super(fm);
        this.context = context;
        this.user = user;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public void addTweet(Tweet tweet) {
        for (TweetsListFragment tweetsListFragment:tweetsListFragments) {
            tweetsListFragment.onTweetCreated(tweet);
        }
    }

    @Override
    public Fragment getItem(int position) {
        TweetsListFragment fragment = null;
        if (position == 0) {
            fragment = UserTimelineFragment.newInstance(user);
        } else if (position == 1) {
            fragment = FavoritesTweetsFragment.newInstance(user);
        }
        tweetsListFragments[position]  = fragment;
        return tweetsListFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
