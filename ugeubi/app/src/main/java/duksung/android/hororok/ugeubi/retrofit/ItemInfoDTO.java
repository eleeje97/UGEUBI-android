package duksung.android.hororok.ugeubi.retrofit;

import com.google.gson.annotations.SerializedName;

import retrofit2.SkipCallbackExecutor;

public class ItemInfoDTO {
    @SerializedName("CLASS_NO")
    String CLASS_NO;

    @SerializedName("ITEM_NAME")
    String ITEM_NAME;

    public String getCLASS_NO() {
        return CLASS_NO;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }
}
