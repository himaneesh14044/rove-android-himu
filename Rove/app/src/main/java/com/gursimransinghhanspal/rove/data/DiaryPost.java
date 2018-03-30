package com.gursimransinghhanspal.rove.data;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class DiaryPost implements Serializable {
	public ArrayList<Bitmap> images = new ArrayList<>();
	public Location location;
	public String description;
}
