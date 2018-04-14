package com.gursimransinghhanspal.rove.misc;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.data.PostSocial;

import java.util.Locale;

public class SocialBar {

	private Context mContext;
	private PostSocial mSocialInfo;
	private SocialInteractionListener mInteractionListener;

	private View mSocialBarView;
	private ImageView mLikeIconImageView;
	private RelativeLayout mLikeBadgeRelativeLayout;
	private TextView mLikeBadgeTextView;
	private ImageView mBookmarkIconImageView;
	private RelativeLayout mBookmarkBadgeRelativeLayout;
	private TextView mBookmarkBadgeTextView;
	private ImageView mCommentIconImageView;
	private RelativeLayout mCommentBadgeRelativeLayout;
	private TextView mCommentBadgeTextView;


	public SocialBar(Context context, View socialBarView, PostSocial socialInfo, SocialInteractionListener interactionListener) {

		mSocialBarView = socialBarView;
		mSocialInfo = socialInfo;
		mInteractionListener = interactionListener;

		// like
		mLikeIconImageView = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_likeIconImageView);
		mLikeBadgeRelativeLayout = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_likeBadgeRelativeLayout);
		mLikeBadgeTextView = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_likeBadgeTextView);
		// bookmark
		mBookmarkIconImageView = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_bookmarkIconImageView);
		mBookmarkBadgeRelativeLayout = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_bookmarkBadgeRelativeLayout);
		mBookmarkBadgeTextView = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_bookmarkBadgeTextView);
		// comment
		mCommentIconImageView = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_commentIconImageView);
		mCommentBadgeRelativeLayout = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_commentBadgeRelativeLayout);
		mCommentBadgeTextView = mSocialBarView.findViewById(R.id.viewLayout_diarySocialBar_commentBadgeTextView);

		mLikeIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLiked();
			}
		});
		mBookmarkIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBookmarked();
			}
		});
		mCommentIconImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCommentClicked();
			}
		});

		updateView(mSocialInfo);
	}

	private void onLiked() {
		mSocialInfo.doesUserLike = !mSocialInfo.doesUserLike;
		if (mSocialInfo.doesUserLike)
			mSocialInfo.numLikes += 1;
		else
			mSocialInfo.numLikes -= 1;

		updateView(mSocialInfo);

		if (mInteractionListener != null) {
			mInteractionListener.onSocialInfoUpdated(mSocialInfo);
		}
	}

	private void onBookmarked() {
		mSocialInfo.didUserBookmark = !mSocialInfo.didUserBookmark;
		if (mSocialInfo.didUserBookmark)
			mSocialInfo.numBookmarks += 1;
		else
			mSocialInfo.numBookmarks -= 1;

		updateView(mSocialInfo);

		if (mInteractionListener != null) {
			mInteractionListener.onSocialInfoUpdated(mSocialInfo);
		}
	}

	private void onCommentClicked() {

	}

	public void updateView(PostSocial updatedInfo) {
		mSocialInfo = updatedInfo;

		if (updatedInfo.doesUserLike) {
			mLikeIconImageView.setImageDrawable(
					mContext.getResources().getDrawable(
							R.drawable.ic_favorite_filled,
							mContext.getTheme()
					)
			);
			mLikeIconImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.socialBar_likeColor));
		} else {
			mLikeIconImageView.setImageDrawable(
					mContext.getResources().getDrawable(
							R.drawable.ic_favorite_border,
							mContext.getTheme()
					)
			);
			mLikeIconImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.socialBar_inactiveViewColor));
		}

		if (updatedInfo.didUserBookmark) {
			mBookmarkIconImageView.setImageDrawable(
					mContext.getResources().getDrawable(
							R.drawable.ic_bookmark_filled,
							mContext.getTheme()
					)
			);
			mLikeIconImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.socialBar_bookmarkColor));
		} else {
			mBookmarkIconImageView.setImageDrawable(
					mContext.getResources().getDrawable(
							R.drawable.ic_bookmark_filled,
							mContext.getTheme()
					)
			);
			mLikeIconImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.socialBar_inactiveViewColor));
		}

		if (updatedInfo.numLikes == 0) {
			mLikeBadgeRelativeLayout.setVisibility(View.GONE);
		} else {
			mLikeBadgeRelativeLayout.setVisibility(View.VISIBLE);
			mLikeBadgeTextView.setText(
					String.format(Locale.US, "%d", updatedInfo.numLikes)
			);
		}

		if (updatedInfo.numBookmarks == 0) {
			mBookmarkBadgeRelativeLayout.setVisibility(View.GONE);
		} else {
			mBookmarkBadgeRelativeLayout.setVisibility(View.VISIBLE);
			mBookmarkBadgeTextView.setText(
					String.format(Locale.US, "%d", updatedInfo.numBookmarks)
			);
		}
	}

	public interface SocialInteractionListener {
		void onSocialInfoUpdated(PostSocial updatedInfo);
	}
}
