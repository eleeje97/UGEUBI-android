package duksung.android.hororok.ugeubi.retrofit;

import com.google.gson.annotations.SerializedName;

public class Sign_up_email_num {

    @SerializedName("email")
    public String email;

    @SerializedName("authenticateNumber")
    public int authenticateNumber;


    public Sign_up_email_num(String email, int authenticateNumber){
        this.email = email;
        this.authenticateNumber = authenticateNumber;
    }



    public int getAuthenticateNumber() {
        return authenticateNumber;
    }

    public void setAuthenticateNumber(int authenticateNumber) {
        this.authenticateNumber = authenticateNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
