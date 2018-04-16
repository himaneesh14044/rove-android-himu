package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.Trip;
import com.gursimransinghhanspal.rove.backend.GetRequestHandler;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ActivityHomeFeed extends AppCompatActivity {
    private static final String TAG = ActivityHomeFeed.class.getName();

    static List<Trip> tripList;
    RecyclerView recyclerView;

    @Override
    public void onBackPressed()
    {
        //Toast.makeText(getApplicationContext(),"BackButton is pressed",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    protected void onRestart()
    {
        //Toast.makeText(getApplicationContext(),"Restart is happending",Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onPause()
    {
        //Toast.makeText(getApplicationContext(),"Pause is happending",Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        //Toast.makeText(getApplicationContext(),"OnStop is happending",Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        //Toast.makeText(getApplicationContext(),"on restore is pressed",Toast.LENGTH_SHORT).show();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Toast.makeText(getApplicationContext(),"OnCreate is pressed",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed);

        FloatingActionButton createDiaryButton = findViewById(R.id.create_diary_button);
        createDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	// TODO: create a new diary on server and pass the id of the diary in intent
                Intent intent = new Intent(ActivityHomeFeed.this, MakeDiary.class);
                intent.putExtra(MakeDiary.DIARY_ID, UUID.randomUUID().toString());
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripList = fetchDiaries();

        FeedAdapter adapter = new FeedAdapter(this,tripList);
        recyclerView.setAdapter(adapter);
        ImageButton imageButton = findViewById(R.id.ImageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BookMark.class);
                startActivity(intent);
            }
        });

        /*ImageButton imageButton1 = (ImageButton) findViewById(R.id.NotificationButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NotificationsActivity.class);
                startActivity(intent);
            }
        });*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch(item.getItemId())
                {
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_search:
                        Intent intent = new Intent(getApplicationContext(), Search.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        Intent userintent = new Intent(getApplicationContext(),UserAccount.class);
                        startActivity(userintent);
                        return true;
                }
                return false;
            }
        });
        ImageButton NotificationButton1 = (ImageButton) findViewById(R.id.NotificationButton1);
        NotificationButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Notifications.class);
                startActivity(intent);
            }
        }) ;

    }

    private List<Trip> fetchDiaries() {
        List<Trip> tripList = new ArrayList<>();

        GetRequestHandler requestHandler = new GetRequestHandler();
        JSONObject jsonResponse = requestHandler.getJSON("/diary/random/", new ArrayList<NameValuePair>());
        Log.d(TAG, "Response: " + jsonResponse);

        if (jsonResponse != null) {
            try {
                if (jsonResponse.getBoolean("res")) {
                    JSONArray searchResults = jsonResponse.getJSONArray("search_results");
                    for (int i = 0; i < searchResults.length(); ++i) {
                        JSONObject searchResult = searchResults.getJSONObject(i);
                        String diaryId = searchResult.getString("_id");
                        JSONArray postIdsJA = searchResult.getJSONArray("post_ids");
                        ArrayList<String> postIds = new ArrayList<>();
                        for (int j = 0; j < postIdsJA.length(); ++j) {
                            postIds.add(postIdsJA.getString(j));
                        }
                        String userId = searchResult.getString("user_id");
                        String title = searchResult.getString("title");
                        String coverPhotoName = searchResult.getString("cover_photo_name");
                        String updatedAt = searchResult.getString("updatedAt");
                        tripList.add(new Trip(diaryId, postIds, userId, title, coverPhotoName, updatedAt));
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tripList;
    }
}
