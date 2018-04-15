package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.backend.PostRequestHandler;
import com.gursimransinghhanspal.rove.dialog.ForgotPassword;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CredentialsLogin extends AppCompatActivity {
    private static final String TAG = CredentialsLogin.class.getName();
    SharedPreferences pref;

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
		mForgotPasswordText = findViewById(R.id.activityLayout_credentialsLogin_forgotPasswordLink);
		mUsernameInputField = findViewById(R.id.activityLayout_credentialsLogin_usernameEditText);
		mPasswordInputField = findViewById(R.id.activityCredentialLogin_passwordInput);
		mNextButton = findViewById(R.id.btn_next);
        pref = getSharedPreferences("RovePref", MODE_PRIVATE);

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
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("userExtId", mUsernameInputField.getText().toString()));
        params.add(new BasicNameValuePair("password", mPasswordInputField.getText().toString()));
        PostRequestHandler requestHandler = new PostRequestHandler();
        JSONObject jsonResponse = requestHandler.getJSON("/user/login/", params);
        Log.d(TAG, "Response: " + jsonResponse);

        if (jsonResponse != null) {
            try {
                String jsonResponseMsg = jsonResponse.getString("msg");
                if (jsonResponse.getBoolean("res")) {
                    String user_id = jsonResponse.getString("user_id");

                    SharedPreferences.Editor edit = pref.edit();
                    //Storing Data using SharedPreferences
                    edit.putString("user_id", user_id);
                    edit.apply();

                    Intent credentialLoginActivityIntent = new Intent(this, ActivityHomeFeed.class);
                    startActivity(credentialLoginActivityIntent);

                    if (finishOnStart) {
                        finish();
                    }
                }
                Toast.makeText(getApplication(), jsonResponseMsg, Toast.LENGTH_LONG).show();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
