package com.gursimransinghhanspal.rove.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.data.DiaryPost;
import com.gursimransinghhanspal.rove.dialog.AddDiaryItem;
import com.gursimransinghhanspal.rove.misc.MakeDiaryDialogInterface;
import com.gursimransinghhanspal.rove.misc.Rove;

import java.io.IOException;
import java.io.InputStream;

public class MakeDiary extends AppCompatActivity implements MakeDiaryDialogInterface {

	private static final int PICK_IMAGE_REQUEST = 1;

	private RecyclerView mDiaryItemsRecyclerView;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_diary);

		imageView = findViewById(R.id.toolbarImage);

		mDiaryItemsRecyclerView = findViewById(R.id.activityLayout_makeDiary_diaryItemsRecyclerView);
		mDiaryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mDiaryItemsRecyclerView.setAdapter(new Adapter());
		mDiaryItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());

		FloatingActionButton fab = findViewById(R.id.activityLayout_makeDiary_addDiaryItemFAB);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog d = new AddDiaryItem(MakeDiary.this, true, MakeDiary.this);
				d.show();
			}
		});
	}


	@Override
	public void onPostAddImage() {
		Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
		getContentIntent.setType("image/*");

		Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickIntent.setType("image/*");

		Intent chooserIntent = Intent.createChooser(getContentIntent, "Select Image");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

		startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
	}

	@Override
	public void onPostRemoveImages() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getResources().getString(R.string.sharedPreferencesFile),
				MODE_PRIVATE
		);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_images)
		);
		editor.apply();
	}

	@Override
	public void onPostAddCurrentLocation() {

	}

	@Override
	public void onPostSelectLocationFromMap() {

	}

	@Override
	public void onPostRemoveLocation() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getResources().getString(R.string.sharedPreferencesFile),
				MODE_PRIVATE
		);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_currentLocation)
		);
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_mapLocation)
		);
		editor.apply();
	}

	@Override
	public void onPostSaveDescription(String description) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getResources().getString(R.string.sharedPreferencesFile),
				MODE_PRIVATE
		);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_description),
				description
		);
		editor.apply();
	}

	@Override
	public void onPostCancel() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getResources().getString(R.string.sharedPreferencesFile),
				MODE_PRIVATE
		);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_images)
		);
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_currentLocation)
		);
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_mapLocation)
		);
		editor.remove(
				getResources().getString(R.string.activity_MakeDiary_savedInfoKey_description)
		);
		editor.apply();
	}

	@Override
	public void onPostSave() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == PICK_IMAGE_REQUEST) {
			try {
				if (data == null || data.getData() == null)
					return;

				InputStream inputStream = getContentResolver().openInputStream(data.getData());
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(inputStream);
				Log.i("MAKEDIARY", bitmap.toString());

				int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
				Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
				imageView.setImageBitmap(scaled);

				imageView.setImageDrawable(
						getResources().getDrawable(R.drawable.im_landscape_placeholder, getTheme())
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View itemView = null;
			if (viewType == 0) {
				itemView = LayoutInflater.from(parent.getContext())
						.inflate(R.layout.recycler_item_diary_text_template, parent, false);
			} else if (viewType == 1) {
				itemView = LayoutInflater.from(parent.getContext())
						.inflate(R.layout.recycler_item_diary_location_template, parent, false);
			} else if (viewType == 2) {
				itemView = LayoutInflater.from(parent.getContext())
						.inflate(R.layout.recycler_item_diary_image_template, parent, false);
			}
			return new ViewHolder(itemView, viewType);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
			DiaryPost post = Rove.STATIC_DIARY_POSTS.get(position);

			if (holder.descriptionTextView != null && !TextUtils.isEmpty(post.description)) {
				holder.descriptionTextView.setText(post.description);
			}

		}

		@Override
		public int getItemViewType(int position) {
			DiaryPost post = Rove.STATIC_DIARY_POSTS.get(position);

			int type = 2;
			if (post.images == null || post.images.size() == 0) {
				type = 1;
			}
			if (post.location == null) {
				type = 0;
			}
			return type;
		}

		@Override
		public int getItemCount() {
			return Rove.STATIC_DIARY_POSTS.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder {

			TextView descriptionTextView;


			public ViewHolder(View itemView, int type) {
				super(itemView);

				FrameLayout socialBar = itemView.findViewById(R.id.viewLayout_diarySocialBar_rootFrameLayout);
				socialBar.setVisibility(View.GONE);

				if (type == 0) {
					descriptionTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryTextTemplate_noteTextView);
				}
				if (type == 1) {
					descriptionTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryLocationTemplate_noteTextView);
				}
				if (type == 2) {
					descriptionTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryImageTemplate_noteTextView);
				}
			}
		}
	}
}
