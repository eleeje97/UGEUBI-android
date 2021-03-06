package duksung.android.hororok.ugeubi.registerMedicine;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import duksung.android.hororok.ugeubi.MainActivity;
import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineDTO;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineResultDTO;
import duksung.android.hororok.ugeubi.medicine.Medicine_kit_fragment;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.medicine.TakingInfoDayDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class RegisterMedicine extends AppCompatActivity {

    MainActivity mainActivity = (MainActivity) MainActivity.mainActivity;



    // retrofit
    RetrofitInterface apiService;

    public final String PREFERENCE = "ugeubi.preference";

    // medicineDTO
    MedicineDTO medicineDTO = null;

    // ??? ?????? EditText
    EditText medicineName, memo;

    // ??? ?????? ??????
    ToggleButton pill, liquidMedicine, powderedMedicine, ointment, prescriptionDrug;

    // ???????????? TextView
    TextView expirationDate;

    // DatePickerDialog
    SpinnerDatePickerDialog spinnerDatePickerDialog;

    // ??? ???????????? ????????????
    RadioGroup takingBtnGroup;

    // ???????????? EditText
    EditText takingDays;

    // ???????????? ??????
    RadioGroup takingTypeBtnGroup;

    // ?????? ??????
    ToggleButton mon, tue, wed, thu, fri, sat, sun;
    ToggleButton[] week = new ToggleButton[7];


    // ?????? ?????? ??????????????????
    TextView takingMedicine_q, medicinetext;

    LinearLayout takinglayout;

    // ???????????? ??????
    ToggleButton term_everyday, term2, term3, term4, term5, term6, term_week;

    // ??? ???????????? ?????????
    Spinner spinner;

    // ??? ???????????? ?????????
    RecyclerView takingTimeList;
    TakingTimeListAdapter takingTimeListAdapter;

    TimePickerDialog timePickerDialog;

    // ??? ???????????? EditText
    EditText takingDoseNum;

    TextView textView3;

    // ?????? ??? ?????? ??????
    Button back_btn, add_btn;


    // section
    LinearLayout expiration_date_section, isTaken_section, prescription_section, takingType_section, takingDay_section, takingTerm_section, takingTime_section;


    public String medicineType_txt = "PILL";
    public String medicineValidterm;
    public boolean isTaken = false;
    public Date date_txt;
    public List<String> takingTime;
    public List<TakingTimeData> takingTimeDataList;
    public List<String> takingDayOfWeek;


    public boolean checkedName = false;
    public boolean checkedTakingNum = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        /************************** init ***************************/

        // retrofit
        apiService = RetrofitClient.getService();

        // ??? ?????? EditText
        medicineName = findViewById(R.id.medicine_name);

        // ??? ??????
        memo = findViewById(R.id.medicine_memo);

        takingMedicine_q = findViewById(R.id.textView6);
        medicinetext = findViewById(R.id.textView7);
        takinglayout = findViewById(R.id.taking_layout);
        textView3 = findViewById(R.id.textView3);



        // ??? ?????? ??????
        pill = findViewById(R.id.pill);
        liquidMedicine = findViewById(R.id.liquidMedicine);
        powderedMedicine = findViewById(R.id.powderedMedicine);
        ointment = findViewById(R.id.ointment);
        prescriptionDrug = findViewById(R.id.prescriptionDrug);

        // ???????????? TextView
        expirationDate = findViewById(R.id.expiration_date);

        // ??? ???????????? ????????????
        takingBtnGroup = findViewById(R.id.takingBtnGroup);

        // ???????????? EditText
        takingDays = findViewById(R.id.taking_days);

        // ???????????? ??????
        takingTypeBtnGroup = findViewById(R.id.takingtype);

        // ?????? ??????
        week[0] = findViewById(R.id.mon);
        week[1] = findViewById(R.id.tue);
        week[2] = findViewById(R.id.wed);
        week[3] = findViewById(R.id.thu);
        week[4] = findViewById(R.id.fri);
        week[5] = findViewById(R.id.sat);
        week[6] = findViewById(R.id.sun);


        // ???????????? ??????
        /*
        term_everyday = findViewById(R.id.term_everyday);
        term2 = findViewById(R.id.term_2);
        term3 = findViewById(R.id.term_3);
        term4 = findViewById(R.id.term_4);
        term5 = findViewById(R.id.term_5);
        term6 = findViewById(R.id.term_6);
        term_week = findViewById(R.id.term_week);
        */
        // ??? ???????????? ?????????
        spinner = findViewById(R.id.taking_num_spinner);

        // ??? ???????????? ?????????
        takingTimeList = findViewById(R.id.takingTimeList);

        // ??? ???????????? EditText
        takingDoseNum = findViewById(R.id.taking_dose_num);

        // section
        expiration_date_section = findViewById(R.id.expiration_date_section);
        isTaken_section = findViewById(R.id.isTaken_section);
        prescription_section = findViewById(R.id.prescription_section);
        takingType_section = findViewById(R.id.taking_type_section);
        takingDay_section = findViewById(R.id.taking_day_section);
        takingTerm_section = findViewById(R.id.taking_term_section);
        takingTime_section = findViewById(R.id.taking_time_section);

        // ?????? ??? ?????? ??????
        back_btn = findViewById(R.id.btn_back);
        add_btn = findViewById(R.id.btn_ok);


        week[0].setChecked(true);


        /************************** init end ***************************/



        medicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(medicineName.getText().toString().length() != 0){
                    checkedName = true;
                }
                else{
                    checkedName = false;
                }
            }
        });

        takingDoseNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(takingDoseNum.getText().toString().length() != 0){
                    checkedTakingNum = true;
                }
                else{
                    checkedTakingNum = false;
                }
            }
        });



        // ???????????? ??? ??? ?????? ?????? ????????? ??????
        View.OnClickListener btn_listener = v -> {

            // ????????? id??? ????????? ??????
            switch (v.getId()){

                // back_btn
                case R.id.btn_back:
                    onBackPressed();
                    break;



                // add_btn
                case R.id.btn_ok:

                    // ?????? ??????
                    try {
                        date_txt = new SimpleDateFormat("yyyy??? MM??? dd???").parse(expirationDate.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        medicineValidterm = sdf.format(date_txt);

                    } catch (ParseException e) { e.printStackTrace(); }

                    // ????????? ?????? ???????????? ??????
                    int count = 0;
                    for(int i=0; i<week.length; i++){
                        if(week[i].isChecked()){
                            count ++;
                        }
                    }

                    Log.i("info","count : " + count );


                    takingDayOfWeek = new ArrayList<>(count);
                    for(int i=0; i<week.length; i++){
                        if(week[i].isChecked()) {
                            takingDayOfWeek.add(week[i].getText().toString());
                        }
                    }

                    // ????????? ?????? ???????????? ??????
                    int countList = takingTimeListAdapter.getList().size();
                    takingTimeDataList = takingTimeListAdapter.getList();
                    takingTime = new ArrayList<>(countList);

                    for(TakingTimeData takingTimeData : takingTimeDataList){
                        takingTime.add(takingTimeData.time);
                    }
                    Log.e("timeList", "countList: " + countList);
                    Log.e("timeList", "takingTime: " + takingTime.size());

                    // ??????, ??????, ????????? ??????
                    if(medicineType_txt.equals("CREAM") || medicineType_txt.equals("LIQUID") || medicineType_txt.equals("POWDER")  ){
                        takingDoseNum.setText("0");
                    }


                    if(medicineType_txt.equals("PRESCRIPTION")){
                        isTaken = true;
                    }


                    Log.i("info","????????? : " + medicineName.getText().toString());
                    Log.i("info","????????? : " + medicineType_txt);
                    Log.i("info","??????????????? : " + medicineValidterm);
                    Log.i("info","??? ???????????? : " + isTaken);
                    Log.i("info","??? ?????? : " + takingDayOfWeek.size());
                    Log.i("info","??? ?????? : " + takingTime.size());
                    Log.i("info","??? ?????? : " + memo.getText().toString());



                    if(takingDoseNum.getText().length() !=0 && medicineName.getText().length() != 0){

                        /** ??? ?????? API ?????? **/

                        Log.i("info","????????? : " + medicineName.getText().toString());
                        Log.i("info","????????? : " + medicineType_txt);
                        Log.i("info","??????????????? : " + medicineValidterm);
                        Log.i("info","??? ???????????? : " + isTaken);
                        Log.i("info","??? ?????? : " + takingDayOfWeek.size());
                        Log.i("info","??? ?????? : " + takingTime.size());
                        Log.i("info","??? ?????? : " + memo.getText().toString());


                        registerMedicine(new MedicineDTO(medicineName.getText().toString(),
                                medicineType_txt, medicineValidterm, isTaken,
                                memo.getText().toString(), new TakingInfoDayDTO(takingTime,
                                takingDayOfWeek, Integer.parseInt(takingDoseNum.getText().toString()))));


                        break;

                    }
                    else if(medicineName.getText().length() !=0 && !isTaken){
                        takingDoseNum.setText("0");
                        registerMedicine(new MedicineDTO(medicineName.getText().toString(),
                                medicineType_txt, medicineValidterm, isTaken,
                                memo.getText().toString(), new TakingInfoDayDTO(takingTime,
                                takingDayOfWeek, Integer.parseInt(takingDoseNum.getText().toString()))));

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"???????????? ???????????????!",Toast.LENGTH_SHORT).show();
                    }






            }
        };

        // ???????????? ??? ?????? ?????? ????????? ??????
        back_btn.setOnClickListener(btn_listener);
        add_btn.setOnClickListener(btn_listener);





        // ??? ?????? ?????? ????????? ??????
        MedicineTypeBtnOnClickListener medicineTypeBtnOnClickListener = new MedicineTypeBtnOnClickListener();

        pill.setOnClickListener(medicineTypeBtnOnClickListener);
        liquidMedicine.setOnClickListener(medicineTypeBtnOnClickListener);
        powderedMedicine.setOnClickListener(medicineTypeBtnOnClickListener);
        ointment.setOnClickListener(medicineTypeBtnOnClickListener);
        prescriptionDrug.setOnClickListener(medicineTypeBtnOnClickListener);



        /** ???????????? DatePickerDialog **/
        Date currentTime = Calendar.getInstance().getTime();


        medicineValidterm = new SimpleDateFormat("yyyy-mm-dd",Locale.getDefault()).format(currentTime);
        String today = new SimpleDateFormat("yyyy??? MM??? dd???", Locale.getDefault()).format(currentTime);
        expirationDate.setText(today);

        spinnerDatePickerDialog = new SpinnerDatePickerDialog(this);

        spinnerDatePickerDialog.setDialogListener(new SpinnerDatePickerDialog.SpinnerDatePickerDialogListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveClicked(int yy, int mm, int dd) {
                expirationDate.setText(yy + "??? " + mm + "??? " + dd + "???");
                // medicineValidterm = yy + "-" + mm + "-" + dd ;
            }

            @Override
            public void onNegativeClicked() {

            }
        });

        expirationDate.setOnClickListener(v -> spinnerDatePickerDialog.show());


        // ??? ???????????? ?????? ?????????
        TakingBtnOnClickListener takingBtnOnClickListener = new TakingBtnOnClickListener();
        takingBtnGroup.setOnCheckedChangeListener(takingBtnOnClickListener);


        // ??? ???????????? ?????? ?????????
        TakingTypeBtnOnClickListener takingTypeBtnOnClickListener = new TakingTypeBtnOnClickListener();
        takingTypeBtnGroup.setOnCheckedChangeListener(takingTypeBtnOnClickListener);

        // ???????????? ?????? ?????????
        /*
        TakingTermBtnOnClickListener takingTermBtnOnClickListener = new TakingTermBtnOnClickListener();
        term_everyday.setOnClickListener(takingTermBtnOnClickListener);
        term2.setOnClickListener(takingTermBtnOnClickListener);
        term3.setOnClickListener(takingTermBtnOnClickListener);
        term4.setOnClickListener(takingTermBtnOnClickListener);
        term5.setOnClickListener(takingTermBtnOnClickListener);
        term6.setOnClickListener(takingTermBtnOnClickListener);
        term_week.setOnClickListener(takingTermBtnOnClickListener);
        */

        // ??? ???????????? ???????????? ????????? ??????
        String[] item = {"1???", "2???", "3???", "4???", "5???"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.taking_num_spinner_item, item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(spinnerAdapter);

        SpinnerOnItemSelectedListener spinnerOnItemSelectedListener = new SpinnerOnItemSelectedListener();
        spinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);


        // ??? ???????????? ?????????
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false);
        takingTimeList.setLayoutManager(linearLayoutManagerWrapper);
        takingTimeListAdapter = new TakingTimeListAdapter(this);
        takingTimeList.setAdapter(takingTimeListAdapter);

        takingTimeList.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), takingTimeList, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Log.e("Test", position + "??????");

                Toast.makeText(getApplicationContext(), "????????????",Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(RegisterMedicine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        takingTimeListAdapter.changeTime(position, hourOfDay, minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                timePickerDialog.show();


            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        takingTimeListAdapter.addItem("09:00");



    }


    // back func
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }



    /** ????????? ?????? ????????? **/
    class MedicineTypeBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();
            takingType_section.setVisibility(View.GONE);
            takingDay_section.setVisibility(View.GONE);
            takingTerm_section.setVisibility(View.GONE);
            takingTime_section.setVisibility(View.GONE);
            prescription_section.setVisibility(View.GONE);
            expiration_date_section.setVisibility(VISIBLE);
            isTaken_section.setVisibility(VISIBLE);
            takingBtnGroup.check(R.id.notTakingBtn);


            //PILL(??????),LIQUID(??????), POWDER(?????????),CREAM(??????), PRESCRIPTION(?????????)
            switch (view.getId()) {
                case R.id.pill :
                    pill.setChecked(true);
                    medicineType_txt = "PILL";
                    textView3.setText("????????????");
                    takingMedicine_q.setText("?????? ?????? ???????????????????");
                    medicinetext.setVisibility(VISIBLE); // ??? ??? ??????
                    takinglayout.setVisibility(VISIBLE);
                    break ;
                case R.id.liquidMedicine :
                    liquidMedicine.setChecked(true);
                    medicineType_txt = "LIQUID";
                    textView3.setText("????????????");
                    takingMedicine_q.setText("?????? ?????? ???????????????????");
                    medicinetext.setVisibility(View.GONE); // ??? ??? ??????
                    takinglayout.setVisibility(View.GONE);
                    break ;
                case R.id.powderedMedicine :
                    powderedMedicine.setChecked(true);
                    medicineType_txt = "POWDER";
                    textView3.setText("????????????");
                    takingMedicine_q.setText("?????? ?????? ???????????????????");
                    medicinetext.setVisibility(View.GONE); // ??? ??? ??????
                    takinglayout.setVisibility(View.GONE);
                    break ;
                case R.id.ointment :
                    ointment.setChecked(true);
                    medicineType_txt = "CREAM";
                    textView3.setText("????????????");
                    takingMedicine_q.setText("?????? ?????? ???????????????????");
                    medicinetext.setVisibility(View.GONE);
                    takinglayout.setVisibility(View.GONE);
                    break ;
                case R.id.prescriptionDrug :
                    prescriptionDrug.setChecked(true);
                    medicineType_txt = "PRESCRIPTION";
                    textView3.setText("???????????????");
                    takingMedicine_q.setText("?????? ?????? ???????????????????");
                    takingType_section.setVisibility(VISIBLE);
                    takingDay_section.setVisibility(VISIBLE);
                    takingTime_section.setVisibility(VISIBLE);
                    medicinetext.setVisibility(View.GONE);
                    takinglayout.setVisibility(View.GONE);
                    isTaken_section.setVisibility(View.GONE);
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




    /** ??? ???????????? ?????? ????????? **/
    class TakingBtnOnClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.takingBtn) {
                isTaken = true;
                takingType_section.setVisibility(VISIBLE);
                takingDay_section.setVisibility(VISIBLE);
                takingTime_section.setVisibility(VISIBLE);
                takingTypeBtnGroup.check(R.id.dayOption);
            } else {
                isTaken = false;
                takingType_section.setVisibility(View.GONE);
                takingDay_section.setVisibility(View.GONE);
                takingTerm_section.setVisibility(View.GONE);
                takingTime_section.setVisibility(View.GONE);
            }
        }
    }


    /** ??? ???????????? ?????? ????????? **/
    class TakingTypeBtnOnClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.dayOption) {
                takingTerm_section.setVisibility(View.GONE);
                takingDay_section.setVisibility(VISIBLE);
            } else {
                takingDay_section.setVisibility(View.GONE);
                takingTerm_section.setVisibility(VISIBLE);
            }
        }
    }


    /** ???????????? ?????? ????????? **/
    /*
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
    */

    /** ??? ???????????? ????????? ????????? **/
    class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view, int position, long id){
            takingTimeListAdapter.clear();
            for (int i = 0; i <= position; i++) {
                takingTimeListAdapter.addItem("09:00");
            }
        }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

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


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }



    /** ??? ?????? API ?????? **/
    public void registerMedicine(MedicineDTO medicineDTO){

        // token
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.register_medicine(accessToken, medicineDTO).enqueue(new Callback<MedicineResultDTO>() {

            @Override
            public void onResponse(Call<MedicineResultDTO> call, Response<MedicineResultDTO> response) {

                Log.e("error","code : " + response.code());

                if(response.isSuccessful()){


                    Log.i("info", "????????????(register medicine)");
                    finish();

                    // ??? ?????? ??? > ????????? ???????????? ???????????? ??????
                    mainActivity.finish();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("tabPosition", 1);
                    startActivity(intent);

                }

                else if(response.code() == 400){
                    Toast.makeText(getApplicationContext(), "???????????? ???????????????!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MedicineResultDTO> call, Throwable t) {
                Log.e("error", "????????????(register medicine)" + t.getCause());
            }
        });

    }
}
