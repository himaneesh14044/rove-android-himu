package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// start activity - `LoginPrimary`
		Intent loginViaFacebookActivityIntent = new Intent(this, LoginPrimary.class);
		startActivity(loginViaFacebookActivityIntent);
		// also call finish() to remove `this` activity from backstack
		finish();
	}
}
