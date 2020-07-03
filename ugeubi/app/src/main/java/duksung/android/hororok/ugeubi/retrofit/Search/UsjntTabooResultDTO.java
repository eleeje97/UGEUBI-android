package duksung.android.hororok.ugeubi.retrofit.Search;

import java.io.Serializable;
import java.util.ArrayList;

public class UsjntTabooResultDTO implements Serializable {
    String ITEM_NAME;
    String ENTP_NAME;
    String CLASS_NAME;
    ArrayList<MixtureItemDTO> mixtureItems;

    public UsjntTabooResultDTO(String ITEM_NAME, String ENTP_NAME, String CLASS_NAME, ArrayList<MixtureItemDTO> mixtureItems) {
        this.ITEM_NAME = ITEM_NAME;
        this.ENTP_NAME = ENTP_NAME;
        this.CLASS_NAME = CLASS_NAME;
        this.mixtureItems = mixtureItems;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public String getENTP_NAME() {
        return ENTP_NAME;
    }

    public String getCLASS_NAME() {
        return CLASS_NAME;
    }

    public ArrayList<MixtureItemDTO> getMixtureItems() {
        return mixtureItems;
    }
}
