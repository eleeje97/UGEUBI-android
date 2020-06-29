package duksung.android.hororok.ugeubi.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Sign_up_email_num {

    @SerializedName("email")
    public String email;

    @SerializedName("authenticateNumber")
    public String authenticateNumber;


    public Sign_up_email_num(String email, String authenticateNumber){
        this.email = email;
        this.authenticateNumber = authenticateNumber;
    }



    public String getAuthenticateNumber() {
        return authenticateNumber;
    }

    public void setAuthenticateNumber(String authenticateNumber) {
        this.authenticateNumber = authenticateNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "email : " + email
                + "authenticateNumber : " + authenticateNumber;
    }
}
