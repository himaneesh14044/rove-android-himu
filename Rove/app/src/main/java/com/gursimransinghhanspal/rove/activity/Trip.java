package com.gursimransinghhanspal.rove;

/**
 * Created by Himaneesh on 09-03-2018.
 */

public class Trip
{
    private String usernmae;
    private String shortdesc;
    private int image;
    public Trip(String username,String shortdesc, int image)
    {
        this.usernmae = username;
        this.shortdesc = shortdesc;
        this.image = image;
    }
    public String getUsernmae() {
        return usernmae;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getImage() {
        return image;
    }
}
