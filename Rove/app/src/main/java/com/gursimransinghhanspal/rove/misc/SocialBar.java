package com.gursimransinghhanspal.rove.misc;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.data.DiaryPost;
import com.gursimransinghhanspal.rove.data.PostSocial;

import java.util.Locale;

public class SocialBar {

	private Context mContext;
	private DiaryPost mDiaryPost;
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


	public SocialBar(Context context, View socialBarView, DiaryPost post, SocialInteractionListener interactionListener) {

		mSocialBarView = socialBarView;
		mDiaryPost = post;
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

		updateView(mDiaryPost);
	}

	private void onLiked() {
		mDiaryPost.socialInfo.doesUserLike = !mDiaryPost.socialInfo.doesUserLike;
		if (mDiaryPost.socialInfo.doesUserLike)
			mDiaryPost.socialInfo.numLikes += 1;
		else
			mDiaryPost.socialInfo.numLikes -= 1;

		updateView(mDiaryPost);

		if (mInteractionListener != null) {
			mInteractionListener.onSocialInfoUpdated(mDiaryPost.socialInfo);
		}
	}

	private void onBookmarked() {
		mDiaryPost.socialInfo.didUserBookmark = !mDiaryPost.socialInfo.didUserBookmark;
		if (mDiaryPost.socialInfo.didUserBookmark)
			mDiaryPost.socialInfo.numBookmarks += 1;
		else
			mDiaryPost.socialInfo.numBookmarks -= 1;

		updateView(mDiaryPost);

		if (mInteractionListener != null) {
			mInteractionListener.onSocialInfoUpdated(mDiaryPost.socialInfo);
		}
	}

	private void onCommentClicked() {

	}

	public void updateView(DiaryPost diaryPost) {
		mDiaryPost = diaryPost;

		if (diaryPost.socialInfo.doesUserLike) {
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

		if (diaryPost.socialInfo.didUserBookmark) {
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

		if (diaryPost.socialInfo.numLikes == 0) {
			mLikeBadgeRelativeLayout.setVisibility(View.GONE);
		} else {
			mLikeBadgeRelativeLayout.setVisibility(View.VISIBLE);
			mLikeBadgeTextView.setText(
					String.format(Locale.US, "%d", diaryPost.socialInfo.numLikes)
			);
		}

		if (diaryPost.socialInfo.numBookmarks == 0) {
			mBookmarkBadgeRelativeLayout.setVisibility(View.GONE);
		} else {
			mBookmarkBadgeRelativeLayout.setVisibility(View.VISIBLE);
			mBookmarkBadgeTextView.setText(
					String.format(Locale.US, "%d", diaryPost.socialInfo.numBookmarks)
			);
		}
	}

	public interface SocialInteractionListener {
		void onSocialInfoUpdated(PostSocial updatedInfo);
	}
}
