package com.gursimransinghhanspal.rove.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.Trip;
import com.gursimransinghhanspal.rove.backend.GetRequestHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himaneesh on 10-03-2018.
 */

public class Tab1 extends Fragment
{
    private static final String TAG = Tab1.class.getName();
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
        tripList = fetchTrendingDiaries();
        FeedAdapter adapter = new FeedAdapter(getContext(),tripList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private List<Trip> fetchTrendingDiaries() {
        List<Trip> tripList = new ArrayList<>();

        ArrayList<NameValuePair> params = new ArrayList<>();
        GetRequestHandler requestHandler = new GetRequestHandler();
        JSONObject jsonResponse;

        if (Search.mLastQueryTrending != null) {
            String[] query = Search.mLastQueryTrending.split(" ");
            params.add(new BasicNameValuePair("searchQuery", TextUtils.join("%20", query)));
            jsonResponse = requestHandler.getJSON("/diary/search/trending/", params);
        } else {
            jsonResponse = requestHandler.getJSON("/diary/trending/", params);
        }

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

        Search.mLastQueryTrending = null;
        return tripList;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
