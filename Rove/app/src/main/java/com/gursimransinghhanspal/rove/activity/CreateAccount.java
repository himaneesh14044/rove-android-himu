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
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.backend.PostRequestHandler;
import com.gursimransinghhanspal.rove.fragment.SignUpEmail;
import com.gursimransinghhanspal.rove.fragment.SignUpName;
import com.gursimransinghhanspal.rove.fragment.SignUpPassword;
import com.gursimransinghhanspal.rove.fragment.SignUpReviewInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CreateAccount extends AppCompatActivity {
    private static final String TAG = CreateAccount.class.getName();

	private View mStatusBarUnderlay;
	private View mNavBarUnderlay;
	private FrameLayout mFragmentContainer;
	private Fragment mActiveFragment;
    SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		// register ui elements
		mStatusBarUnderlay = findViewById(R.id.view_statusbar_underlay);
		mNavBarUnderlay = findViewById(R.id.view_navbar_underlay);
		mFragmentContainer = findViewById(R.id.activityLayout_createAccount_fragmentContainerFrameLayout);
        pref = getSharedPreferences("RovePref", MODE_PRIVATE);

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
		editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_firstName), "");
		editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_lastName), "");
		editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_email), "");
		editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_password), "");
		editor.apply();
	}

	private void switchActivityToHome(boolean finishOnStart) {
		Intent credentialLoginActivityIntent = new Intent(this, CredentialsLogin.class);
		startActivity(credentialLoginActivityIntent);

		if (finishOnStart) {
			finish();
		}
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
		final String firstName = preferences.getString(
				getResources().getString(R.string.activity_createAccount_savedInfoKey_firstName), ""
		);
		final String lastName = preferences.getString(
				getResources().getString(R.string.activity_createAccount_savedInfoKey_lastName), ""
		);
		final String email = preferences.getString(
				getResources().getString(R.string.activity_createAccount_savedInfoKey_email), ""
		);
		final String password = preferences.getString(
				getResources().getString(R.string.activity_createAccount_savedInfoKey_password), ""
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
						editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_firstName), firstName);
						editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_lastName), lastName);
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
						editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_email), email);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Name);
					}

					@Override
					public void onNextButtonPressed(FragmentIdentifier identifier, String email) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_email), email);
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
						editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_password), password);
						editor.apply();
						CreateAccount.this.showFragment(FragmentIdentifier.Email);
					}

					@Override
					public void onNextButtonPressed(FragmentIdentifier identifier, String password) {
						SharedPreferences preferences = getPreferences(MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(getResources().getString(R.string.activity_createAccount_savedInfoKey_password), password);
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
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("userExtId", email));
						params.add(new BasicNameValuePair("password", password));
                        PostRequestHandler requestHandler = new PostRequestHandler();
                        JSONObject jsonResponse = requestHandler.getJSON("/user/signup/", params);
                        Log.d(TAG, "Sign up Response: " + jsonResponse);

                        if (jsonResponse != null) {
                            try {
                                String jsonResponseMsg = jsonResponse.getString("msg");
                                if (jsonResponse.getBoolean("res")) {
                                    String user_id = jsonResponse.getString("user_id");
                                    SharedPreferences.Editor edit = pref.edit();
                                    //Storing Data using SharedPreferences
                                    edit.putString("user_id", user_id);
                                    edit.apply();

                                    params = new ArrayList<>();
									params.add(new BasicNameValuePair("userId", user_id));
									params.add(new BasicNameValuePair("firstName", firstName));
									params.add(new BasicNameValuePair("lastName", lastName));
									params.add(new BasicNameValuePair("isProfilePicPresent", "false"));

									jsonResponse = requestHandler.getJSON("/profile/", params);
									Log.d(TAG, "Profile Response: " + jsonResponse);

									if (jsonResponse != null) {
										try {
											if (jsonResponse.getBoolean("res")) {
												switchActivityToHome(true);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
                                }
                                Toast.makeText(getApplication(), jsonResponseMsg, Toast.LENGTH_LONG).show();
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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
