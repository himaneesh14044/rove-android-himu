package com.gursimransinghhanspal.rove.activity;

/**
 * Created by Mankus on 30-03-2018.
 */

public class UserAccount_List_Item {

    private String diaryTitle;
    private int coverImage;

    public UserAccount_List_Item(String diaryTitle, int coverImage) {
        this.diaryTitle = diaryTitle;
        this.coverImage = coverImage;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public int getCoverImage() {
        return coverImage;
    }

}
