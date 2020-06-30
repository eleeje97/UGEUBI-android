package duksung.android.hororok.ugeubi.retrofit;

import com.google.gson.annotations.SerializedName;

public class Sign_up_DTO {


    @SerializedName("email")
    public String email;

    @SerializedName("userId")
    public String userId;

    @SerializedName("password")
    public String password;

    @SerializedName("userName")
    public String userName;


    public Sign_up_DTO(String email, String userId, String password, String userName){
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString(){

        return "POST>> " + "email : " + email
                + "userId : " + userId
                + "password : " + password
                + "userName : "+ userName ;
    }
}
