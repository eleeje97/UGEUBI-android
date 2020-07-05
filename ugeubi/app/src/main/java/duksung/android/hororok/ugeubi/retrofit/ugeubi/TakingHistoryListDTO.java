package duksung.android.hororok.ugeubi.retrofit.ugeubi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineItemDTO;

public class TakingHistoryListDTO {

    ArrayList<TakingHistoryDTO> historyList;


    public ArrayList<TakingHistoryDTO> getItems() {
        return historyList;
    }
}
