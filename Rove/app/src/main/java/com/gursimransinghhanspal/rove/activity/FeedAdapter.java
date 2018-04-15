package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
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
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, int position)
    {
        com.gursimransinghhanspal.rove.Trip list = tripList.get(position);
        holder.textViewDesc.setText(list.getShortdesc());
        holder.textViewPerson.setText(list.getUsernmae());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(list.getImage()));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),holder.textViewDesc.getText(),Toast.LENGTH_SHORT).show();


                if (holder.imageButton.getTag()!=null && holder.imageButton.getTag().equals(R.drawable.ic_bookmark_black_24dp))
                {
                    holder.imageButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    holder.imageButton.setColorFilter(Color.parseColor("#00838f"));
                    Iterator<BookmarkedPost> iterator = BookMark.bookmarkhimulist.iterator();
                    while (iterator.hasNext()) {
                        BookmarkedPost bookmarkedPost = iterator.next();
                        if (bookmarkedPost.getShortdesc().equals(holder.textViewDesc.getText().toString()))
                        {
                            iterator.remove();
                        }
                    }
                    holder.imageButton.setTag(R.drawable.ic_bookmark_border_black_24dp);
                }
                else
                {
                    holder.imageButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    holder.imageButton.setColorFilter(Color.parseColor("#00838f"));
                    for(int i=0;i<tripList.size();i++) {
                        if (holder.textViewDesc.getText().toString().equals(ActivityHomeFeed.tripList.get(i).getShortdesc())) {
                            BookmarkedPost bookmarkedPost = new BookmarkedPost(ActivityHomeFeed.tripList.get(i).getUsernmae(), ActivityHomeFeed.tripList.get(i).getShortdesc(), ActivityHomeFeed.tripList.get(i).getImage());
                            BookMark.bookmarkhimulist.add(bookmarkedPost);
                        }
                    }
                    holder.imageButton.setTag(R.drawable.ic_bookmark_black_24dp);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewPerson, textViewDesc;
        ImageView imageView;
        ImageButton imageButton;
        public FeedViewHolder(View itemView) {
            super(itemView);
            textViewPerson = itemView.findViewById(R.id.name);
            textViewDesc = itemView.findViewById(R.id.about);
            imageView = itemView.findViewById(R.id.description);
            imageButton = itemView.findViewById(R.id.pinbookmark);
        }
    }
}
