package duksung.android.hororok.ugeubi.ugeubi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import duksung.android.hororok.ugeubi.R;

public class Ugeubi_fragment extends Fragment {
    private RecyclerView dose_list;
    private DoseListAdapter adapter;

    private Button add_btn;

    private UgeubiDialog ugeubiDialog;

    private LinearLayout calendarBtn;
    private TextView dateTextView;
    private int mYear, mMonth, mDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_ugeubi, container, false);

        dose_list = rootView.findViewById(R.id.dose_list);
        add_btn = rootView.findViewById(R.id.add_btn);
        calendarBtn = rootView.findViewById(R.id.calendar_btn);
        dateTextView = rootView.findViewById(R.id.date);

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



        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView.setText(mYear + "년 " + mMonth + "월 " + mDay + "일");
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month + 1;
                        mDay = dayOfMonth;
                        dateTextView.setText(mYear + "년 " + mMonth + "월 " + mDay + "일");
                    }
                }, mYear, mMonth - 1, mDay);

                datePickerDialog.setMessage("메시지");
                datePickerDialog.show();
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
