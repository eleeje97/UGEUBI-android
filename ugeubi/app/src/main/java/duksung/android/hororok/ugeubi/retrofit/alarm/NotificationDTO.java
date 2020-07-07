package duksung.android.hororok.ugeubi.retrofit.alarm;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NotificationDTO {

    @SerializedName("notificationId")
    public String notificationId;

    @SerializedName("medicineId")
    public String medicineId;

    @SerializedName("medicineName")
    public String medicineName;

    @SerializedName("notificationDate")
    public String notificationDate;

    @SerializedName("notificationTime")
    public String notificationTime;

    @SerializedName("notificationType")
    public String notificationType;


    public String getNotificationId() {
        return notificationId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public String getNotificationType() {
        return notificationType;
    }
}
