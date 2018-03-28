package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.gursimransinghhanspal.rove.R;
/**
 * Created by Himaneesh on 09-03-2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>
{
    private Context mCtx;
    private List<com.gursimransinghhanspal.rove.Trip> tripList;

    public FeedAdapter(Context mCtx,List<com.gursimransinghhanspal.rove.Trip> tripList)
    {
        this.mCtx=mCtx;
        this.tripList=tripList;
    }


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item,null);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position)
    {
        com.gursimransinghhanspal.rove.Trip list = tripList.get(position);
        holder.textViewDesc.setText(list.getShortdesc());
        holder.textViewPerson.setText(list.getUsernmae());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(list.getImage()));
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewPerson, textViewDesc;
        ImageView imageView;

        public FeedViewHolder(View itemView) {
            super(itemView);
            textViewPerson = itemView.findViewById(R.id.name);
            textViewDesc = itemView.findViewById(R.id.about);
            imageView = itemView.findViewById(R.id.description);
        }
    }
}
