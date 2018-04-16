package com.gursimransinghhanspal.rove;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Himaneesh on 09-03-2018.
 */

// Trip is basically our diary.
public class Trip
{
    private static DateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    private String diaryId;
    private ArrayList<String> postIds;
    private String userId;
    private String title;
    private String coverPhotoName;
    private Date updatedAt;

    public Trip(String diaryId, ArrayList<String> postIds,
                String userId, String title, String coverPhotoName, String updatedAt)
    {
        this.diaryId = diaryId;
        this.postIds = postIds;
        this.userId  = userId;
        this.title = title;
        this.coverPhotoName = coverPhotoName;
        try {
            this.updatedAt = dateFormat.parse(updatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDiaryId() {
        return this.diaryId;
    }

    public ArrayList<String> getPostIds() {
        return this.postIds;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCoverPhotoName() {
        return this.coverPhotoName;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }
}
