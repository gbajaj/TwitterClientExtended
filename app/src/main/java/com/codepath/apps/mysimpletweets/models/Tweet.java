package com.codepath.apps.mysimpletweets.models;

import com.codepath.apps.mysimpletweets.storage.MyTwitterDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by gauravb on 3/22/17.
 */
@Table(database = MyTwitterDataBase.class)
@Parcel(analyze = {Tweet.class})
public class Tweet extends BaseModel {
    @Column
    String body;

    public String getBody() {
        return body;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Column
    @PrimaryKey
    Long id;
    @ForeignKey(saveForeignKeyModel = false)
    User user;
    @Column
    String createdAt;
    @Column
    boolean favorited;
    @Column
    int retweetCount;
    @Column
    Boolean mentioned;
    @Column
    Boolean hasRetweetStatus;
    @Column
    String reTweetText;
    @ForeignKey(saveForeignKeyModel = false)
    User retweetUser;
    @ForeignKey(saveForeignKeyModel = false)
    Media media;

    //Deserialize

    public boolean isFavorited() {
        return favorited;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public static Tweet fromJSON(JSONObject jsonObject, boolean userMention) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.id = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.mentioned = userMention;
        tweet.hasRetweetStatus = Boolean.FALSE;
        if (jsonObject.has("retweeted_status")) {
            JSONObject retweetStatus = jsonObject.getJSONObject("retweeted_status");
            if (retweetStatus != null) {
                tweet.hasRetweetStatus = true;
                tweet.reTweetText = retweetStatus.getString("text");
                tweet.retweetUser = User.fromJSON(retweetStatus.getJSONObject("user"));
            }
        }
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        JSONObject entities = jsonObject.getJSONObject("entities");

        ArrayList<Media> list = Media.fromJSONArray(entities.optJSONArray("media"));
        if (list != null && list.isEmpty() == false) {
            tweet.media = list.get(0);
        }
        return tweet;
    }

    public Boolean getMentioned() {
        return mentioned;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray, boolean userMention) throws JSONException {
        ArrayList<Tweet> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(fromJSON(jsonArray.getJSONObject(i), userMention));
        }
        return list;
    }

    public User getUser() {
        return user;
    }

    public Boolean getHasRetweetStatus() {
        return hasRetweetStatus;
    }

    public String getReTweetText() {
        return reTweetText;
    }

    public User getRetweetUser() {
        return retweetUser;
    }

    public Media getMedia() {
        return media;
    }
}
