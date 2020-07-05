package duksung.android.hororok.ugeubi.retrofit.medicine;

import com.google.gson.annotations.SerializedName;

public class MedicineItemDTO {

    @SerializedName("medicineId") int medicineId;
    @SerializedName("medicineName") String medicineName;
    @SerializedName("medicineType") String medicineType;
    @SerializedName("medicineValidTerm") String medicineValidTerm;
    @SerializedName("isImminent") boolean isImminent;
    @SerializedName("isTaken") boolean isTaken;
    @SerializedName("memo") String memo;

    @SerializedName("takingInfoDayDto") TakingInfoDayDTO takingInfoDayDto;

    @SerializedName("takingInfo") TakingInfoDayDTO takingInfo;

    public int getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public String getMedicineValidTerm() {
        return medicineValidTerm;
    }

    public boolean isImminent() {
        return isImminent;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public String getMemo() {
        return memo;
    }

    public TakingInfoDayDTO getTakingInfoDayDto() {
        return takingInfoDayDto;
    }

    public TakingInfoDayDTO getTakingInfo() {
        return takingInfo;
    }
}
