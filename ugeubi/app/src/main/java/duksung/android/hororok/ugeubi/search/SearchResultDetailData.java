package duksung.android.hororok.ugeubi.search;

import java.io.Serializable;

public class SearchResultDetailData implements Serializable {
    String content_key;
    String content_value;

    public SearchResultDetailData(String content_key, String content_value) {
        this.content_key = content_key;
        this.content_value = content_value;
    }


    public String getContent_key() {
        return content_key;
    }

    public String getContent_value() {
        return content_value;
    }
}
