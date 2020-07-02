package duksung.android.hororok.ugeubi.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.medicine.Medicine_data;
import duksung.android.hororok.ugeubi.medicine.Medicine_kit_fragment;
import duksung.android.hororok.ugeubi.registerMedicine.RegisterMedicine;
import duksung.android.hororok.ugeubi.retrofit.ItemInfoDTO;

// 검색 결과 페이지
public class Search_Result extends Activity {


    GridView search_result_view;
    Button back_btn;
    TextView noResult_textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        back_btn = findViewById(R.id.search_back_btn);
        search_result_view = findViewById(R.id.gridview_search_result);
        noResult_textView = findViewById(R.id.noResult_textView);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // 어댑터 생성, 설정
        Search_result_adapter adapter = new Search_result_adapter();
        search_result_view.setAdapter(adapter);


        // 결과리스트 인텐트 가져오기
        Intent intent = getIntent();
        ArrayList<ItemInfoDTO> resultList = (ArrayList<ItemInfoDTO>) intent.getSerializableExtra("resultList");


        // 아이템 리스너 설정
        search_result_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();


            }
        });



        // 결과리스트를 그리드뷰에 추가
        if (resultList.size() == 0) {
            noResult_textView.setVisibility(View.VISIBLE);
        } else {
            noResult_textView.setVisibility(View.INVISIBLE);
            for (ItemInfoDTO itemInfoDTO : resultList) {
                adapter.addItem(itemInfoDTO.getITEM_NAME(), itemInfoDTO.getENTP_NAME());
            }

        }








    }


    // 어댑터
    class Search_result_adapter extends BaseAdapter {
        // 검색 데이터 결과
        ArrayList<Search_data> items = new ArrayList<Search_data>();

        // 알약 아이콘을 랜덤으로 보여주기 위해 사용
        int[] pill_icons = {R.drawable.medicine_icon_pill1, R.drawable.pill_icon2,R.drawable.icon_pill3};
        Random random = new Random();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(String medicine_name, String entp_name){
            int num = random.nextInt(3);
            if (medicine_name.length() > 7) {
                medicine_name = medicine_name.substring(0,7) + "...";
            }
            Search_data data = new Search_data(pill_icons[num], medicine_name, entp_name);
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
