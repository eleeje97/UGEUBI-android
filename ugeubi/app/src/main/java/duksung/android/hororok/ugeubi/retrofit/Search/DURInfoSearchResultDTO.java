package duksung.android.hororok.ugeubi.retrofit.Search;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DURInfoSearchResultDTO {
    @SerializedName("items")
    ArrayList<ItemInfoDTO> items;

    @SerializedName("totalCount")
    int totalCount;

    public ArrayList<ItemInfoDTO> getItems() {
        return items;
    }

    public int getTotalCount() {
        return  totalCount;
    }

}
