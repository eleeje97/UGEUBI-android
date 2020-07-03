package duksung.android.hororok.ugeubi.retrofit.FInd;

import com.google.gson.annotations.SerializedName;

public class FindPwDTO {

    @SerializedName("userId")
    public String userId;

    @SerializedName("email")
    public String email;


    public FindPwDTO(String userId, String email){
        this.userId = userId;
        this.email = email;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
