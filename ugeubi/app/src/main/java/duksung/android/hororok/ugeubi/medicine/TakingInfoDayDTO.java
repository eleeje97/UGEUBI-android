package duksung.android.hororok.ugeubi.medicine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TakingInfoDayDTO {

    @SerializedName("takingTime")
    public List<String> takingTime;

    @SerializedName("takingDayOfWeek")
    public List<String> takingDayOfWeek;

    @SerializedName("takingNumber")
    public int takingNumber;

    public TakingInfoDayDTO(){}
    public TakingInfoDayDTO(List<String> takingTime, List<String> takingDayOfWeek, int takingNumber){
        this.takingTime = takingTime;
        this.takingDayOfWeek = takingDayOfWeek;
        this.takingNumber = takingNumber;
    }

    public List<String> getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(List<String> takingTime) {
        this.takingTime = takingTime;
    }

    public List<String> getTakingDayOfWeek() {
        return takingDayOfWeek;
    }

    public void setTakingDayOfWeek(List<String> takingDayOfWeek) {
        this.takingDayOfWeek = takingDayOfWeek;
    }

    public int getTakingNumber() {
        return takingNumber;
    }

    public void setTakingNumber(int takingNumber) {
        this.takingNumber = takingNumber;
    }
}
