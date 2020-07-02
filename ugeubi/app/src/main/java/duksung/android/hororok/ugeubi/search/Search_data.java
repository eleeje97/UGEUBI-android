package duksung.android.hororok.ugeubi.search;

public class Search_data {
    private int image;
    private String medicine_name;
    private String entp_name;

    public Search_data(int image, String medicine_name, String entp_name) {
        this.image = image;
        this.medicine_name = medicine_name;
        this.entp_name = entp_name;
    }

    public int getImage() {
        return image;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public String getEntp_name() {
        return entp_name;
    }
}
