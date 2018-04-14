package com.gursimransinghhanspal.rove.data;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class DiaryPost implements Serializable {
	public String postId;
	public String textDescription;
	public Location taggedLocation;
	public ArrayList<Bitmap> images;

	public DiaryPost() {
		this.postId = "defaultPostId";
		this.images = new ArrayList<>();
		this.taggedLocation = null;
		this.textDescription = null;
	}
}
