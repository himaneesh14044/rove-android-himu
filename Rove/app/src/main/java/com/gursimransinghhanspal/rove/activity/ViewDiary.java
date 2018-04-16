package com.gursimransinghhanspal.rove.activity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.data.Diary;
import com.gursimransinghhanspal.rove.data.DiaryPost;
import com.gursimransinghhanspal.rove.data.PostSocial;
import com.gursimransinghhanspal.rove.fragment.ImageTile;
import com.gursimransinghhanspal.rove.misc.PostTemplateType;
import com.gursimransinghhanspal.rove.misc.Rove;
import com.gursimransinghhanspal.rove.misc.SocialBar;

import java.util.ArrayList;

public class ViewDiary extends AppCompatActivity {

	private static final String TAG = "ViewDiary";
	public static final String DB_DIARY_ID = "com.gursimransinghhanspal.rove.activity.ViewDiary.DiaryId";

	// the diary and the post currently being edited
	private static Diary VIEWING_DIARY;

	//
	private String dbDiaryId;

	//
	private CollapsingToolbarLayout mCollapsingToolbarLayout;
	private RecyclerView mDiaryItemsRecyclerView;
	private ImageView mCoverImageView;

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

		// register ui components
		mCollapsingToolbarLayout = findViewById(R.id.activityLayout_makeDiary_collapsingToolbarLayout);
		mCoverImageView = findViewById(R.id.activityLayout_makeDiary_collapsingToolbar_bgImageView);
		FloatingActionButton toolbarFAB = findViewById(R.id.activityLayout_makeDiary_collapsingToolbar_editFAB);
		toolbarFAB.setVisibility(View.GONE);

		mDiaryItemsRecyclerView = findViewById(R.id.activityLayout_makeDiary_diaryItemsRecyclerView);

		// set onClick listeners
		FloatingActionButton mainFAB = findViewById(R.id.activityLayout_makeDiary_addDiaryItemFAB);
		mainFAB.setVisibility(View.GONE);

		// instantiate location client
		MapsInitializer.initialize(this);
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
			int maxHeight = 1024;
			int maxWidth = 1024;
			float scale = Math.min(((float) maxHeight / coverImage.getWidth()), ((float) maxWidth / coverImage.getHeight()));

			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			Bitmap scaledBitmap = Bitmap.createBitmap(coverImage, 0, 0, coverImage.getWidth(), coverImage.getHeight(), matrix, true);
			mCoverImageView.setImageBitmap(scaledBitmap);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// TODO: get updated diary from server
		// TODO: get diary from server here into STATIC_EDITING_DIARY
		// search if diary exists
		this.VIEWING_DIARY = new Diary();
		for (Diary diary :
				Rove.STATIC_USER_DIARIES) {
			if (diary.diaryId.compareTo(dbDiaryId) == 0) {
				VIEWING_DIARY = diary;
				break;
			}
		}

		mDiaryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mDiaryItemsRecyclerView.setAdapter(new RecyclerAdapter(VIEWING_DIARY));
		mDiaryItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());

		// update the UI
		updateUI(VIEWING_DIARY);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// TODO: send updated diary back to server
	}

	private void setupImageViewPager(ViewPager viewPagerReference, DiaryPost diaryPost) {
		PagerAdapter pagerAdapter = new ViewDiary.ImageTilePageAdapter(getSupportFragmentManager(), diaryPost.images);
		if (viewPagerReference != null) {
			viewPagerReference.setAdapter(pagerAdapter);
		}
	}

	class RecyclerAdapter extends RecyclerView.Adapter<ViewDiary.RecyclerAdapter.ViewHolder> {

		private Diary mReferencedDiary;

		RecyclerAdapter(Diary referencedDiary) {
			mReferencedDiary = referencedDiary;
		}

		@NonNull
		@Override
		public ViewDiary.RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View itemView = null;
			if (viewType == PostTemplateType.TEXT_TEMPLATE.intValue) {
				itemView = LayoutInflater.from(parent.getContext())
						.inflate(R.layout.recycler_item_diary_text_template, parent, false);
			} else if (viewType == PostTemplateType.LOCATION_TEMPLATE.intValue) {
				itemView = LayoutInflater.from(parent.getContext())
						.inflate(R.layout.recycler_item_diary_location_template, parent, false);
			} else if (viewType == PostTemplateType.IMAGE_TEMPLATE.intValue) {
				itemView = LayoutInflater.from(parent.getContext())
						.inflate(R.layout.recycler_item_diary_image_template, parent, false);
			}
			return new ViewDiary.RecyclerAdapter.ViewHolder(itemView, viewType);
		}

		@Override
		public void onBindViewHolder(@NonNull final ViewDiary.RecyclerAdapter.ViewHolder holder, int position) {
			final DiaryPost post = mReferencedDiary.posts.get(position);

			// setup social bar
			SocialBar socialBarControl = new SocialBar(
					ViewDiary.this,
					holder.mSocialBarView,
					post,
					new SocialBar.SocialInteractionListener() {
						@Override
						public void onSocialInfoUpdated(PostSocial updatedInfo) {
							// TODO: send updated info to server here
							// String id = post.postId;
						}
					}
			);

			switch (post.getTemplateType()) {
				case TEXT_TEMPLATE:
					// setup text description
					if (!TextUtils.isEmpty(post.textDescription)) {
						holder.txt_descriptionTextView.setVisibility(View.VISIBLE);
						holder.txt_descriptionTextView.setText(post.textDescription);
					} else {
						holder.txt_descriptionTextView.setVisibility(View.GONE);
					}
					break;

				case LOCATION_TEMPLATE:
					// setup map view
					holder.loc_locationMapView.onCreate(Bundle.EMPTY);
					holder.loc_locationMapView.getMapAsync(new OnMapReadyCallback() {
						@Override
						public void onMapReady(GoogleMap googleMap) {
							googleMap.addMarker(new MarkerOptions()
									.position(new LatLng(post.taggedLocation.getLatitude(), post.taggedLocation.getLongitude())));
							googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(post.taggedLocation.getLatitude(), post.taggedLocation.getLongitude()), 15));
							// googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 1000, null);

							// some ui settings for map
							holder.loc_locationMapView.setClickable(false);
							googleMap.getUiSettings().setMyLocationButtonEnabled(false);
							googleMap.getUiSettings().setZoomControlsEnabled(false);
							googleMap.getUiSettings().setCompassEnabled(false);
							googleMap.getUiSettings().setZoomGesturesEnabled(false);
							googleMap.getUiSettings().setScrollGesturesEnabled(false);
						}
					});

					// setup location info
					holder.loc_locationInfoTextView.setText(post.getLocationLongName());

					// setup text description
					if (!TextUtils.isEmpty(post.textDescription)) {
						holder.loc_descriptionTextView.setVisibility(View.VISIBLE);
						holder.loc_descriptionTextView.setText(post.textDescription);
					} else {
						holder.loc_descriptionTextView.setVisibility(View.GONE);
					}

					break;

				case IMAGE_TEMPLATE:
					// setup image view pager
					if (post.images.size() > 0) {
						holder.img_imagesViewPager.setVisibility(View.VISIBLE);
						setupImageViewPager(holder.img_imagesViewPager, post);
					} else {
						holder.img_imagesViewPager.setVisibility(View.GONE);
					}

					// setup location tag
					if (post.taggedLocation != null) {
						holder.img_locationLinearLayout.setVisibility(View.VISIBLE);
						holder.img_locationTextView.setText(String.format("%s %s", "Taken at", post.getLocationShortName()));
					} else {
						holder.img_locationLinearLayout.setVisibility(View.GONE);
					}

					// setup text description
					if (!TextUtils.isEmpty(post.textDescription)) {
						holder.img_descriptionTextView.setVisibility(View.VISIBLE);
						holder.img_descriptionTextView.setText(post.textDescription);
					} else {
						holder.img_descriptionTextView.setVisibility(View.GONE);
					}

					break;

				default:
					break;
			}
		}

		@Override
		public int getItemViewType(int position) {
			DiaryPost post = mReferencedDiary.posts.get(position);
			return post.getTemplateType().intValue;
		}

		@Override
		public int getItemCount() {
			return mReferencedDiary.posts.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder {

			FrameLayout mSocialBarView;

			TextView txt_descriptionTextView;

			MapView loc_locationMapView;
			TextView loc_locationInfoTextView;
			TextView loc_openMapTextView;
			TextView loc_descriptionTextView;

			ViewPager img_imagesViewPager;
			LinearLayout img_locationLinearLayout;
			TextView img_locationTextView;
			TextView img_descriptionTextView;


			public ViewHolder(View itemView, int type) {
				super(itemView);

				mSocialBarView = itemView.findViewById(R.id.viewLayout_diarySocialBar_rootFrameLayout);

				if (type == PostTemplateType.TEXT_TEMPLATE.intValue) {
					txt_descriptionTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryTextTemplate_noteTextView);
				}
				if (type == PostTemplateType.LOCATION_TEMPLATE.intValue) {
					loc_locationMapView = itemView.findViewById(R.id.recyclerItemLayout_diaryLocationTemplate_locationMapView);
					loc_locationInfoTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryLocationTemplate_locationInfoTextView);
					loc_openMapTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryLocationTemplate_openMapTextView);
					loc_descriptionTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryLocationTemplate_noteTextView);
				}
				if (type == PostTemplateType.IMAGE_TEMPLATE.intValue) {
					img_imagesViewPager = itemView.findViewById(R.id.recyclerItemLayout_diaryImageTemplate_imagesViewPager);
					img_locationLinearLayout = itemView.findViewById(R.id.recyclerItemLayout_diaryImageTemplate_locationLinearLayout);
					img_locationTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryImageTemplate_locationTextView);
					img_descriptionTextView = itemView.findViewById(R.id.recyclerItemLayout_diaryImageTemplate_noteTextView);
				}
			}
		}
	}

	private class ImageTilePageAdapter extends FragmentStatePagerAdapter {

		ArrayList<Bitmap> mImages;

		public ImageTilePageAdapter(FragmentManager fm, ArrayList<Bitmap> images) {
			super(fm);
			mImages = images;
		}

		@Override
		public Fragment getItem(int position) {
			return ImageTile.newInstance(mImages.get(position));
		}

		@Override
		public int getCount() {
			return mImages.size();
		}
	}
}
