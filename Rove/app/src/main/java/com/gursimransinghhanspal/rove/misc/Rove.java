package com.gursimransinghhanspal.rove.misc;

import android.app.Application;

import com.gursimransinghhanspal.rove.data.DiaryPost;

import java.util.ArrayList;

public class Rove extends Application {

	public static ArrayList<DiaryPost> STATIC_DIARY_POSTS = new ArrayList<>();

	@Override
	public void onCreate() {
		super.onCreate();

		DiaryPost post1 = new DiaryPost();
		post1.description = "Having fun in hot springs of Kashmir! #YOLO";
		STATIC_DIARY_POSTS.add(post1);

		DiaryPost post2 = new DiaryPost();
		post2.description = "Having fun in hot springs of Jammu! #YODO";
		STATIC_DIARY_POSTS.add(post2);
	}
}
