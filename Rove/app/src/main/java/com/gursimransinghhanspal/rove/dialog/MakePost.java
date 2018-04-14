package com.gursimransinghhanspal.rove.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.data.DiaryPost;
import com.gursimransinghhanspal.rove.misc.MakeDiaryPostDialogInterface;

import java.util.Locale;

public class MakePost extends Dialog {

	private Context mActivityContext;
	private DiaryPost mReferencedPost;
	private MakeDiaryPostDialogInterface mInteractionListener;

	private TextView mAddImageCountTextView;
	private EditText mDescriptionEditText;
	private ImageView mLocationRadioImageView;
	private TextView mSelectedLocationTextView;

	public MakePost(@NonNull Context context, DiaryPost referencedPost, MakeDiaryPostDialogInterface interactionListener) {
		super(context, true, null);

		mActivityContext = context;
		mReferencedPost = referencedPost;
		mInteractionListener = interactionListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_add_diary_item);

		// register ui elements
		ImageView addImageIconImageView = findViewById(R.id.dialogLayout_addDiaryItem_addImageIconImageView);
		TextView addImageTitleTextView = findViewById(R.id.dialogLayout_addDiaryItem_addImageTitleTextView);
		mAddImageCountTextView = findViewById(R.id.dialogLayout_addDiaryItem_addImageCountTextView);
		TextView removeImagesTextView = findViewById(R.id.dialogLayout_addDiaryItem_removeImagesTextView);

		ImageView currentLocationIconImageView = findViewById(R.id.dialogLayout_addDiaryItem_currentLocationIconImageView);
		TextView currentLocationTitleTextView = findViewById(R.id.dialogLayout_addDiaryItem_currentLocationTitleTextView);
		ImageView mapLocationIconImageView = findViewById(R.id.dialogLayout_addDiaryItem_mapLocationIconImageView);
		TextView mapLocationTitleTextView = findViewById(R.id.dialogLayout_addDiaryItem_mapLocationTitleTextView);
		TextView removeLocationTextView = findViewById(R.id.dialogLayout_addDiaryItem_removeLocationTextView);
		mLocationRadioImageView = findViewById(R.id.dialogLayout_addDiaryItem_locationRadioImageView);
		mSelectedLocationTextView = findViewById(R.id.dialogLayout_addDiaryItem_selectedLocationTextView);

		mDescriptionEditText = findViewById(R.id.dialogLayout_addDiaryItem_descriptionEditText);

		RelativeLayout cancelLayout = findViewById(R.id.dialogLayout_addDiaryItem_cancelRelativeLayout);
		RelativeLayout saveLayout = findViewById(R.id.dialogLayout_addDiaryItem_saveRelativeLayout);

		// set onClick listeners
		addImageIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onAddImageClicked();
			}
		});
		addImageTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onAddImageClicked();
			}
		});
		removeImagesTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRemoveImagesClicked();
			}
		});

		currentLocationIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCurrentLocationClicked();
			}
		});
		currentLocationTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCurrentLocationClicked();
			}
		});
		mapLocationIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMapLocationClicked();
			}
		});
		mapLocationTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMapLocationClicked();
			}
		});
		removeLocationTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRemoveLocationClicked();
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
		updateUI(mReferencedPost);
	}

	private void onAddImageClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onAddImageClicked();
		}
	}

	private void onRemoveImagesClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onRemoveImagesClicked();
		}
	}

	private void onCurrentLocationClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onCurrentLocationClicked();
		}
	}

	private void onMapLocationClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onMapLocationClicked();
		}
	}

	private void onRemoveLocationClicked() {
		if (mInteractionListener != null) {
			mInteractionListener.onRemoveLocationClicked();
		}
	}

	private void onCancel() {
		dismiss();
		if (mInteractionListener != null) {
			mInteractionListener.onCancel();
		}
	}

	private void onSave() {
		dismiss();
		if (mInteractionListener != null) {
			String textDescription = mDescriptionEditText.getText().toString().trim();
			mInteractionListener.onSave(textDescription);
		}
	}

	public void updateUI(DiaryPost referencedPost) {
		mReferencedPost = referencedPost;

		// set selected images count
		int selectedImagesCount = referencedPost.images.size();
		mAddImageCountTextView.setText(
				String.format(Locale.US, "%d", selectedImagesCount)
		);

		// set referenced location
		if (referencedPost.taggedLocation == null) {
			mLocationRadioImageView.setImageDrawable(
					mActivityContext.getResources().getDrawable(
							R.drawable.ic_radio_button_unchecked_24dp,
							mActivityContext.getTheme()
					)
			);
			mSelectedLocationTextView.setVisibility(View.GONE);
		} else {
			mLocationRadioImageView.setImageDrawable(
					mActivityContext.getResources().getDrawable(
							R.drawable.ic_radio_button_checked_24dp,
							mActivityContext.getTheme()
					)
			);
			mSelectedLocationTextView.setVisibility(View.VISIBLE);
		}


		// set text description
		mDescriptionEditText.setText(referencedPost.textDescription);
	}
}
