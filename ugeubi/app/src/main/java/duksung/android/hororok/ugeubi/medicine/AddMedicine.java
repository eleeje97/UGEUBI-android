package duksung.android.hororok.ugeubi.medicine;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import duksung.android.hororok.ugeubi.R;

public class AddMedicine extends Activity {


    private int mYear = 0, mMonth = 0, mDay = 0;
    TextView current_day;

    DateFormat fmDateandTime = DateFormat.getDateTimeInstance();

    Calendar SelectDate = Calendar.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_medicine);


        current_day = findViewById(R.id.expiration_date);


        current_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getApplicationContext(), DatePicker.class);

                //startActivityForResult(intent, D);
            }
        });

    /*
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                SelectDate.set(Calendar.YEAR, year);

                SelectDate.set(Calendar.MONTH, month);

                SelectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };


        View.OnClickListener Clickname = new View.OnClickListener() {

            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.expiration_date:

                        new DatePickerDialog(getApplicationContext(),d,

                                SelectDate.get(Calendar.YEAR),

                                SelectDate.get(Calendar.MONTH),

                                SelectDate.get(Calendar.DAY_OF_MONTH)).show();


                        break;

                }


            }
        }*/
    }
}