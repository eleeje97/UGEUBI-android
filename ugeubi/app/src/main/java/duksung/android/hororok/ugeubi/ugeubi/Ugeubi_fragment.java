package duksung.android.hororok.ugeubi.ugeubi;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineItemDTO;
import duksung.android.hororok.ugeubi.retrofit.ugeubi.TakingHistoryDTO;
import duksung.android.hororok.ugeubi.retrofit.ugeubi.TakingHistoryListDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Ugeubi_fragment extends Fragment {
    private RecyclerView dose_list;
    private DoseListAdapter adapter;

    public final String PREFERENCE = "ugeubi.preference";
    public String datetxt = "";
    public Date date_f;


    private Button add_btn;

    private UgeubiDialog ugeubiDialog;

    private LinearLayout calendarBtn;
    private TextView dateTextView;
    private int mYear, mMonth, mDay;

    public String m, d;
    public String date_txt = "";

    RetrofitInterface apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_ugeubi, container, false);

        apiService = RetrofitClient.getService();


        dose_list = rootView.findViewById(R.id.dose_list);
        add_btn = rootView.findViewById(R.id.add_btn);
        calendarBtn = rootView.findViewById(R.id.calendar_btn);
        dateTextView = rootView.findViewById(R.id.date);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dose_list.setLayoutManager(layoutManager);
        adapter = new DoseListAdapter(getContext());

        ugeubiDialog = new UgeubiDialog(getContext(), positiveListener, negativeListener);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ugeubiDialog.show();
            }
        });


        /** 업데이트 **/
        // 12시가 되면 api 호출?


        /** 캘린더 **/
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView.setText(mYear + "년 " + mMonth + "월 " + mDay + "일");


        // 캘린더 버튼 클릭했을 때
        calendarBtn.setOnClickListener(v -> {
            final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month + 1;
                    mDay = dayOfMonth;
                    dateTextView.setText(mYear + "년 " + mMonth + "월 " + mDay + "일");

                    if(mMonth < 10){ m = "0" + mMonth; }
                    else{ m = mMonth + ""; }

                    if(mDay < 10){ d = "0" + mDay; }
                    else{ d = mDay + ""; }

                    date_txt = mYear + "-" + m + "-" + d;
                    Log.e("error", date_txt);


                    /** 약 알림 기록 API 호출 **/
                    getTakingHistory(date_txt);


                }
            }, mYear, mMonth - 1, mDay);

            datePickerDialog.setMessage("메시지");
            datePickerDialog.show();




        });




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



    public void getTakingHistory(String date){

        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.getTakingHistory(accessToken,date).enqueue(new Callback <TakingHistoryListDTO>() {
            @Override
            public void onResponse(Call<TakingHistoryListDTO> call, Response<TakingHistoryListDTO> response) {


                Log.e("error", "code : " + response.code());

                if (response.isSuccessful()) {

                    TakingHistoryListDTO dto = response.body();

                    for (TakingHistoryDTO takingHistoryDTO : dto.getItems()) {
                        Log.i("medicine_kit", "이름: " + takingHistoryDTO.getMedicineName());
                        Log.i("medicine_kit", "몇시: " + takingHistoryDTO.getTakingTime());
                        Log.i("medicine_kit", "몇알: " + takingHistoryDTO.getTakingNumber());
                        adapter.addItem(takingHistoryDTO.takingTime,
                                takingHistoryDTO.medicineName,
                                takingHistoryDTO.takingNumber,
                                takingHistoryDTO.taking_history_is_taken);
                        adapter.notifyDataSetChanged();
                        dose_list.setAdapter(adapter);

                        Log.i("info", "통신성공(업데이트알람)");
                    }
                }
            }

            @Override
            public void onFailure(Call<TakingHistoryListDTO> call, Throwable t) {
                Log.e("error", "통신실패(업데이트알람)"+ t.getMessage() + t.getCause());
            }

        });
    }

}
