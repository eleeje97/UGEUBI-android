package duksung.android.hororok.ugeubi.retrofit;


import com.google.gson.annotations.SerializedName;

public class Sign_up_email_num {


    @SerializedName("authenticateNumber")
    public String authenticateNumber;

    @SerializedName("email")
    public String email;


    public Sign_up_email_num(){}

    public Sign_up_email_num(String authenticateNumber, String email){
        this.authenticateNumber = authenticateNumber;
        this.email = email;
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

    @Override
    public String toString() {
        return "email : " + email
                + "authenticateNumber : " + authenticateNumber;
    }
}
