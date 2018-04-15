package com.gursimransinghhanspal.rove.misc;

public interface EditDiaryDialogInterface {
	void onSelectCoverImageClicked();

	void onRemoveCoverImageClicked();

	void onVisibilitySelected(PostVisibility visibility);

	void onSave(String updatedTitle);

	void onCancel();
}
