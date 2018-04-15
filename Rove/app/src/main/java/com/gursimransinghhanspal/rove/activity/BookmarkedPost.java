package com.gursimransinghhanspal.rove.activity;

/**
 * Created by himaneesh on 31/3/18.
 */
//POJO class for the bookmarked post
public class BookmarkedPost
{
    private String username;
    private String shortdesc;
    private int image;

    public String getUsername() {
        return username;
    }

    public BookmarkedPost(String username, String shortdesc, int image)
    {
        this.username = username;
        this.shortdesc = shortdesc;
        this.image = image;

    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getImage() {
        return image;
    }
}
