package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gursimransinghhanspal.rove.R;

public class CreateAccount extends AppCompatActivity {

	private View mStatusBarUnderlay;
	private View mNavBarUnderlay;
	private FrameLayout mFragmentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		// register ui elements
		mStatusBarUnderlay = findViewById(R.id.view_statusbar_underlay);
		mNavBarUnderlay = findViewById(R.id.view_navbar_underlay);
		mFragmentContainer = findViewById(R.id.activityCreateAccount_fragmentContainer_frameLayout);

		// setup
		setupUnderlayViews(this, mStatusBarUnderlay, mNavBarUnderlay);
	}

	public static void setupUnderlayViews(Context context, View statusBarUnderlay, View navBarUnderlay) {
		Resources resources = context.getResources();

		int navbarHeightDimenId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		int statusbarHeightDimenId = resources.getIdentifier("status_bar_height", "dimen", "android");

		if (statusbarHeightDimenId > 0) {
			float height = resources.getDimensionPixelSize(statusbarHeightDimenId);
			statusBarUnderlay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(height)));
		} else {
			statusBarUnderlay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
		}

		if (navbarHeightDimenId > 0) {
			float height = resources.getDimensionPixelSize(navbarHeightDimenId);
			navBarUnderlay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(height)));
		} else {
			navBarUnderlay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
		}
	}


}
