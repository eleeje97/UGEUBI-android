package duksung.android.hororok.ugeubi.registerMedicine;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import duksung.android.hororok.ugeubi.R;

public class RegisterMedicine extends AppCompatActivity {

    // 약 이름 EditText
    EditText medicineName;

    // 약 종류 버튼
    ToggleButton pill, liquidMedicine, powderedMedicine, ointment, prescriptionDrug;

    // 유통기한 TextView
    TextView expirationDate;

    // 약 복용유무 버튼그룹
    RadioGroup takingBtnGroup;

    // 복용기간 SeekBar
    SeekBar takingDays;

    // 복용타입 그룹
    RadioGroup takingTypeBtnGroup;

    // 요일 버튼
    ToggleButton mon, tue, wed, thu, fri, sat, sun;

    // 복용간격 버튼
    ToggleButton term_everyday, term2, term3, term4, term5, term6, term_week;

    // 약 복용횟수 스피너
    Spinner spinner;

    // 약 복용시간 리스트
    RecyclerView takingTimeList;

    // 약 복용갯수 EditText
    EditText takingDoseNum;


    // section
    LinearLayout generic_section, prescription_section, takingType_section, takingDay_section, takingTerm_section, takingTime_section;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        /************************** init ***************************/
        // 약 이름 EditText
        medicineName = findViewById(R.id.medicine_name);

        // 약 종류 버튼
        pill = findViewById(R.id.pill);
        liquidMedicine = findViewById(R.id.liquidMedicine);
        powderedMedicine = findViewById(R.id.powderedMedicine);
        ointment = findViewById(R.id.ointment);
        prescriptionDrug = findViewById(R.id.prescriptionDrug);

        // 유통기한 TextView
        expirationDate = findViewById(R.id.expiration_date);

        // 약 복용유무 버튼그룹
        takingBtnGroup = findViewById(R.id.takingBtnGroup);

        // 복용기간 SeekBar
        takingDays = findViewById(R.id.taking_days);

        // 복용타입 그룹
        takingTypeBtnGroup = findViewById(R.id.takingtype);

        // 요일 버튼
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        sun = findViewById(R.id.sun);

        // 복용간격 버튼
        term_everyday = findViewById(R.id.term_everyday);
        term2 = findViewById(R.id.term_2);
        term3 = findViewById(R.id.term_3);
        term4 = findViewById(R.id.term_4);
        term5 = findViewById(R.id.term_5);
        term6 = findViewById(R.id.term_6);
        term_week = findViewById(R.id.term_week);

        // 약 복용횟수 스피너
        spinner = findViewById(R.id.taking_num_spinner);

        // 약 복용시간 리스트
        takingTimeList = findViewById(R.id.takingTimeList);
        TakingTimeListAdapter takingTimeListAdapter;

        // 약 복용갯수 EditText
        takingDoseNum = findViewById(R.id.taking_dose_num);

        // section
        generic_section = findViewById(R.id.generic_section);
        prescription_section = findViewById(R.id.prescription_section);
        takingType_section = findViewById(R.id.taking_type_section);
        takingDay_section = findViewById(R.id.taking_day_section);
        takingTerm_section = findViewById(R.id.taking_term_section);
        takingTime_section = findViewById(R.id.taking_time_section);

        /************************** init end ***************************/




        // 약 종류 버튼 리스너 설정
        MedicineTypeBtnOnClickListener medicineTypeBtnOnClickListener = new MedicineTypeBtnOnClickListener();

        pill.setOnClickListener(medicineTypeBtnOnClickListener);
        liquidMedicine.setOnClickListener(medicineTypeBtnOnClickListener);
        powderedMedicine.setOnClickListener(medicineTypeBtnOnClickListener);
        ointment.setOnClickListener(medicineTypeBtnOnClickListener);
        prescriptionDrug.setOnClickListener(medicineTypeBtnOnClickListener);



        // 약 복용유무 버튼 리스너
        TakingBtnOnClickListener takingBtnOnClickListener = new TakingBtnOnClickListener();
        takingBtnGroup.setOnCheckedChangeListener(takingBtnOnClickListener);


        // 약 복용타입 버튼 리스너
        TakingTypeBtnOnClickListener takingTypeBtnOnClickListener = new TakingTypeBtnOnClickListener();
        takingTypeBtnGroup.setOnCheckedChangeListener(takingTypeBtnOnClickListener);

        // 복용간격 버튼 리스너
        TakingTermBtnOnClickListener takingTermBtnOnClickListener = new TakingTermBtnOnClickListener();
        term_everyday.setOnClickListener(takingTermBtnOnClickListener);
        term2.setOnClickListener(takingTermBtnOnClickListener);
        term3.setOnClickListener(takingTermBtnOnClickListener);
        term4.setOnClickListener(takingTermBtnOnClickListener);
        term5.setOnClickListener(takingTermBtnOnClickListener);
        term6.setOnClickListener(takingTermBtnOnClickListener);
        term_week.setOnClickListener(takingTermBtnOnClickListener);




        // 약 복용횟수 스피너에 아이템 담기
        String[] item = {"1번", "2번", "3번", "4번", "5번"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.taking_num_spinner_item, item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);

        spinner.setAdapter(spinnerAdapter);


        // 약 복용시간 리스트
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        takingTimeList.setLayoutManager(layoutManager);
        TakingTimeBtnOnClickListener takingTimeBtnOnClickListener = new TakingTimeBtnOnClickListener();
        takingTimeListAdapter = new TakingTimeListAdapter(this, takingTimeBtnOnClickListener);
        takingTimeList.setAdapter(takingTimeListAdapter);

        takingTimeListAdapter.addItem("AM 9:00");
        takingTimeListAdapter.addItem("PM 1:00");
        takingTimeListAdapter.addItem("PM 5:00");
        takingTimeListAdapter.addItem("PM 1:00");
        takingTimeListAdapter.addItem("PM 5:00");

    }


    /** 약종류 버튼 리스너 **/
    class MedicineTypeBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();
            takingType_section.setVisibility(View.GONE);
            takingDay_section.setVisibility(View.GONE);
            takingTime_section.setVisibility(View.GONE);
            prescription_section.setVisibility(View.GONE);
            generic_section.setVisibility(View.VISIBLE);
            takingBtnGroup.check(R.id.notTakingBtn);


            switch (view.getId()) {
                case R.id.pill :
                    pill.setChecked(true);
                    break ;
                case R.id.liquidMedicine :
                    liquidMedicine.setChecked(true);
                    break ;
                case R.id.powderedMedicine :
                    powderedMedicine.setChecked(true);
                    break ;
                case R.id.ointment :
                    ointment.setChecked(true);
                    break ;
                case R.id.prescriptionDrug :
                    prescriptionDrug.setChecked(true);
                    generic_section.setVisibility(View.GONE);
                    prescription_section.setVisibility(View.VISIBLE);
                    takingType_section.setVisibility(View.VISIBLE);
                    takingDay_section.setVisibility(View.VISIBLE);
                    takingTime_section.setVisibility(View.VISIBLE);
                    break ;
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
                takingType_section.setVisibility(View.VISIBLE);
                takingDay_section.setVisibility(View.VISIBLE);
                takingTime_section.setVisibility(View.VISIBLE);
            } else {
                takingType_section.setVisibility(View.GONE);
                takingDay_section.setVisibility(View.GONE);
                takingTime_section.setVisibility(View.GONE);
            }
        }
    }


    /** 약 복용타입 버튼 리스너 **/
    class TakingTypeBtnOnClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.dayOption) {
                takingTerm_section.setVisibility(View.GONE);
                takingDay_section.setVisibility(View.VISIBLE);
            } else {
                takingDay_section.setVisibility(View.GONE);
                takingTerm_section.setVisibility(View.VISIBLE);
            }
        }
    }


    /** 약종류 버튼 리스너 **/
    class TakingTermBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();


            switch (view.getId()) {
                case R.id.term_everyday :
                    term_everyday.setChecked(true);
                    break ;
                case R.id.term_2 :
                    term2.setChecked(true);
                    break ;
                case R.id.term_3 :
                    term3.setChecked(true);
                    break ;
                case R.id.term_4 :
                    term4.setChecked(true);
                    break ;
                case R.id.term_5 :
                    term5.setChecked(true);
                    break ;
                case R.id.term_6 :
                    term6.setChecked(true);
                    break ;
                case R.id.term_week :
                    term_week.setChecked(true);
                    break ;
            }
        }

        void setAllBtnUnChecked() {
            term_everyday.setChecked(false);
            term2.setChecked(false);
            term3.setChecked(false);
            term4.setChecked(false);
            term5.setChecked(false);
            term6.setChecked(false);
            term_week.setChecked(false);
        }
    }


    /** 약 복용시간 버튼 리스너 **/
    class TakingTimeBtnOnClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "날짜선택",Toast.LENGTH_SHORT).show();
        }
    }

}
