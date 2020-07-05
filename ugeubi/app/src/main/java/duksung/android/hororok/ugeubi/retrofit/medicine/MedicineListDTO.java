package duksung.android.hororok.ugeubi.retrofit.medicine;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MedicineListDTO {

    @SerializedName("medicineList")
    ArrayList<MedicineItemDTO> medicineList;


    public ArrayList<MedicineItemDTO> getItems() {
        return medicineList;
    }


}
