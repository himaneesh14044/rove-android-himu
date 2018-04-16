package com.gursimransinghhanspal.rove.activity;

import com.gursimransinghhanspal.rove.R;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAccount extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<UserAccount_List_Item> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        recyclerView = (RecyclerView) findViewById(R.id.userAccountRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        listItems.add(new UserAccount_List_Item("MANALI TOUR", R.drawable.travel3));
        listItems.add(new UserAccount_List_Item("TIBET CALLED", R.drawable.travel2));
        listItems.add(new UserAccount_List_Item("LONDON TOUR", R.drawable.travel1));

        adapter = new userAccountAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
    }
}
