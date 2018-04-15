package com.gursimransinghhanspal.rove.data;

import android.graphics.Bitmap;
import android.location.Location;

import com.gursimransinghhanspal.rove.misc.PostTemplateType;

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

	public PostTemplateType getTemplateType() {
		if (this.images.size() > 0) {
			return PostTemplateType.IMAGE_TEMPLATE;
		} else if (this.taggedLocation != null) {
			return PostTemplateType.LOCATION_TEMPLATE;
		} else {
			return PostTemplateType.TEXT_TEMPLATE;
		}
	}
}
