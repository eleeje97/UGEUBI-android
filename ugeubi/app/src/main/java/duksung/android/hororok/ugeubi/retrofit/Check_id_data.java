package duksung.android.hororok.ugeubi.retrofit;

import com.google.gson.annotations.SerializedName;

public class Check_id_data {

    @SerializedName("userId")
    public String userId;

    @SerializedName("available")
    public Boolean available;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){

        return "GET>> " + "userId : " + userId
                + "available : " + available;
    }
}
