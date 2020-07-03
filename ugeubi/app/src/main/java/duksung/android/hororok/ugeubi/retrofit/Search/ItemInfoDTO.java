package duksung.android.hororok.ugeubi.retrofit.Search;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemInfoDTO implements Serializable {
    // 품목명
    @SerializedName("ITEM_NAME") String ITEM_NAME;

    // 업체명
    @SerializedName("ENTP_NAME") String ENTP_NAME;

    // 성상
    @SerializedName("CHART") String CHART;


    /** 기본검색 **/
    // 분류
    @SerializedName("CLASS_NO") String CLASS_NO;

    // 저장방법
    @SerializedName("STORAGE_METHOD") String STORAGE_METHOD;

    // 원료성분
    @SerializedName("MATERIAL_NAME") String MATERIAL_NAME;

    // 유효기간
    @SerializedName("VALID_TERM") String VALID_TERM;

    // 용법용량
    @SerializedName("UD_DOC_ID") String UD_DOC_ID;

    // 주의사항
    @SerializedName("NB_DOC_ID") String NB_DOC_ID;


    /** 병용금기 **/
    @SerializedName("MIXTURE_ITEM_NAME") String MIXTURE_ITEM_NAME;

    /** 나머지 검색 **/
    // 분류
    @SerializedName("CLASS_NAME")
    String CLASS_NAME;

    // 주성분
    @SerializedName("MAIN_INGR")
    String MAIN_INGR;

    // 금기내용
    @SerializedName("PROHBT_CONTENT")
    String PROHBT_CONTENT;



    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public String getENTP_NAME() {
        return ENTP_NAME;
    }

    public String getCLASS_NO() {
        return CLASS_NO;
    }

    public String getCHART() {
        return CHART;
    }

    public String getSTORAGE_METHOD() {
        return STORAGE_METHOD;
    }

    public String getMATERIAL_NAME() {
        return MATERIAL_NAME;
    }

    public String getVALID_TERM() {
        return VALID_TERM;
    }

    public String getUD_DOC_ID() {
        return UD_DOC_ID;
    }

    public String getNB_DOC_ID() {
        return NB_DOC_ID;
    }

    public String getCLASS_NAME() {
        return CLASS_NAME;
    }

    public String getMAIN_INGR() {
        return MAIN_INGR;
    }

    public String getPROHBT_CONTENT() {
        return PROHBT_CONTENT;
    }

    public String getMIXTURE_ITEM_NAME() {
        return MIXTURE_ITEM_NAME;
    }
}
