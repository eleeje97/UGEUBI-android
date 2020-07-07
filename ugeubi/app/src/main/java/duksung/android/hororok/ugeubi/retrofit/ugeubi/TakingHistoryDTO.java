package duksung.android.hororok.ugeubi.retrofit.ugeubi;

import com.google.gson.annotations.SerializedName;

public class TakingHistoryDTO implements Comparable<TakingHistoryDTO> {

    @SerializedName("id")
    public int id;

    @SerializedName("userId")
    public int userId;

    @SerializedName("medicineId")
    public int medicineId;

    @SerializedName("taking_history_id")
    public int taking_history_id;

    @SerializedName("taking_history_date")
    public String taking_history_date;

    public String getTaking_history_date() {
        return taking_history_date;
    }

    public void setTaking_history_date(String taking_history_date) {
        this.taking_history_date = taking_history_date;
    }

    @SerializedName("medicineName")
    public String medicineName;

    @SerializedName("takingTime")
    public String takingTime;

    @SerializedName("takingNumber")
    public int takingNumber;

    @SerializedName("taking_history_is_taken")
    public boolean taking_history_is_taken;


    public TakingHistoryDTO(int taking_history_id, boolean taking_history_is_taken) {
        this.taking_history_id = taking_history_id;
        this.taking_history_is_taken = taking_history_is_taken;
    }

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

    @Override
    public int compareTo(TakingHistoryDTO o) {
        if (taking_history_is_taken == o.taking_history_is_taken) {
            return takingTime.compareTo(o.takingTime);
        } else {
            if (taking_history_is_taken) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
