package duksung.android.hororok.ugeubi.retrofit.ugeubi;

import com.google.gson.annotations.SerializedName;

public class TakingHistoryDTO {

    @SerializedName("id")
    public int id;

    @SerializedName("userId")
    public int userId;

    @SerializedName("medicineId")
    public int medicineId;

    @SerializedName("taking_history_id")
    public int taking_history_id;

    @SerializedName("medicineName")
    public String medicineName;

    @SerializedName("takingTime")
    public String takingTime;

    @SerializedName("takingNumber")
    public int takingNumber;

    @SerializedName("taking_history_is_taken")
    public boolean taking_history_is_taken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getTaking_history_id() {
        return taking_history_id;
    }

    public void setTaking_history_id(int taking_history_id) {
        this.taking_history_id = taking_history_id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(String takingTime) {
        this.takingTime = takingTime;
    }

    public int getTakingNumber() {
        return takingNumber;
    }

    public void setTakingNumber(int takingNumber) {
        this.takingNumber = takingNumber;
    }

    public boolean isTaking_history_is_taken() {
        return taking_history_is_taken;
    }

    public void setTaking_history_is_taken(boolean taking_history_is_taken) {
        this.taking_history_is_taken = taking_history_is_taken;
    }
}
