package duksung.android.hororok.ugeubi.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DURInfoSearchResultDTO {
    @SerializedName("item")
    ArrayList<ItemInfoDTO> items;

    public ArrayList<ItemInfoDTO> getItems() {
        return items;
    }

}
