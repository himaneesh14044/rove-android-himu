package com.gursimransinghhanspal.rove.data;

import android.graphics.Bitmap;

import com.gursimransinghhanspal.rove.misc.PostVisibility;

import java.util.ArrayList;
import java.util.UUID;

public class Diary {
	public String diaryId;
	public String title;
	public Bitmap coverImage;
	public PostVisibility visibility;
	public ArrayList<DiaryPost> posts;

	// provide default values
	public Diary() {
		this.diaryId = UUID.randomUUID().toString();
		this.title = "New Diary";
		this.coverImage = null;
		this.visibility = PostVisibility.PRIVATE;
		this.posts = new ArrayList<>();
	}
}
