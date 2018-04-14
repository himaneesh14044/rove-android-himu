package com.gursimransinghhanspal.rove.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.data.Diary;
import com.gursimransinghhanspal.rove.misc.EditDiaryDialogInterface;
import com.gursimransinghhanspal.rove.misc.PostVisibility;

public class EditDiary extends Dialog {

	private static final String TAG = "EditDiary";

	private Context mActivityContext;
	private Diary mReferencedDiary;
	private EditDiaryDialogInterface mInteractionListener;

	private EditText mTitleEditText;
	private RadioGroup mPrivacyRadioGroup;
	private RadioButton mPrivateRadioButton;
	private RadioButton mFollowerRadioButton;
	private RadioButton mPublicRadioButton;

	public EditDiary(@NonNull Context context, Diary referencedDiary, EditDiaryDialogInterface interactionListener) {
		super(context, true, null);
		mActivityContext = context;
		mReferencedDiary = referencedDiary;
		mInteractionListener = interactionListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_edit_diary);

		// register ui components
		ImageView selectImageIconImageView = findViewById(R.id.dialogLayout_editDiaryItem_selectImageIconImageView);
		TextView selectImageTitleTextView = findViewById(R.id.dialogLayout_editDiaryItem_selectImageTitleTextView);
		TextView removeImageTextView = findViewById(R.id.dialogLayout_editDiaryItem_removeImageTextView);

		mTitleEditText = findViewById(R.id.dialogLayout_editDiaryItem_titleEditText);

		RelativeLayout cancelLayout = findViewById(R.id.dialogLayout_editDiaryItem_cancelRelativeLayout);
		RelativeLayout saveLayout = findViewById(R.id.dialogLayout_editDiaryItem_saveRelativeLayout);

		mPrivacyRadioGroup = findViewById(R.id.dialogLayout_editDiaryItem_privacyRadioGroup);
		mPrivateRadioButton = findViewById(R.id.dialogLayout_editDiaryItem_privateRadioButton);
		mFollowerRadioButton = findViewById(R.id.dialogLayout_editDiaryItem_followerRadioButton);
		mPublicRadioButton = findViewById(R.id.dialogLayout_editDiaryItem_publicRadioButton);

		// set onClick listeners
		selectImageIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCoverImageClicked();
			}
		});
		selectImageTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCoverImageClicked();
			}
		});
		removeImageTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRemoveCoverImageClicked();
			}
		});

		mPrivateRadioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onVisibilitySelected(v);
			}
		});
		mFollowerRadioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onVisibilitySelected(v);
			}
		});
		mPublicRadioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onVisibilitySelected(v);
			}
		});

		cancelLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCancel();
			}
		});
		saveLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSave();
			}
		});

		// update ui
		updateUI(mReferencedDiary);
	}

	private void onSelectCoverImageClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onSelectCoverImageClicked();
		}
	}

	private void onRemoveCoverImageClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onRemoveCoverImageClicked();
		}
	}

	private void onVisibilitySelected(View v) {
		if (mInteractionListener == null) {
			return;
		}

		switch (v.getId()) {
			case R.id.dialogLayout_editDiaryItem_privateRadioButton:
				mInteractionListener.onVisibilitySelected(PostVisibility.PRIVATE);
				break;
			case R.id.dialogLayout_editDiaryItem_followerRadioButton:
				mInteractionListener.onVisibilitySelected(PostVisibility.FOLLOWERS);
				break;
			case R.id.dialogLayout_editDiaryItem_publicRadioButton:
				mInteractionListener.onVisibilitySelected(PostVisibility.PUBLIC);
				break;
			default:
				break;
		}
	}

	private void onCancel() {
		if (mInteractionListener != null) {
			mInteractionListener.onCancel();
		}
		dismiss();
	}

	private void onSave() {
		if (mInteractionListener != null) {
			String typedText = mTitleEditText.getText().toString().trim();
			mInteractionListener.onSave(typedText);
		}
		dismiss();
	}

	public void updateUI(Diary referenceDiary) {
		mReferencedDiary = referenceDiary;

		// set image selected
		// nothing to show in ui

		// set title
		mTitleEditText.setText(referenceDiary.title);

		// set privacy radio
		Log.d(TAG, String.format("updateUI()::Visibility: %s", referenceDiary.visibility));
		mPrivacyRadioGroup.clearCheck();
		switch (referenceDiary.visibility) {
			case PRIVATE:
				mPrivateRadioButton.setChecked(true);
				break;
			case FOLLOWERS:
				mFollowerRadioButton.setChecked(true);
				break;
			case PUBLIC:
				mPublicRadioButton.setChecked(true);
				break;
			default:
				break;
		}
	}
}
