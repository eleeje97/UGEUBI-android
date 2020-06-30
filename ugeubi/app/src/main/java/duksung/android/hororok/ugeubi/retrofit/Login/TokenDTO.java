package duksung.android.hororok.ugeubi.retrofit.Login;

import com.google.gson.annotations.SerializedName;

public class TokenDTO {
    @SerializedName("tokenType")
    private String tokenType;

    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
