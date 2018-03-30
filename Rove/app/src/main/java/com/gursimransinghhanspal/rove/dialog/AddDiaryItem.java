package com.gursimransinghhanspal.rove.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.misc.MakeDiaryDialogInterface;

import java.util.Locale;

public class AddDiaryItem extends Dialog {

	private Context mActivityContext;
	private MakeDiaryDialogInterface mActivityInterface;

	private TextView mAddImageCountTextView;
	private EditText mDescriptionEditText;
	private ImageView mCurrentLocationRadioImageView;
	private ImageView mMapLocationRadioImageView;

	public AddDiaryItem(@NonNull Context context, boolean cancelable, MakeDiaryDialogInterface callbackInterface) {
		super(context, cancelable, null);

		mActivityContext = context;
		mActivityInterface = callbackInterface;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_add_diary_item);

		ImageView addImageIconImageView = findViewById(R.id.dialogLayout_addDiaryItem_addImageIconImageView);
		TextView addImageTitleTextView = findViewById(R.id.dialogLayout_addDiaryItem_addImageTitleTextView);
		mAddImageCountTextView = findViewById(R.id.dialogLayout_addDiaryItem_addImageCountTextView);
		TextView removeImagesTextView = findViewById(R.id.dialogLayout_addDiaryItem_removeImagesTextView);

		ImageView currentLocationIconImageView = findViewById(R.id.dialogLayout_addDiaryItem_currentLocationIconImageView);
		TextView currentLocationTitleTextView = findViewById(R.id.dialogLayout_addDiaryItem_currentLocationTitleTextView);
		mCurrentLocationRadioImageView = findViewById(R.id.dialogLayout_addDiaryItem_currentLocationRadioImageView);
		ImageView mapLocationIconImageView = findViewById(R.id.dialogLayout_addDiaryItem_mapLocationIconImageView);
		TextView mapLocationTitleTextView = findViewById(R.id.dialogLayout_addDiaryItem_mapLocationTitleTextView);
		mMapLocationRadioImageView = findViewById(R.id.dialogLayout_addDiaryItem_mapLocationRadioImageView);
		TextView removeLocationTextView = findViewById(R.id.dialogLayout_addDiaryItem_removeLocationTextView);

		mDescriptionEditText = findViewById(R.id.dialogLayout_addDiaryItem_descriptionEditText);

		RelativeLayout cancelLayout = findViewById(R.id.dialogLayout_addDiaryItem_cancelRelativeLayout);
		RelativeLayout saveLayout = findViewById(R.id.dialogLayout_addDiaryItem_saveRelativeLayout);

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
				onSelectCurrentLocationClicked();
			}
		});
		currentLocationTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCurrentLocationClicked();
			}
		});
		mCurrentLocationRadioImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCurrentLocationClicked();
			}
		});
		mapLocationIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectLocationFromMapsClicked();
			}
		});
		mapLocationTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectLocationFromMapsClicked();
			}
		});
		mMapLocationRadioImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectLocationFromMapsClicked();
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
				onCancelClicked();
			}
		});
		saveLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSaveClicked();
			}
		});
	}

	public void refreshData() {
		SharedPreferences sharedPreferences = mActivityContext.getSharedPreferences(
				mActivityContext.getResources().getString(R.string.sharedPreferencesFile),
				Context.MODE_PRIVATE
		);
		String selectedImagesAsString = sharedPreferences.getString(
				mActivityContext.getResources().getString(R.string.activity_MakeDiary_savedInfoKey_images),
				""
		);
		String currentLocation = sharedPreferences.getString(
				mActivityContext.getResources().getString(R.string.activity_MakeDiary_savedInfoKey_currentLocation),
				""
		);
		String mapLocation = sharedPreferences.getString(
				mActivityContext.getResources().getString(R.string.activity_MakeDiary_savedInfoKey_mapLocation),
				""
		);
		String description = sharedPreferences.getString(
				mActivityContext.getResources().getString(R.string.activity_MakeDiary_savedInfoKey_description),
				""
		);

		int selectedImagesCount = 0;
		if (!TextUtils.isEmpty(selectedImagesAsString)) {
			String[] selectedImagesAsArray = selectedImagesAsString.split(";");
			selectedImagesCount = selectedImagesAsArray.length;
		}
		mAddImageCountTextView.setText(
				String.format(Locale.US, "%d", selectedImagesCount)
		);

		if (TextUtils.isEmpty(currentLocation)) {
			mCurrentLocationRadioImageView.setImageDrawable(
					mActivityContext.getResources().getDrawable(
							R.drawable.ic_radio_button_unchecked_24dp,
							mActivityContext.getTheme()
					)
			);
		} else {
			mCurrentLocationRadioImageView.setImageDrawable(
					mActivityContext.getResources().getDrawable(
							R.drawable.ic_radio_button_checked_24dp,
							mActivityContext.getTheme()
					)
			);
		}

		if (TextUtils.isEmpty(mapLocation)) {
			mMapLocationRadioImageView.setImageDrawable(
					mActivityContext.getResources().getDrawable(
							R.drawable.ic_radio_button_unchecked_24dp,
							mActivityContext.getTheme()
					)
			);
		} else {
			mMapLocationRadioImageView.setImageDrawable(
					mActivityContext.getResources().getDrawable(
							R.drawable.ic_radio_button_checked_24dp,
							mActivityContext.getTheme()
					)
			);
		}

		mDescriptionEditText.setText(description);
	}

	private void saveState() {
		if (mActivityInterface != null) {
			String description = mDescriptionEditText.getText().toString();
			mActivityInterface.onPostSaveDescription(description);
		}
	}

	private void onAddImageClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostAddImage();
		}
	}

	private void onRemoveImagesClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostRemoveImages();
		}
		refreshData();
	}

	private void onSelectCurrentLocationClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostAddCurrentLocation();
		}
	}

	private void onSelectLocationFromMapsClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostSelectLocationFromMap();
		}
	}

	private void onRemoveLocationClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostRemoveLocation();
		}
		refreshData();
	}

	private void onCancelClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostCancel();
		}
		refreshData();
		dismiss();
	}

	private void onSaveClicked() {
		saveState();
		if (mActivityInterface != null) {
			mActivityInterface.onPostSave();
		}
	}
}
