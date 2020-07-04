package duksung.android.hororok.ugeubi.medicine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.registerMedicine.RegisterMedicine;

public class Medicine_kit_fragment  extends Fragment {


    // adapter
    public MedicineAdapter medicine_adapter = null;
    private ListView listView = null;



    // 추가 버튼
    Button add_btn;
    LinearLayout layout;
    Context context;

    // GridView
    GridView gridView;

    // LinearLayout
    LinearLayout linearLayout;

    // 버튼 크기 저장
    int width, height;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_medicine_kit, container, false);

        // 추가버튼, 그리드뷰
        add_btn = rootView.findViewById(R.id.add_btn);
        gridView = rootView.findViewById(R.id.gridview);

        // 등록된 약이 없다는 멘트를 담은 레이아웃
        linearLayout = rootView.findViewById(R.id.medicine_kit_init);

        // 어댑터 생성
        medicine_adapter = new MedicineAdapter();

        // 약 추가 버튼이 클릭시
        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 약 등록하기 버튼 누름 > 약 등록 페이지로 이동
                Intent intent = new Intent(getActivity(), RegisterMedicine.class);
                startActivity(intent);

                // 등록된 약이 있다면 그리드뷰 활성화
                //linearLayout.setVisibility(View.GONE);
                //gridView.setVisibility(View.VISIBLE);

                // 약 정보를 어댑터에 추가
                //medicine_adapter.addItem(new Medicine_data("날짜1",R.drawable.medicine_icon_pill1,"타이레놀","해열 및 진통제"));

                // 그리드뷰에 등록된 약 아이템 추가
                //gridView.setAdapter(adapter);
            }
        });


        // 아이템 등록하기
        //Intent intent = getActivity().getIntent();

        /*
        medicine_adapter.addItem(new Medicine_data("날짜", R.drawable.icon_pill3,
                                 intent.getStringExtra("MedicineName"),
                                 intent.getStringExtra("MedicineMemo")));
        */



        // 그리드 뷰에 아이템 클릭시
        // 해당 아이템의 상세 페이지로 이동
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),Medicine_kit_detail.class);
                startActivity(intent);
            }
        });





        return rootView;
    }






}
