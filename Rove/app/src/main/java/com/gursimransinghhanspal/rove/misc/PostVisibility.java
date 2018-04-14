package com.gursimransinghhanspal.rove.misc;

public enum PostVisibility {
	PRIVATE(1),
	FOLLOWERS(2),
	PUBLIC(3);

	public final int intValue;

	PostVisibility(int intValue) {
		this.intValue = intValue;
	}

	PostVisibility findByValue(int _intValue) {
		PostVisibility[] array = PostVisibility.values();
		for (PostVisibility pv : array) {
			if (pv.intValue == _intValue) {
				return pv;
			}
		}
		return null;
	}
}
