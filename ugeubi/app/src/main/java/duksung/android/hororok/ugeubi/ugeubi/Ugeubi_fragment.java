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
import duksung.android.hororok.ugeubi.retrofit.ugeubi.TakingHistoryDTO;
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
    private TextView dateTextView, text;
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
        calendarBtn = rootView.findViewById(R.id.calendar_btn);
        dateTextView = rootView.findViewById(R.id.date);
        text = rootView.findViewById(R.id.text33);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dose_list.setLayoutManager(layoutManager);
        adapter = new DoseListAdapter(getContext());

        //ugeubiDialog = new UgeubiDialog(getContext(), positiveListener, negativeListener);




        /** 캘린더 **/
        final Calendar calendar = Calendar.getInstance();
        final Calendar maxdate = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // maxdate == 현재
        maxdate.get(Calendar.YEAR);
        maxdate.get((Calendar.MONTH)+1);
        maxdate.get(Calendar.DATE);

        dateTextView.setText(mYear + "년 " + mMonth + "월 " + mDay + "일");


        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        getTakingHistory(format.format(today));



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
                    Log.e("ugeubi", date_txt);


                    /** 약 알림 기록 API 호출 **/
                    adapter.clear();
                    getTakingHistory(date_txt);


                }
            }, mYear, mMonth - 1, mDay);

            datePickerDialog.setMessage("날짜를 선택해주세요!");


            datePickerDialog.getDatePicker().setMaxDate(maxdate.getTimeInMillis());
            datePickerDialog.show();


        });





        return rootView;
    }


    /** 다이얼로그  **/
    /*
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

     */


    /** 복용약 기록 가져오는 API **/
    public void getTakingHistory(String date){

        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.getTakingHistory(accessToken,date).enqueue(new Callback <List<TakingHistoryDTO>>() {
            @Override
            public void onResponse(Call<List<TakingHistoryDTO>> call, Response<List<TakingHistoryDTO>> response) {

                Log.e("ugeubi", "code : " + response.code());

                if (response.isSuccessful()) {
                    Log.i("ugeubi", "통신성공");
                    List<TakingHistoryDTO> apiResponse = response.body();

                    Log.e("ugeubi", "size() : " +response.body().size());
                    Log.e("ugeubi", "body() : " +response.body());

                    // 아이템이 있다면 "알람이 없어요" 텍스트 GONE
                    if(apiResponse.size() > 0){
                        text.setVisibility(View.GONE);
                    } else {
                        text.setVisibility(View.VISIBLE);
                    }

                    for (TakingHistoryDTO takingHistoryDTO : apiResponse) {
                        Log.i("ugeubi", "이름: " + takingHistoryDTO.getMedicineName());
                        Log.i("ugeubi", "몇시: " + takingHistoryDTO.getTakingTime());
                        adapter.addItem(takingHistoryDTO);
                        adapter.notifyDataSetChanged();
                        dose_list.setAdapter(adapter);


                    }
                } else {
                    Log.e("ugeubi", "errorbody() : " +response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<TakingHistoryDTO>> call, Throwable t) {
                Log.e("ugeubi", "통신실패"+ t.getMessage() + t.getCause());
            }

        });
    }




}
