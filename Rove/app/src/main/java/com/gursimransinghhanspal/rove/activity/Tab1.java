package com.gursimransinghhanspal.rove.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himaneesh on 10-03-2018.
 */

public class Tab1 extends Fragment
{
    List<Trip> tripList;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.tab1,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view1);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        tripList = new ArrayList<>();
        tripList.add(new Trip("John Doe","Recently visited the beautiful city of Manali. Have a look at my tour.",R.drawable.travel1));
        tripList.add(new Trip("John Doe","Recently visited Goa. Have a look at my tour.",R.drawable.travel2));
        tripList.add(new Trip("John Doe","Recently visited Shimla. Have a look at my tour.",R.drawable.travel3));
        tripList.add(new Trip("John Doe","Recently visited Dehradun. Have a look at my tour.",R.drawable.travel4));
        tripList.add(new Trip("John Doe","Recently visited Kashmir. Have a look at my tour.",R.drawable.travel5));
        tripList.add(new Trip("John Doe","Recently visited Himachal Pradesh. Have a look at my tour.",R.drawable.travel6));
        FeedAdapter adapter = new FeedAdapter(getContext(),tripList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
