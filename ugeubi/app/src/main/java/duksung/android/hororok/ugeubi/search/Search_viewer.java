package duksung.android.hororok.ugeubi.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.medicine.Medicine_data;

public class Search_viewer extends LinearLayout {



    public Search_viewer(Context context) {
        super(context);
        init(context);
    }

    public Search_viewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    // 초기 설정
    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_result_item,this,true);


    }

    public void setItem(Search_data singerItem){



    }
}