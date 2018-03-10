package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.fragment.SignUpEmail;
import com.gursimransinghhanspal.rove.fragment.SignUpName;
import com.gursimransinghhanspal.rove.fragment.SignUpPassword;
import com.gursimransinghhanspal.rove.fragment.SignUpReviewInfo;


public class CreateAccount extends AppCompatActivity {

	private View mStatusBarUnderlay;
	private View mNavBarUnderlay;
	private FrameLayout mFragmentContainer;
	private Fragment mActiveFragment;

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

		initializeSavedInfo();
		showFragment(FragmentIdentifier.Name);
	}

	@Override
	public void onBackPressed() {
		switchActivityToLoginMain(true);
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

	private void initializeSavedInfo() {
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_firstName), "");
		editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_lastName), "");
		editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_email), "");
		editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_password), "");
		editor.apply();
	}

	private void switchActivityToHome(boolean finishOnStart) {
		/*
		Intent credentialLoginActivityIntent = new Intent(this, CredentialLogin.class);
		startActivity(credentialLoginActivityIntent);

		if (finishOnStart) {
			finish();
		}
		*/
	}

	private void switchActivityToLoginMain(boolean finishOnStart) {
		Intent loginMainActivityIntent = new Intent(this, LoginMain.class);
		startActivity(loginMainActivityIntent);

		if (finishOnStart) {
			finish();
		}
	}

	private void showFragment(FragmentIdentifier fragmentIdentifier) {
		FragmentManager manager = getSupportFragmentManager();

		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		String firstName = preferences.getString(
				getResources().getString(R.string.activityCreateAccount_savedInfo_firstName), ""
		);
		String lastName = preferences.getString(
				getResources().getString(R.string.activityCreateAccount_savedInfo_lastName), ""
		);
		String email = preferences.getString(
				getResources().getString(R.string.activityCreateAccount_savedInfo_email), ""
		);
		String password = preferences.getString(
				getResources().getString(R.string.activityCreateAccount_savedInfo_password), ""
		);

		switch (fragmentIdentifier) {
			case Name:
				SignUpName signUpName = SignUpName.newInstance(FragmentIdentifier.Name, firstName, lastName);
				signUpName.setOnInteractionListener(new SignUpName.OnFragmentInteractionListener() {
					@Override
					public void onBackButtonPressed(FragmentIdentifier identifier, String firstName, String lastName) {
						switchActivityToLoginMain(true);
					}

					@Override
					public void onNextButtonPressed(FragmentIdentifier identifier, String firstName, String lastName) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_firstName), firstName);
						editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_lastName), lastName);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Email);
					}
				});
				mActiveFragment = signUpName;
				break;

			case Email:
				SignUpEmail signUpEmail = SignUpEmail.newInstance(FragmentIdentifier.Email, email);
				signUpEmail.setOnInteractionListener(new SignUpEmail.OnFragmentInteractionListener() {
					@Override
					public void onBackButtonPressed(FragmentIdentifier identifier, String email) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_email), email);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Name);
					}

					@Override
					public void onNextButtonPressed(FragmentIdentifier identifier, String email) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_email), email);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Password);
					}
				});
				mActiveFragment = signUpEmail;
				break;

			case Password:
				SignUpPassword signUpPassword = SignUpPassword.newInstance(FragmentIdentifier.Password, password);
				signUpPassword.setOnInteractionListener(new SignUpPassword.OnFragmentInteractionListener() {
					@Override
					public void onBackButtonPressed(FragmentIdentifier identifier, String password) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_password), password);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Email);
					}

					@Override
					public void onNextButtonPressed(FragmentIdentifier identifier, String password) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activityCreateAccount_savedInfo_password), password);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Review);
					}
				});
				mActiveFragment = signUpPassword;
				break;

			case Review:
				SignUpReviewInfo signUpReviewInfo = SignUpReviewInfo.newInstance(FragmentIdentifier.Review, firstName, lastName, email);
				signUpReviewInfo.setOnInteractionListener(new SignUpReviewInfo.OnFragmentInteractionListener() {
					@Override
					public void onBackButtonPressed(FragmentIdentifier identifier) {
						CreateAccount.this.showFragment(FragmentIdentifier.Password);
					}

					@Override
					public void onCompleteSignUpButtonPressed(FragmentIdentifier identifier) {
						switchActivityToHome(true);
					}
				});
				mActiveFragment = signUpReviewInfo;
				break;
		}

		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(mFragmentContainer.getId(), mActiveFragment);
		transaction.commit();
	}

	public enum FragmentIdentifier {
		Name,
		Email,
		Password,
		Review
	}
}
