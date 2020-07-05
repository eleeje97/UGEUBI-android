package duksung.android.hororok.ugeubi.retrofit.alarm;

import com.google.gson.annotations.SerializedName;

/**
 * "medicine_id" : "2",
 *     "user_id" : "1",
 *     "notification_date" : "2015-04-18",
 *     "notification_type" : "valid_term"
 */
public class NotificationDTO {

    @SerializedName("medicine_id")
    public String medicine_id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("notification_date")
    public String notification_date;

    @SerializedName("notification_type")
    public String notification_type;

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
