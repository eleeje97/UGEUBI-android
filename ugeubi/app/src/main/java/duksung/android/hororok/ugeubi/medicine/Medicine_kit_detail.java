package duksung.android.hororok.ugeubi.medicine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import duksung.android.hororok.ugeubi.MainActivity;
import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.registerMedicine.RegisterMedicine;
import duksung.android.hororok.ugeubi.registerMedicine.TakingTimeListAdapter;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineItemDTO;
import duksung.android.hororok.ugeubi.ugeubi.UgeubiDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medicine_kit_detail extends Activity {

    MainActivity mainActivity = (MainActivity) MainActivity.mainActivity;

    Button back_btn;

    public final String PREFERENCE = "ugeubi.preference";
    RetrofitInterface apiService;

    EditText medicine_name, medicine_memo;
    ToggleButton pill, liquidMedicine, powderedMedicine, ointment, prescriptionDrug;
    TextView expiration_date_txt, expiration_date;
    RadioButton takingBtn, notTakingBtn;
    ToggleButton mon, tue, wed, thu, fri, sat, sun;
    EditText taking_dose_num;

    // 삭제 버튼
    LinearLayout delete_btn;


    RadioGroup takingBtnGroup;

    RecyclerView takingTimeList;
    TakingTimeListAdapter takingTimeListAdapter;

    // section
    LinearLayout expiration_date_section, isTaken_section, takingDay_section, takingTime_section, taking_dose_num_section;


    UgeubiDialog ugeubiDialog;
    public int medicineId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_kit_detail);

        apiService = RetrofitClient.getService();


        /** init **/


        delete_btn = findViewById(R.id.delete_btn);
        back_btn = findViewById(R.id.btn_back);
        medicine_name = findViewById(R.id.medicine_name);
        medicine_memo = findViewById(R.id.medicine_memo);
        pill = findViewById(R.id.pill);
        liquidMedicine = findViewById(R.id.liquidMedicine);
        powderedMedicine = findViewById(R.id.powderedMedicine);
        ointment = findViewById(R.id.ointment);
        prescriptionDrug = findViewById(R.id.prescriptionDrug);
        expiration_date_txt = findViewById(R.id.expiration_date_txt);
        expiration_date = findViewById(R.id.expiration_date);
        takingBtn = findViewById(R.id.takingBtn);
        notTakingBtn = findViewById(R.id.notTakingBtn);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        sun = findViewById(R.id.sun);
        taking_dose_num = findViewById(R.id.taking_dose_num);

        takingBtnGroup = findViewById(R.id.takingBtnGroup);

        takingTimeList = findViewById(R.id.takingTimeList);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false);
        takingTimeList.setLayoutManager(linearLayoutManagerWrapper);
        takingTimeListAdapter = new TakingTimeListAdapter(this);
        takingTimeList.setAdapter(takingTimeListAdapter);

        // section
        expiration_date_section = findViewById(R.id.expiration_date_section);
        isTaken_section = findViewById(R.id.isTaken_section);
        takingDay_section = findViewById(R.id.taking_day_section);
        takingTime_section = findViewById(R.id.taking_time_section);
        taking_dose_num_section = findViewById(R.id.taking_dose_num_section);

        /** init end **/


        Intent intent = getIntent();
        medicineId = intent.getIntExtra("medicineId", 0);
        getMedicineDetailInfo(medicineId);


        delete_btn.setOnClickListener(v -> {

            ugeubiDialog.show();

        });



        // 뒤로가기 버튼이 눌렸다면
        back_btn.setOnClickListener(v -> {
            // 뒤로가기 함수 호출
            onBackPressed();
        });


        // 약 종류 버튼 리스너 설정
        MedicineTypeBtnOnClickListener medicineTypeBtnOnClickListener = new MedicineTypeBtnOnClickListener();

        pill.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        liquidMedicine.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        powderedMedicine.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        ointment.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        prescriptionDrug.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);

        // 약 복용유무 버튼 리스너
        TakingBtnOnClickListener takingBtnOnClickListener = new TakingBtnOnClickListener();
        takingBtnGroup.setOnCheckedChangeListener(takingBtnOnClickListener);


        ugeubiDialog = new UgeubiDialog(this, positiveListener, negativeListener);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


    /** 약 상세조회 API 호출 **/
    public void getMedicineDetailInfo(int medicineId) {

        // token
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.getMedicineDetailInfo(accessToken, medicineId).enqueue(new Callback<MedicineItemDTO>() {
            @Override
            public void onResponse(Call<MedicineItemDTO> call, Response<MedicineItemDTO> response) {
                if(response.isSuccessful()){
                    Log.i("info", "통신성공");
                    MedicineItemDTO apiResponse = response.body();
                    Log.i("medicine_kit", "medicineName: " + apiResponse.getMedicineName());


                    medicine_name.setText(apiResponse.getMedicineName());
                    medicine_memo.setText(apiResponse.getMemo());

                    if (apiResponse.getMedicineType().equals("알약")) {
                        pill.setChecked(true);
                        taking_dose_num_section.setVisibility(View.VISIBLE);
                        taking_dose_num.setText(apiResponse.getTakingInfo().takingNumber + "");
                    } else if (apiResponse.getMedicineType().equals("물약")) {
                        liquidMedicine.setChecked(true);
                    } else if (apiResponse.getMedicineType().equals("가루약")) {
                        powderedMedicine.setChecked(true);
                    } else if (apiResponse.getMedicineType().equals("연고")) {
                        ointment.setChecked(true);
                    } else {
                        prescriptionDrug.setChecked(true);
                        expiration_date_txt.setText("최종복용일");
                    }

                    expiration_date.setText(apiResponse.getMedicineValidTerm());

                    if (apiResponse.isTaken()) {
                        takingBtn.setChecked(true);

                        for (String day : apiResponse.getTakingInfo().takingDayOfWeek) {
                            if (day.equals("월")) {
                                mon.setChecked(true);
                            } else if (day.equals("화")) {
                                tue.setChecked(true);
                            } else if (day.equals("수")) {
                                wed.setChecked(true);
                            } else if (day.equals("목")) {
                                thu.setChecked(true);
                            } else if (day.equals("금")) {
                                fri.setChecked(true);
                            } else if (day.equals("토")) {
                                sat.setChecked(true);
                            } else if (day.equals("일")) {
                                sun.setChecked(true);
                            }
                        }


                        for (String time : apiResponse.getTakingInfo().takingTime) {
                            takingTimeListAdapter.addItem(time.substring(0,5));
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<MedicineItemDTO> call, Throwable t) {
                Log.e("error", "통신실패(register detail)" + t.getCause());
            }
        });

    }


    /** 약종류 버튼 리스너 **/
    class MedicineTypeBtnOnClickListener implements ToggleButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setAllBtnUnChecked();
            takingDay_section.setVisibility(View.GONE);
            takingTime_section.setVisibility(View.GONE);
            expiration_date_section.setVisibility(View.VISIBLE);
            isTaken_section.setVisibility(View.VISIBLE);


            buttonView.setChecked(isChecked);

            if (buttonView.getId() == R.id.prescriptionDrug) {
                takingDay_section.setVisibility(View.VISIBLE);
                takingTime_section.setVisibility(View.VISIBLE);
                isTaken_section.setVisibility(View.GONE);
            }
        }

        void setAllBtnUnChecked() {
            pill.setChecked(false);
            liquidMedicine.setChecked(false);
            powderedMedicine.setChecked(false);
            ointment.setChecked(false);
            prescriptionDrug.setChecked(false);
        }
    }


    /** 약 복용유무 버튼 리스너 **/
    class TakingBtnOnClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.takingBtn) {
                takingDay_section.setVisibility(View.VISIBLE);
                takingTime_section.setVisibility(View.VISIBLE);
            } else {
                takingDay_section.setVisibility(View.GONE);
                takingTime_section.setVisibility(View.GONE);
            }
        }
    }


    class LinearLayoutManagerWrapper extends LinearLayoutManager {
        LinearLayoutManagerWrapper(Context context) {
            super(context);
        }

        LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

    }


    public void deleteMedicine(int medicineID){


        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.deleteMedicine(accessToken,medicineID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    finish();
                    Toast.makeText(getApplicationContext(), "해당 약이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                    mainActivity.finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("tabPosition", 1);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("error", "통신실패(register medicine)" + t.getCause());
            }
        });
    }


    /** 다이얼로그  **/

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            /** deleteAPI 호출 **/
            deleteMedicine(medicineId);
            ugeubiDialog.dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            ugeubiDialog.dismiss();
        }
    };




}

