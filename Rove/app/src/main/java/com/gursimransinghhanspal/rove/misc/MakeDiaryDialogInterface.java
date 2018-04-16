package com.gursimransinghhanspal.rove.misc;

public interface MakeDiaryDialogInterface {

	void onPostAddImage();

	void onPostRemoveImages();

	void onPostAddCurrentLocation();

	void onPostSelectLocationFromMap();

	void onPostRemoveLocation();

	void onPostSaveDescription(String description);

	void onPostCancel();

	void onPostSave();
}
