package duksung.android.hororok.ugeubi.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.R;

public class SearchResultDetailViewer extends LinearLayout {
    TextView content_key;
    TextView content_value;

    public SearchResultDetailViewer(Context context) {
        super(context);
        init(context);
    }

    public SearchResultDetailViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 초기 설정
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_result_detail_item,this,true);

        content_key = findViewById(R.id.key);
        content_value = findViewById(R.id.value);
    }

    public void setItem(SearchResultDetailData singerItem) {
        content_key.setText(singerItem.getContent_key());
        content_value.setText(singerItem.getContent_value());
    }
}
