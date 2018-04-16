package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

//	private ImageView mAppLogo;
//	private TextView mAppName;
//	private TextView mAppDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_splash);

		// register ui elements
//		mAppLogo = findViewById(R.id.activitySplash_appLogo_imageView);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// start activity - `LoginMain`
		Intent loginMainActivityIntent = new Intent(this, LoginMain.class);
		startActivity(loginMainActivityIntent);
		// also call finish() to remove `this` activity from backstack

//		Intent makeDiaryActivityIntent = new Intent(this, MakeDiary.class);
//		makeDiaryActivityIntent.putExtra(MakeDiary.DB_DIARY_ID, "1");
//		startActivity(makeDiaryActivityIntent);
		finish();
	}

	private void animate() {

	}
}
