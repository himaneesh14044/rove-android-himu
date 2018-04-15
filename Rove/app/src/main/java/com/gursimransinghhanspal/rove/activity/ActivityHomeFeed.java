package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.Trip;

import java.util.ArrayList;
import java.util.List;


public class ActivityHomeFeed extends AppCompatActivity {

    static List<Trip> tripList;
    RecyclerView recyclerView;
    Bundle himusavedinstance;
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
        ImageButton ib3 = ( ImageButton) findViewById(R.id.imageButton3);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = new Intent(getApplicationContext(),CreateDiary1.class);
//                startActivity(intent1);
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripList = new ArrayList<>();
        tripList.add(new Trip("John Doe","Recently visited the beautiful city of Manali. Have a look at my tour.",R.drawable.travel1));
        tripList.add(new Trip("John Doe","Recently visited Goa. Have a look at my tour.",R.drawable.travel2));
        tripList.add(new Trip("John Doe","Recently visited Shimla. Have a look at my tour.",R.drawable.travel3));
        tripList.add(new Trip("John Doe","Recently visited Dehradun. Have a look at my tour.",R.drawable.travel4));
        tripList.add(new Trip("John Doe","Recently visited Kashmir. Have a look at my tour.",R.drawable.travel5));
        tripList.add(new Trip("John Doe","Recently visited Himachal Pradesh. Have a look at my tour.",R.drawable.travel6));

        FeedAdapter adapter = new FeedAdapter(this,tripList);
        recyclerView.setAdapter(adapter);
        ImageButton imageButton = (ImageButton) findViewById(R.id.ImageButton2);
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
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
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
                    case R.id.navigation_dashboard:
                        Intent intent = new Intent(getApplicationContext(),Search.class);
                        //Toast.makeText(getApplicationContext(),"Search should work",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        return true;
                    case R.id.navigation_notifications:
                        Intent userintent = new Intent(getApplicationContext(),UserAccount.class);
                        startActivity(userintent);
                        return true;

                }
                return false;
            }
        });

    }
}
