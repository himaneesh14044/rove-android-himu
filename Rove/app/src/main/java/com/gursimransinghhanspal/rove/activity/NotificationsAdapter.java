package com.gursimransinghhanspal.rove.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gursimransinghhanspal.rove.R;

import java.util.List;

/**
 * Created by Mankus on 15-04-2018.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<NotificationsListItem> listItems;
    private Context context;

    public NotificationsAdapter(List<NotificationsListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NotificationsListItem listItem = listItems.get(position);
        holder.notification_text.setText(listItem.getNotification_text());
        holder.notification_time.setText(listItem.getNotification_time());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView notification_text;
        public TextView notification_time;

        public ViewHolder(View itemView) {
            super(itemView);

            notification_text = (TextView) itemView.findViewById(R.id.notification_text);
            notification_time = (TextView) itemView.findViewById(R.id.notification_time);

        }
    }

}
