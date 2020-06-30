package duksung.android.hororok.ugeubi.retrofit;


import com.google.gson.annotations.SerializedName;

public class authenticationDTO {


    @SerializedName("authenticateNumber")
    public String authenticateNumber;

    @SerializedName("email")
    public String email;


    public authenticationDTO(){}
    public authenticationDTO(String authenticateNumber, String email){
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
