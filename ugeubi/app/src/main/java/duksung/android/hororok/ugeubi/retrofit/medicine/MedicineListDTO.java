package duksung.android.hororok.ugeubi.retrofit.medicine;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MedicineListDTO {
    /**
     * {
     *     "medicineList": [
     *         {
     *             "medicineId": 1,
     *             "medicineName": "테스트약이름",
     *             "medicineType": "PILL",
     *             "medicineValidTerm": "2020-07-08",
     *             "isImminent": true, //유통기한 남은 기간이 5일 이하일 경우 true
     *             "isTaken": true,
     *             "memo": "testmemo"
     *         }
     *     ]
     * }
     */

    @SerializedName("medicineList")
    ArrayList<MedicineItemDTO> medicineList;


    public ArrayList<MedicineItemDTO> getItems() {
        return medicineList;
    }


}
