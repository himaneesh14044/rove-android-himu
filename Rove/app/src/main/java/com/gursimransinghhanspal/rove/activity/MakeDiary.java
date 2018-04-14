package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.gursimransinghhanspal.rove.data.Diary;
import com.gursimransinghhanspal.rove.data.DiaryPost;
import com.gursimransinghhanspal.rove.dialog.EditDiary;
import com.gursimransinghhanspal.rove.dialog.MakePost;
import com.gursimransinghhanspal.rove.misc.EditDiaryDialogInterface;
import com.gursimransinghhanspal.rove.misc.MakeDiaryPostDialogInterface;
import com.gursimransinghhanspal.rove.misc.PostVisibility;
import com.gursimransinghhanspal.rove.misc.Rove;

import java.io.IOException;
import java.io.InputStream;

public class MakeDiary extends AppCompatActivity {

	private static final String TAG = "MakeDiary";
	public static final String DB_DIARY_ID = "com.gursimransinghhanspal.rove.activity.MakeDiary.DiaryId";

	// the diary and the post currently being edited
	private static Diary STATIC_EDITING_DIARY;
	private static DiaryPost STATIC_EDITING_DIARY_POST;

	private String dbDiaryId;

	private static final int SELECT_COVER_IMAGE_REQUEST = 1;
	private static final int ADD_IMAGE_REQUEST = 2;

	private CollapsingToolbarLayout mCollapsingToolbarLayout;
	private RecyclerView mDiaryItemsRecyclerView;
	private ImageView mCoverImageView;
	private FloatingActionButton mToolbarEditFAB;
	private MakePost mActiveMakePostDialog;
	private EditDiary mActiveEditDiaryDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_diary);

		// get diary diaryId
		Bundle startingArgs = getIntent().getExtras();
		if (startingArgs == null) {
			finish();
			return;
		}
		dbDiaryId = startingArgs.getString(DB_DIARY_ID, null);
		if (dbDiaryId == null) {
			finish();
			return;
		}

		// search if diary exists
		STATIC_EDITING_DIARY = new Diary();
		STATIC_EDITING_DIARY_POST = new DiaryPost();
		for (Diary diary :
				Rove.STATIC_USER_DIARIES) {
			if (diary.diaryId.compareTo(dbDiaryId) == 0) {
				STATIC_EDITING_DIARY = diary;
				break;
			}
		}

		mCollapsingToolbarLayout = findViewById(R.id.activityLayout_makeDiary_collapsingToolbarLayout);
		mCoverImageView = findViewById(R.id.activityLayout_makeDiary_collapsingToolbar_bgImageView);
		FloatingActionButton toolbarFAB = findViewById(R.id.activityLayout_makeDiary_collapsingToolbar_editFAB);

		mDiaryItemsRecyclerView = findViewById(R.id.activityLayout_makeDiary_diaryItemsRecyclerView);
		mDiaryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mDiaryItemsRecyclerView.setAdapter(new Adapter());
		mDiaryItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());

		FloatingActionButton mainFAB = findViewById(R.id.activityLayout_makeDiary_addDiaryItemFAB);
		mainFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// create a new post here to edit
				STATIC_EDITING_DIARY_POST = new DiaryPost();
				mActiveMakePostDialog = new MakePost(
						MakeDiary.this,
						STATIC_EDITING_DIARY_POST,
						new MakeDiaryPostDialogInterface() {

							@Override
							public void onAddImageClicked() {
								makePostOnAddImageClicked();
							}

							@Override
							public void onRemoveImagesClicked() {
								makePostOnRemoveImagesClicked();
							}

							@Override
							public void onCurrentLocationClicked() {
								makePostOnCurrentLocationClicked();
							}

							@Override
							public void onMapLocationClicked() {
								makePostOnMapLocationClicked();
							}

							@Override
							public void onRemoveLocationClicked() {
								makePostOnRemoveLocationClicked();
							}

							@Override
							public void onCancel() {
								makePostOnCancel();
							}

							@Override
							public void onSave(String textDescription) {
								makePostOnSave(textDescription);
							}
						}
				);
				mActiveMakePostDialog.show();
			}
		});

		toolbarFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActiveEditDiaryDialog = new EditDiary(
						MakeDiary.this,
						STATIC_EDITING_DIARY,
						new EditDiaryDialogInterface() {
							@Override
							public void onSelectCoverImageClicked() {
								editDiaryOnSelectCoverImageClicked();
							}

							@Override
							public void onRemoveCoverImageClicked() {
								editDiaryOnRemoveCoverImageClicked();
							}

							@Override
							public void onVisibilitySelected(PostVisibility visibility) {
								editDiaryOnVisibilitySelected(visibility);
							}

							@Override
							public void onSave(String updatedTitle) {
								editDiaryOnSave(updatedTitle);
							}

							@Override
							public void onCancel() {
								editDiaryOnCancel();
							}
						}
				);
				mActiveEditDiaryDialog.show();
			}
		});

		// update the UI
		updateUI(STATIC_EDITING_DIARY);
	}

	/**
	 * Update UI
	 */
	private void updateUI(Diary editingDiary) {
		mCollapsingToolbarLayout.setTitle(editingDiary.title);

		Bitmap coverImage = editingDiary.coverImage;
		if (coverImage == null) {
			// set placeholder image
			mCoverImageView.setImageDrawable(
					getResources().getDrawable(R.drawable.im_landscape_placeholder, getTheme())
			);
		} else {
			// set appropriate size for bitmap
			int nh = (int) (coverImage.getHeight() * (512.0 / coverImage.getWidth()));
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(coverImage, 512, nh, true);
			mCoverImageView.setImageBitmap(scaledBitmap);
		}
	}

	/**
	 * Edit Diary Dialog: onSelectCoverImageClicked();
	 * <p>
	 * Opens a launcher for selecting an image from gallery. The image is immediately set as the
	 * cover image of the diary.
	 */
	private void editDiaryOnSelectCoverImageClicked() {
		Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
		getContentIntent.setType("image/*");
		Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickIntent.setType("image/*");
		Intent chooserIntent = Intent.createChooser(getContentIntent, "Select Cover Image");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
		startActivityForResult(chooserIntent, SELECT_COVER_IMAGE_REQUEST);
	}

	/**
	 * Edit Diary Dialog: onRemoveCoverImageClicked();
	 * <p>
	 * Removes the cover image. The ui is also updated immediately.
	 */
	private void editDiaryOnRemoveCoverImageClicked() {
		STATIC_EDITING_DIARY.coverImage = null;
		updateUI(STATIC_EDITING_DIARY);
	}

	/**
	 * Edit Diary Dialog: onVisibilitySelected(...);
	 */
	private void editDiaryOnVisibilitySelected(PostVisibility visibility) {
		Log.d(TAG, String.format("editDiaryOnVisibilitySelected()::Visibility: %s", visibility));
		STATIC_EDITING_DIARY.visibility = visibility;
	}

	/**
	 * Edit Diary Dialog: onSave(...);
	 */
	private void editDiaryOnSave(String updatedTitle) {
		STATIC_EDITING_DIARY.title = updatedTitle;
		updateUI(STATIC_EDITING_DIARY);

		// if diary with the same id already exists, replace with current diary
		// else add this diary to the list
		for (Diary diary : Rove.STATIC_USER_DIARIES) {
			if (diary.diaryId.compareTo(STATIC_EDITING_DIARY.diaryId) == 0) {
				Rove.STATIC_USER_DIARIES.remove(diary);
				break;
			}
		}
		Rove.STATIC_USER_DIARIES.add(STATIC_EDITING_DIARY);
	}

	/**
	 * Edit Diary Dialog: onCancel();
	 */
	private void editDiaryOnCancel() {
		// if diary with the same id already exists, roll back to it
		// else roll back to a new diary
		Boolean found = false;
		for (Diary diary : Rove.STATIC_USER_DIARIES) {
			if (diary.diaryId.compareTo(STATIC_EDITING_DIARY.diaryId) == 0) {
				STATIC_EDITING_DIARY = diary;
				found = true;
				break;
			}
		}
		if (!found) {
			STATIC_EDITING_DIARY = new Diary();
		}
		updateUI(STATIC_EDITING_DIARY);
	}

	/**
	 * Make Post Dialog: onAddImageClicked();
	 */
	private void makePostOnAddImageClicked() {
		Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
		getContentIntent.setType("image/*");
		Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickIntent.setType("image/*");
		Intent chooserIntent = Intent.createChooser(getContentIntent, "Add Image to Post");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
		startActivityForResult(chooserIntent, ADD_IMAGE_REQUEST);
	}

	/**
	 * Make Post Dialog: onRemoveImagesClicked();
	 */
	private void makePostOnRemoveImagesClicked() {
		STATIC_EDITING_DIARY_POST.images.clear();
		// update the dialog ui, if dialog is showing
		if (mActiveMakePostDialog != null && mActiveMakePostDialog.isShowing()) {
			mActiveMakePostDialog.updateUI(STATIC_EDITING_DIARY_POST);
		}
	}

	/**
	 * Make Post Dialog: onCurrentLocationClicked();
	 */
	private void makePostOnCurrentLocationClicked() {

	}

	/**
	 * Make Post Dialog: onMapLocationClicked();
	 */
	private void makePostOnMapLocationClicked() {

	}

	/**
	 * Make Post Dialog: onRemoveLocationClicked();
	 */
	private void makePostOnRemoveLocationClicked() {
		STATIC_EDITING_DIARY_POST.taggedLocation = null;
		// update the dialog ui, if dialog is showing
		if (mActiveMakePostDialog != null && mActiveMakePostDialog.isShowing()) {
			mActiveMakePostDialog.updateUI(STATIC_EDITING_DIARY_POST);
		}
	}

	/**
	 * Make Post Dialog: onCancel();
	 */
	private void makePostOnCancel() {
		// if a post with the same id exists, roll back to it
		// else delete the post altogether
		Boolean found = false;
		for (DiaryPost post : STATIC_EDITING_DIARY.posts) {
			if (post.postId.compareTo(STATIC_EDITING_DIARY_POST.postId) == 0) {
				STATIC_EDITING_DIARY_POST = post;
				found = true;
				break;
			}
		}

		if (!found) {
			STATIC_EDITING_DIARY_POST = new DiaryPost();
		}

		// update the dialog ui, if dialog is showing
		if (mActiveMakePostDialog != null && mActiveMakePostDialog.isShowing()) {
			mActiveMakePostDialog.updateUI(STATIC_EDITING_DIARY_POST);
		}
	}

	/**
	 * Make Post Dialog: onSave(...);
	 */
	private void makePostOnSave(String textDescription) {
		// save text description
		STATIC_EDITING_DIARY_POST.textDescription = textDescription;

		// if a post with the same id exists, replace it
		// else add the post
		Boolean found = false;
		Integer index = -1;
		for (DiaryPost post : STATIC_EDITING_DIARY.posts) {
			if (post.postId.compareTo(STATIC_EDITING_DIARY_POST.postId) == 0) {
				found = true;
				index = STATIC_EDITING_DIARY.posts.indexOf(post);
				STATIC_EDITING_DIARY.posts.remove(post);
				break;
			}
		}

		if (found) {
			STATIC_EDITING_DIARY.posts.add(index, STATIC_EDITING_DIARY_POST);
			mDiaryItemsRecyclerView.getAdapter().notifyItemChanged(index);
			mDiaryItemsRecyclerView.scrollToPosition(index);
		} else {
			STATIC_EDITING_DIARY.posts.add(STATIC_EDITING_DIARY_POST);
			mDiaryItemsRecyclerView.getAdapter().notifyItemChanged(STATIC_EDITING_DIARY.posts.size());
			mDiaryItemsRecyclerView.scrollToPosition(STATIC_EDITING_DIARY.posts.size());
		}

		// update ui
		// update the dialog ui, if dialog is showing
		if (mActiveMakePostDialog != null && mActiveMakePostDialog.isShowing()) {
			mActiveMakePostDialog.updateUI(STATIC_EDITING_DIARY_POST);
		}
		updateUI(STATIC_EDITING_DIARY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == ADD_IMAGE_REQUEST) {
			if (data == null || data.getData() == null) {
				return;
			}

			try {
				InputStream inputStream = getContentResolver().openInputStream(data.getData());
				Bitmap decodedBitmap = BitmapFactory.decodeStream(inputStream);
				Log.i(TAG, String.format("decodedBitmap: %s", decodedBitmap.toString()));

				// add decoded bitmap to the post's bitmap array
				STATIC_EDITING_DIARY_POST.images.add(decodedBitmap);
				// update the dialog ui, if dialog is showing
				if (mActiveMakePostDialog != null && mActiveMakePostDialog.isShowing()) {
					mActiveMakePostDialog.updateUI(STATIC_EDITING_DIARY_POST);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (requestCode == SELECT_COVER_IMAGE_REQUEST) {
			if (data == null || data.getData() == null) {
				return;
			}

			try {
				InputStream inputStream = getContentResolver().openInputStream(data.getData());
				Bitmap decodedBitmap = BitmapFactory.decodeStream(inputStream);
				Log.i(TAG, String.format("decodedBitmap: %s", decodedBitmap.toString()));

				// set cover image as the decoded bitmap and update ui
				STATIC_EDITING_DIARY.coverImage = decodedBitmap;
				updateUI(STATIC_EDITING_DIARY);

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
			DiaryPost post = null;//Rove.STATIC_DIARY_POSTS.get(position);

			if (holder.descriptionTextView != null && !TextUtils.isEmpty(post.textDescription)) {
				holder.descriptionTextView.setText(post.textDescription);
			}

		}

		@Override
		public int getItemViewType(int position) {
			DiaryPost post = null;//Rove.STATIC_DIARY_POSTS.get(position);

			int type = 2;
			if (post.images == null || post.images.size() == 0) {
				type = 1;
			}
			if (post.taggedLocation == null) {
				type = 0;
			}
			return type;
		}

		@Override
		public int getItemCount() {
			return 0;//Rove.STATIC_DIARY_POSTS.size();
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
