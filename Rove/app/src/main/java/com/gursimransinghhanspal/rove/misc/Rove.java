package com.gursimransinghhanspal.rove.misc;

import android.app.Application;

import com.gursimransinghhanspal.rove.data.Diary;

import java.util.ArrayList;

public class Rove extends Application {

	// a list of all user diaries
	public static ArrayList<Diary> STATIC_USER_DIARIES = new ArrayList<>();

	@Override
	public void onCreate() {
		super.onCreate();
	}
}
