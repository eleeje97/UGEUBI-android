package duksung.android.hororok.ugeubi.retrofit.medicine;

import com.google.gson.annotations.SerializedName;

public class MedicineType {

    //PILL(알약),LIQUID(물약), POWDER(가루약),CREAM(연고), PRESCRIPTION(처방약)
    @SerializedName("PILL")
    public String PILL;

    @SerializedName("LIQUID")
    public String LIQUID;

    @SerializedName("POWDER")
    public String POWDER;

    @SerializedName("CREAM")
    public String CREAM;

    @SerializedName("PRESCRIPTION")
    public String PRESCRPTION;


}
