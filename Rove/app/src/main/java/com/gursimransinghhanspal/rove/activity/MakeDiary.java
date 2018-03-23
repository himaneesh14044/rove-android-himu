package com.gursimransinghhanspal.rove.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gursimransinghhanspal.rove.R;

public class MakeDiary extends AppCompatActivity {

	private RecyclerView mDiaryItemsRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_diary);

		mDiaryItemsRecyclerView = findViewById(R.id.activityLayout_makeDiary_diaryItemsRecyclerView);
		mDiaryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mDiaryItemsRecyclerView.setAdapter(new Adapter());
		mDiaryItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View itemView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.recycler_item_diary_loading_shimmer, parent, false);

			return new ViewHolder(itemView);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

		}

		@Override
		public int getItemCount() {
			return 10;
		}

		public class ViewHolder extends RecyclerView.ViewHolder {

			public ViewHolder(View itemView) {
				super(itemView);
			}
		}
	}
}
