package duksung.android.hororok.ugeubi.retrofit.FInd;

import com.google.gson.annotations.SerializedName;

public class AuthenticationPwDTO {


    @SerializedName("userId")
    public String userId;

    @SerializedName("email")
    public String email;

    @SerializedName("temporaryPassword")
    public String temporaryPassword;



    public AuthenticationPwDTO(String userId, String email, String temporaryPassword){
        this.userId = userId;
        this.email = email;
        this.temporaryPassword = temporaryPassword;

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

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }
}
