package duksung.android.hororok.ugeubi.retrofit.ugeubi;

import com.google.gson.annotations.SerializedName;

public class CheckedTakingMedicineDTO {

    @SerializedName("taking_history_id")
    public int taking_history_id;

    @SerializedName("istaken")
    public boolean istaken;

    public int getTaking_history_id() {
        return taking_history_id;
    }

    public void setTaking_history_id(int taking_history_id) {
        this.taking_history_id = taking_history_id;
    }

    public boolean isIstaken() {
        return istaken;
    }

    public void setIstaken(boolean istaken) {
        this.istaken = istaken;
    }
}
