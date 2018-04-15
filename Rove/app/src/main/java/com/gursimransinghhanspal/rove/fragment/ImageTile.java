package com.gursimransinghhanspal.rove.fragment;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gursimransinghhanspal.rove.R;

public class ImageTile extends Fragment {
	private static final String ARG_BITMAP = "com.gursimransinghhanspal.rove.fragment.ImageTile.bitmap";
	private Bitmap mScaledBitmap;

	private FrameLayout mRootFrameLayout;
	private ImageView mImageView;

	public ImageTile() {
		// Required empty public constructor
	}

	public static ImageTile newInstance(Bitmap bitmap) {
		ImageTile fragment = new ImageTile();
		Bundle args = new Bundle();
		args.putParcelable(ARG_BITMAP, bitmap);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			Bitmap fullBitmap = getArguments().getParcelable(ARG_BITMAP);
			if (fullBitmap != null) {
				int maxHeight = 1024;
				int maxWidth = 1024;
				float scale = Math.min(((float) maxHeight / fullBitmap.getWidth()), ((float) maxWidth / fullBitmap.getHeight()));

				Matrix matrix = new Matrix();
				matrix.postScale(scale, scale);
				mScaledBitmap = Bitmap.createBitmap(fullBitmap, 0, 0, fullBitmap.getWidth(), fullBitmap.getHeight(), matrix, true);
			}
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_image_tile, container, false);
		mRootFrameLayout = fragmentView.findViewById(R.id.fragmentLayout_imageTile_rootFrameLayout);
		mImageView = fragmentView.findViewById(R.id.fragmentLayout_imageTile_imageView);

		// set bitmap in imageView
		if (mScaledBitmap != null) {
			mImageView.setImageBitmap(mScaledBitmap);
		}
		return fragmentView;
	}
}
