package duksung.android.hororok.ugeubi.medicine;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class Medicine_kit_fragment  extends Fragment {


    // adapter
    private Medicine_adapter medicine_adapter = null;
    private ListView listView = null;



    // 추가 버튼
    Button add_btn;
    LinearLayout layout;
    Context context;

    // GridView
    GridView gridView;

    // 버튼 크기 저장
    int width, height;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_medicine_kit, container, false);





        add_btn = rootView.findViewById(R.id.add_btn);


        gridView = rootView.findViewById(R.id.gridview);








        add_btn.setOnClickListener(new View.OnClickListener() {


            Medicine_adapter adapter = new Medicine_adapter();
            @Override
            public void onClick(View v) {
                Log.e("e","추가 버튼 클릭됨");
                adapter.addItem(new Medicine_data("날짜1",R.drawable.medicine_icon_pill1,"타이레놀","해열 및 진통제"));


                gridView.setAdapter(adapter);

            }
        });

        return rootView;
    }



    class Medicine_adapter extends BaseAdapter {
        ArrayList<Medicine_data> items = new ArrayList<Medicine_data>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Medicine_data data){
            items.add(data);
        }

        @Override
        public Medicine_data getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Medicine_viewer medicineViewer = new Medicine_viewer(getContext());
            medicineViewer.setItem(items.get(i));
            return medicineViewer;
        }
    }



}
