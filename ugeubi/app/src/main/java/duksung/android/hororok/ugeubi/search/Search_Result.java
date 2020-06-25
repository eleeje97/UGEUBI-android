package duksung.android.hororok.ugeubi.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.medicine.Medicine_data;
import duksung.android.hororok.ugeubi.medicine.Medicine_kit_fragment;
import duksung.android.hororok.ugeubi.registerMedicine.RegisterMedicine;

// 검색 결과 페이지
public class Search_Result extends Activity {


    GridView search_result_view;
    Button back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        back_btn = findViewById(R.id.search_back_btn);
        search_result_view = findViewById(R.id.gridview_search_result);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // 어댑터 생성
        Search_result_adapter adapter = new Search_result_adapter();


            // 약 추가
        adapter.addItem(new Search_data());
        adapter.addItem(new Search_data());
        adapter.addItem(new Search_data());
        adapter.addItem(new Search_data());
        adapter.addItem(new Search_data());
        adapter.addItem(new Search_data());
        adapter.addItem(new Search_data());

        search_result_view.setAdapter(adapter);










    }


    // 어댑터
    class Search_result_adapter extends BaseAdapter {

        // 검색 데이터 결과
        ArrayList<Search_data> items = new ArrayList<Search_data>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Search_data data){
            items.add(data);
        }

        @Override
        public Search_data getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Search_viewer searchViewer = new Search_viewer(getApplication());
            searchViewer.setItem(items.get(i));
            return searchViewer;

        }
    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
