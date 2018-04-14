package com.gursimransinghhanspal.rove.misc;

public enum PostTemplateType {
	TEXT_TEMPLATE(1),
	LOCATION_TEMPLATE(2),
	IMAGE_TEMPLATE(3);

	public final int intValue;

	PostTemplateType(int intValue) {
		this.intValue = intValue;
	}
}
