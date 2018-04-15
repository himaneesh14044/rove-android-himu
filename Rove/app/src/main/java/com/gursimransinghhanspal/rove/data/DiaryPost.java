package com.gursimransinghhanspal.rove.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.places.Place;
import com.gursimransinghhanspal.rove.misc.PostTemplateType;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiaryPost implements Serializable {
	public String postId;
	public String textDescription;
	public CustomLocation taggedLocation;
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

	public String getLocationShortName() {
		if (this.taggedLocation != null) {
			return this.taggedLocation.getShortName();
		}
		return "";
	}

	public String getLocationLongName() {
		if (this.taggedLocation != null) {
			return this.taggedLocation.getLongName();
		}
		return "";
	}

	public static class CustomLocation {
		private double latitude;
		private double longitude;
		private String longName;
		private String shortName;

		public CustomLocation(Context ctx, double lat, double lng) {
			this.latitude = lat;
			this.longitude = lng;
			this.generateLocationNames(ctx);
		}

		public CustomLocation(Place place) {
			this.latitude = place.getLatLng().latitude;
			this.longitude = place.getLatLng().longitude;
			this.shortName = place.getName().toString();
			this.longName = String.format("%s\n\n%s", place.getName().toString(), place.getAddress().toString());
		}

		private void generateLocationNames(Context ctx) {
			Geocoder gcd = new Geocoder(ctx, Locale.getDefault());
			List<Address> addresses = null;
			try {
				addresses = gcd.getFromLocation(latitude, longitude, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (addresses != null && addresses.size() > 0) {
				this.shortName = String.format("%s, %s", addresses.get(0).getSubLocality(), addresses.get(0).getLocality());
				this.longName = String.format("%s, %s, %s", addresses.get(0).getSubLocality(), addresses.get(0).getLocality(), addresses.get(0).getCountryName());
			}
		}

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public String getLongName() {
			return longName;
		}

		public String getShortName() {
			return shortName;
		}

		@Override
		public String toString() {
			String out = String.format(Locale.US, "%f:%f", this.latitude, this.longitude);
			return out;
		}
	}
}
