package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePicker extends Activity {


    private int mYear =0, mMonth=0, mDay=0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);



        // 현재 날짜를 보여줄 텍스트 뷰
        TextView current_day = findViewById(R.id.expiration_date);



        current_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClick(v);
                Toast.makeText(getApplicationContext(), "눌림", Toast.LENGTH_SHORT).show();

            }
        });
        Calendar calendar = new GregorianCalendar();

        mYear = calendar.get(Calendar.YEAR);

        mMonth = calendar.get(Calendar.MONTH);

        mDay = calendar.get(Calendar.DAY_OF_MONTH);



        android.widget.DatePicker datePicker = findViewById(R.id.datepicker);

        datePicker.init(mYear, mMonth, mDay,mOnDateChangedListener);

    }



    public void mOnClick(View v){

        Intent intent = new Intent();

        intent.putExtra("mYear",mYear);

        intent.putExtra("mMonth", mMonth);

        intent.putExtra("mDay", mDay);

        setResult(RESULT_OK, intent);

        finish();

    }



    android.widget.DatePicker.OnDateChangedListener mOnDateChangedListener = new android.widget.DatePicker.OnDateChangedListener(){

        @Override

        public void onDateChanged(android.widget.DatePicker datePicker, int yy, int mm, int dd) {

            mYear = yy;

            mMonth = mm;

            mDay = dd;

        }

    };

}