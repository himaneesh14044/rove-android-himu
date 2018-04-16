package com.gursimransinghhanspal.rove.activity;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;

import java.util.List;

/**
 * Created by Mankus on 30-03-2018.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<Notifications_List_Item> listItems;
    private Context context;

    public NotificationsAdapter(List<Notifications_List_Item> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notifications,parent);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Notifications_List_Item listItem = listItems.get(position);
        holder.notification_text.setText(listItem.getNotification_text());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView notification_text;

        public ViewHolder(View itemView) {
            super(itemView);

            notification_text = (TextView) itemView.findViewById(R.id.notification_text);

        }
    }

}
