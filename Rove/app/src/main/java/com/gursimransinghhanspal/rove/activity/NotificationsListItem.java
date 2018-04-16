package com.gursimransinghhanspal.rove.activity;

/**
 * Created by Mankus on 15-04-2018.
 */

public class NotificationsListItem {
    private String notification_text;
    private String notification_time;

    public NotificationsListItem(String notification_text, String notification_time) {
        this.notification_text = notification_text;
        this.notification_time = notification_time;
    }

    public String getNotification_text() {
        return notification_text;
    }

    public String getNotification_time() {return notification_time;}

    public void setNotification_text(String notification_text) {
        this.notification_text = notification_text;
    }
    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

}
