package duksung.android.hororok.ugeubi.retrofit.FInd;

import com.google.gson.annotations.SerializedName;

public class NewPwDTO {

    @SerializedName("userId")
    public String userId;

    @SerializedName("password")
    public String password;

    public NewPwDTO(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
