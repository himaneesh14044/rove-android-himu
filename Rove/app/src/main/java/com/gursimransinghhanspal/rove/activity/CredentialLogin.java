package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.dialog.ForgotPassword;

public class CredentialLogin extends AppCompatActivity {

	private View mStatusBarUnderlay;
	private View mNavBarUnderlay;
	private View mBackButton;
	private View mNextButton;
	private TextView mForgotPasswordText;
	private EditText mUsernameInputField;
	private EditText mPasswordInputField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credentials_login);

		// register ui elements
		mStatusBarUnderlay = findViewById(R.id.view_statusbar_underlay);
		mNavBarUnderlay = findViewById(R.id.view_navbar_underlay);
		mBackButton = findViewById(R.id.btn_back);
		mForgotPasswordText = findViewById(R.id.activityCredentialLogin_forgotPasswordText);
		mUsernameInputField = findViewById(R.id.activityCredentialLogin_usernameInput);
		mPasswordInputField = findViewById(R.id.activityCredentialLogin_passwordInput);
		mNextButton = findViewById(R.id.btn_next);

		// register onClick events
		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchActivityToLoginMain(true);
			}
		});
		mForgotPasswordText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showForgotPasswordDialog();
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchActivityToHome(true);
			}
		});

		CreateAccount.setupUnderlayViews(this, mStatusBarUnderlay, mNavBarUnderlay);
	}

	@Override
	public void onBackPressed() {
		switchActivityToLoginMain(true);
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

	private void showForgotPasswordDialog() {
		ForgotPassword dialog = new ForgotPassword(this, true, null);
		dialog.show();
	}
}
