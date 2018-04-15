package com.gursimransinghhanspal.rove.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.gursimransinghhanspal.rove.R;

import java.util.ArrayList;
import java.util.List;

public class BookMark extends AppCompatActivity
{
    BookmarkedPost bookmarkedPostList;
    RecyclerView recyclerView;
    static List<BookmarkedPost> bookmarkhimulist = new ArrayList<>();

    @Override
    public void onBackPressed()
    {
        //Toast.makeText(getApplicationContext(),"back is pressed in bookmark ",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        recyclerView = findViewById(R.id.brecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View mBackButton = findViewById(R.id.btn_back);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityHomeFeed.class);
                startActivity(intent);
                finish();
            }
        });

        BookmarkFeedAdapter adapter = new BookmarkFeedAdapter(this,bookmarkhimulist);
        recyclerView.setAdapter(adapter);
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
                        Intent homeintent = new Intent(getApplicationContext(),ActivityHomeFeed.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.navigation_search:
                        Intent intent = new Intent(getApplicationContext(),Search.class);
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
    }
}
