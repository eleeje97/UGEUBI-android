package duksung.android.hororok.ugeubi.ugeubi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import duksung.android.hororok.ugeubi.R;

public class Ugeubi_fragment extends Fragment {
    private RecyclerView dose_list;
    private DoseListAdapter adapter;

    private Button add_btn;

    private UgeubiDialog ugeubiDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_ugeubi, container, false);

        dose_list = rootView.findViewById(R.id.dose_list);
        add_btn = rootView.findViewById(R.id.add_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dose_list.setLayoutManager(layoutManager);
        adapter = new DoseListAdapter(getContext());
        dose_list.setAdapter(adapter);

        ugeubiDialog = new UgeubiDialog(getContext(), positiveListener, negativeListener);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ugeubiDialog.show();
            }
        });


        // Test 코드
        adapter.addItem("7:00", "비타민 D", "2알 복용하세요", false);
        adapter.addItem("8:00", "타이레놀", "2알 복용하세요", false);

        adapter.addItem("7:00", "비타민 D", "2알 복용하세요", true);
        adapter.addItem("8:00", "타이레놀", "2알 복용하세요", true);


        return rootView;
    }


    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getContext(), "확인버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            ugeubiDialog.dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getContext(), "취소버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            ugeubiDialog.dismiss();
        }
    };


}
