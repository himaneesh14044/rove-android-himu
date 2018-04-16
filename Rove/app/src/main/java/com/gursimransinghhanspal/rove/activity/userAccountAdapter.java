package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mankus on 30-03-2018.
 */

public class userAccountAdapter extends RecyclerView.Adapter<userAccountAdapter.ViewHolder>{

    private List<UserAccount_List_Item> userAccountListItems;
    private Context context;

    public userAccountAdapter(List<UserAccount_List_Item> listItems, Context context) {

        this.userAccountListItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user_account,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserAccount_List_Item listItem = userAccountListItems.get(position);

        holder.textViewTitle.setText(listItem.getDiaryTitle());
        holder.imageViewCover.setImageDrawable(context.getResources().getDrawable(listItem.getCoverImage()));

    }

    @Override
    public int getItemCount() {
        return userAccountListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitle;
        public ImageView imageViewCover;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.diaryTitle);
            imageViewCover = (ImageView) itemView.findViewById(R.id.coverImage);
        }
    }
}
