package duksung.android.hororok.ugeubi.retrofit.alarm;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationListDTO {

    @SerializedName("notificationsList")
    ArrayList<NotificationDTO> notificationsList;

    public ArrayList<NotificationDTO> getNotificationsList() {
        return notificationsList;
    }
}
