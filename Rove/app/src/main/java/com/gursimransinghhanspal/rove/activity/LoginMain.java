package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gursimransinghhanspal.rove.R;

import java.util.Arrays;

public class LoginMain extends AppCompatActivity {

	private static final String sFacebookEmailPermissionName = "email";
	private static final String sFacebookPublicProfilePermissionName = "public_profile";

	private View mStatusBarUnderlay;
	private View mNavBarUnderlay;
	private LoginButton mFacebookLoginButton;
	private CallbackManager mFacebookCallbackManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_main);

		// register ui items
		mStatusBarUnderlay = findViewById(R.id.view_statusbar_underlay);
		mNavBarUnderlay = findViewById(R.id.view_navbar_underlay);
		RelativeLayout mCredentialLoginButton = findViewById(R.id.activityLayout_loginMain_credentialsLoginButton);
		mFacebookLoginButton = findViewById(R.id.activityLoginMain_facebookLoginButton);
		LinearLayout mCustomFacebookLoginButton = findViewById(R.id.activityLoginMain_customFacebookLoginButton);
		RelativeLayout mCreateAccountButton = findViewById(R.id.activityLoginMain_createAccountButton);
		TextView mSkipLoginText = findViewById(R.id.activityLoginMain_skipLoginText);

		// register onClick actions
		mCredentialLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchActivityToCredentialLogin(true);
			}
		});
		mCustomFacebookLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFacebookLoginButton.performClick();
			}
		});
		mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchActivityToCreateAccount(true);
			}
		});
		mSkipLoginText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: implement something here
			}
		});

		// setup other stuff
		CreateAccount.setupUnderlayViews(this, mStatusBarUnderlay, mNavBarUnderlay);
		setupFacebookLoginFlow();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static void facebookLogout() {
		AccessToken facebookAccessToken = AccessToken.getCurrentAccessToken();
		String facebookUserId = facebookAccessToken.getUserId();

		GraphRequest deleteFacebookPermissionsRequest = new GraphRequest(
				AccessToken.getCurrentAccessToken(),
				String.format("/%s/permissions/", facebookUserId),
				null,
				HttpMethod.DELETE,
				new GraphRequest.Callback() {
					@Override
					public void onCompleted(GraphResponse graphResponse) {
						// do nothing!
					}
				}
		);
		deleteFacebookPermissionsRequest.executeAsync();
		LoginManager.getInstance().logOut();
	}

	private void switchActivityToCredentialLogin(boolean finishOnStart) {
		Intent credentialLoginActivityIntent = new Intent(this, CredentialsLogin.class);
		startActivity(credentialLoginActivityIntent);

		if (finishOnStart) {
			finish();
		}
	}

	private void switchActivityToCreateAccount(boolean finishOnStart) {
		Intent createAccountActivityIntent = new Intent(this, CreateAccount.class);
		startActivity(createAccountActivityIntent);

		if (finishOnStart) {
			finish();
		}
	}

	private void setupFacebookLoginFlow() {
		// callback manager
		mFacebookCallbackManager = CallbackManager.Factory.create();

		// callback registration
		mFacebookLoginButton.setReadPermissions(Arrays.asList(sFacebookEmailPermissionName, sFacebookPublicProfilePermissionName));
		mFacebookLoginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				facebookLogout();
				//Intent intent = new Intent(getApplicationContext(),ActivityHomeFeed.class);
				//startActivity(intent);
			}

			@Override
			public void onCancel() {
				// App code
			}

			@Override
			public void onError(FacebookException exception) {
				// App code
			}
		});
	}
}
