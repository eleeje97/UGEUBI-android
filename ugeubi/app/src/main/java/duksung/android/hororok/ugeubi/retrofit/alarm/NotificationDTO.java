package duksung.android.hororok.ugeubi.retrofit.alarm;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NotificationDTO {

    @SerializedName("createdTime")
    public Date createdTime;

    @SerializedName("updatedTime")
    public Date updatedTime;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }
}
