package duksung.android.hororok.ugeubi.medicine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.MainActivity;
import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineItemDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medicine_kit_detail extends Activity {

    Button back_btn;

    public final String PREFERENCE = "ugeubi.preference";
    RetrofitInterface apiService;

    EditText medicine_name, medicine_memo;
    ToggleButton pill, liquidMedicine, powderedMedicine, ointment, prescriptionDrug;
    TextView expiration_date_txt, expiration_date;
    RadioButton takingBtn, notTakingBtn;
    ToggleButton mon, tue, wed, thu, fri, sat, sun;
    EditText taking_dose_num;

    // section
    LinearLayout expiration_date_section, isTaken_section, takingType_section, takingDay_section, takingTime_section;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_kit_detail);

        apiService = RetrofitClient.getService();


        /** init **/

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

        // section
        expiration_date_section = findViewById(R.id.expiration_date_section);
        isTaken_section = findViewById(R.id.isTaken_section);
        takingType_section = findViewById(R.id.taking_type_section);
        takingDay_section = findViewById(R.id.taking_day_section);
        takingTime_section = findViewById(R.id.taking_time_section);

        /** init end **/


        // 뒤로가기 버튼이 눌렸다면
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로가기 함수 호출
                onBackPressed();
            }
        });


        // 약 종류 버튼 리스너 설정
        MedicineTypeBtnOnClickListener medicineTypeBtnOnClickListener = new MedicineTypeBtnOnClickListener();

        pill.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        liquidMedicine.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        powderedMedicine.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        ointment.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);
        prescriptionDrug.setOnCheckedChangeListener(medicineTypeBtnOnClickListener);


        Intent intent = getIntent();
        int medicineId = intent.getIntExtra("medicineId", 0);
        getMedicineDetailInfo(medicineId);

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
                    Log.i("medicine_kit", "items.size: " + apiResponse.getMedicineName());


                    medicine_name.setText(apiResponse.getMedicineName());
                    medicine_memo.setText(apiResponse.getMemo());

                    if (apiResponse.getMedicineType().equals("알약")) {
                        pill.setChecked(true);
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
                    }






                }
            }

            @Override
            public void onFailure(Call<MedicineItemDTO> call, Throwable t) {
                Log.e("error", "통신실패(register medicine)" + t.getCause());
            }
        });

    }


    /** 약종류 버튼 리스너 **/
    class MedicineTypeBtnOnClickListener implements ToggleButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setAllBtnUnChecked();
            takingType_section.setVisibility(View.GONE);
            takingDay_section.setVisibility(View.GONE);
            takingTime_section.setVisibility(View.GONE);
            expiration_date_section.setVisibility(View.VISIBLE);
            isTaken_section.setVisibility(View.VISIBLE);


            buttonView.setChecked(isChecked);

            if (buttonView.getId() == R.id.prescriptionDrug) {
                takingType_section.setVisibility(View.VISIBLE);
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

}

