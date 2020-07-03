package duksung.android.hororok.ugeubi.retrofit.FInd;

import com.google.gson.annotations.SerializedName;

public class FindIdDTO {

    @SerializedName("userId")
    public String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
