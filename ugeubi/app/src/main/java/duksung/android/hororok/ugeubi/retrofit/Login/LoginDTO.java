package duksung.android.hororok.ugeubi.retrofit.Login;

import com.google.gson.annotations.SerializedName;

public class LoginDTO {
    @SerializedName("userId")
    public String userId;

    @SerializedName("password")
    public String password;

    public LoginDTO(String userId, String password){
        this.userId = userId;
        this.password = password;
    }
}
