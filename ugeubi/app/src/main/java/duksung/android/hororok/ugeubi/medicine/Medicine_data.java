package duksung.android.hororok.ugeubi.medicine;

public class Medicine_data {

    private String period;
    private int image;
    private String name;
    private String content;

    public Medicine_data(String period, int image, String name, String content){
        this.period = period;
        this.image = image;
        this.name = name;
        this.content = content;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
