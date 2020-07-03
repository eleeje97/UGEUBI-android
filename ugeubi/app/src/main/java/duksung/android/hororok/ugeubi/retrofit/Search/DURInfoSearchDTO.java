package duksung.android.hororok.ugeubi.retrofit.Search;

import com.google.gson.annotations.SerializedName;

public class DURInfoSearchDTO {
    @SerializedName("itemName")
    public String itemName;

    @SerializedName("pageNo")
    public String pageNo;

    public DURInfoSearchDTO(String itemName, String pageNo){
        this.itemName = itemName;
        this.pageNo = pageNo;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPageNo() {
        return pageNo;
    }
}
