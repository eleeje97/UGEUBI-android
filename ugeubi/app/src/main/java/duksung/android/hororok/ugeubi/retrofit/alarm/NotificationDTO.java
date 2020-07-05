package duksung.android.hororok.ugeubi.retrofit.alarm;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NotificationDTO {

    @SerializedName("createdTime")
    public String createdTime;

    @SerializedName("updatedTime")
    public String updatedTime;

    @SerializedName("notification_id")
    public String notification_id;

    @SerializedName("medicine_id")
    public String medicine_id;

    @SerializedName("medicine_name")
    public String medicine_name;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("notification_date")
    public String notification_date;

    @SerializedName("notification_time")
    public String notification_time;

    @SerializedName("notification_type")
    public String notification_type;


    public String getCreatedTime() {
        return createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public String getNotification_type() {
        return notification_type;
    }
}
