package duksung.android.hororok.ugeubi.retrofit.medicine;

import com.google.gson.annotations.SerializedName;

/**
 * {
 *   "medicineId": 1,
 *   "medicineName": "테스트약이름",
 *   "medicineType": "알약",
 *   "medicineValidTerm": "2020-07-09",
 *   "isTaken": true,
 *   "memo": "testmemo",
 *   "takingInfo": {
 *     "takingTime": [
 *       "11:11:00",
 *       "01:00:00"
 *     ],
 *     "takingDayOfWeek": [
 *       "화",
 *       "금"
 *     ],
 *     "takingNumber": 1
 *   }
 * }
 */

public class MedicineResultDTO {

    @SerializedName("medicineId")
    public String medicineId;

    @SerializedName("medicineName")
    public String medicineName;

    @SerializedName("medicineType")
    public MedicineType medicineType;

    @SerializedName("medicineValidTerm")
    public String medicineValidTerm;

    @SerializedName("isTaken")
    public boolean isTaken;

    @SerializedName("memo")
    public String memo;

    @SerializedName("takingInfo")
    public TakingInfoDayDTO takingInfoDay;

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(MedicineType medicineType) {
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

    public TakingInfoDayDTO getTakingInfoDay() {
        return takingInfoDay;
    }

    public void setTakingInfoDay(TakingInfoDayDTO takingInfoDay) {
        this.takingInfoDay = takingInfoDay;
    }
}
