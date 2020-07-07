package duksung.android.hororok.ugeubi.registerMedicine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

import duksung.android.hororok.ugeubi.R;

public class SpinnerDatePickerDialog extends Dialog implements View.OnClickListener {

    private Button okButton;
    private Button cancelButton;

    private DatePicker datePicker;

    private int mYear = 0, mMonth = 0, mDay = 0;

    private SpinnerDatePickerDialogListener spinnerDatePickerDialogListener;

    public SpinnerDatePickerDialog(Context context) {
        super(context);
    }


    //인터페이스 설정
    interface SpinnerDatePickerDialogListener {
        void onPositiveClicked(int yy, int mm, int dd);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(SpinnerDatePickerDialogListener spinnerDatePickerDialogListener){
        this.spinnerDatePickerDialogListener = spinnerDatePickerDialogListener;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외의 화면은 흐리게
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_date_picker);

        okButton = findViewById(R.id.ok_btn);
        cancelButton = findViewById(R.id.cancel_btn);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        datePicker = findViewById(R.id.datepicker);

        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(mYear, mMonth, mDay,mOnDateChangedListener);

    }

    android.widget.DatePicker.OnDateChangedListener mOnDateChangedListener = (datePicker, yy, mm, dd) -> {
        mYear = yy;
        mMonth = mm;
        mDay = dd;
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_btn: //확인 버튼을 눌렀을 때

                //인터페이스의 함수를 호출하여 변수에 저장된 값들을 Activity로 전달
                spinnerDatePickerDialogListener.onPositiveClicked(mYear,mMonth + 1,mDay);
                dismiss();
                break;

            case R.id.cancel_btn: //취소 버튼을 눌렀을 때
                cancel();
                break;
        }
    }

}




