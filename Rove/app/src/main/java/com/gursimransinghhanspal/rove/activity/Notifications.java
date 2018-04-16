package com.gursimransinghhanspal.rove.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gursimransinghhanspal.rove.R;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<NotificationsListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = (RecyclerView)findViewById(R.id.recyvlerViewNotifications);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        listItems.add(new NotificationsListItem("Rich requested to follow you","12:00"));
        listItems.add(new NotificationsListItem("Mandeep liked your post","04:00"));
        listItems.add(new NotificationsListItem("Gursimran commented on your photo","5:24"));
        listItems.add(new NotificationsListItem("Harish liked your diary","3:30"));

        adapter = new NotificationsAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
    }
}
