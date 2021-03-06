package duksung.android.hororok.ugeubi.retrofit.medicine;

import com.google.gson.annotations.SerializedName;

public class MedicineDTO {

    @SerializedName("medicineName")
    public String medicineName;

    @SerializedName("medicineType")
    public String medicineType;

    @SerializedName("medicineValidTerm")
    public String medicineValidTerm;

    @SerializedName("isTaken")
    public boolean isTaken;

    @SerializedName("memo")
    public String memo;

    @SerializedName("takingInfoDayDto")
    public TakingInfoDayDTO takingInfoDayDto;

    public MedicineDTO(){}

    public MedicineDTO(String medicineName, String medicineType, String medicineValidTerm, boolean isTaken, String memo, TakingInfoDayDTO takingInfoDayDTO){
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.medicineValidTerm = medicineValidTerm;
        this.isTaken = isTaken;
        this.memo = memo;
        this.takingInfoDayDto = takingInfoDayDTO;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getMedicineValidTerm() {
        return medicineValidTerm;
    }

    public void setMedicineValidTerm(String medicineValidTerm) {
        this.medicineValidTerm = medicineValidTerm;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public TakingInfoDayDTO getTakingInfoDayDto() {
        return takingInfoDayDto;
    }

    public void setTakingInfoDayDto(TakingInfoDayDTO takingInfoDayDto) {
        this.takingInfoDayDto = takingInfoDayDto;
    }
}