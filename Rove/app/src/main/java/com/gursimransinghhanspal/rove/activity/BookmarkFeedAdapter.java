package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;

import java.util.List;

public class BookmarkFeedAdapter extends RecyclerView.Adapter<BookmarkFeedAdapter.FeedViewHolder>
{
    private Context mCtx;
    TextView textView;
    private List<BookmarkedPost> bookmarkedList;
    public BookmarkFeedAdapter(Context mCtx, List<BookmarkedPost> bookmarkedList)
    {
        this.mCtx=mCtx;
        this.bookmarkedList=bookmarkedList;
    }

    @Override
    public BookmarkFeedAdapter.FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.pinned_bookmark_item,null);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookmarkFeedAdapter.FeedViewHolder holder, int position)
    {
        BookmarkedPost bookmarkedPost = bookmarkedList.get(position);
        holder.textViewDesc.setText(bookmarkedPost.getShortdesc());
        holder.textViewPerson.setText(bookmarkedPost.getUsername());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(bookmarkedPost.getImage()));

    }

    @Override
    public int getItemCount() {
        return bookmarkedList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewPerson, textViewDesc;
        ImageView imageView;
        ImageButton imageButton;
        public FeedViewHolder(View itemView)
        {
            super(itemView);
            textViewPerson = itemView.findViewById(R.id.bname);
            textViewDesc = itemView.findViewById(R.id.babout);
            imageView = itemView.findViewById(R.id.bdescription);
        }
    }
}
