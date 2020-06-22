package duksung.android.hororok.ugeubi.medicine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.R;

public class Medicine_kit_detail extends Activity {

    Button back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_kit_detail);



        back_btn = findViewById(R.id.medicine_detail_back_btn);

        // 뒤로가기 버튼이 눌렸다면
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로가기 함수 호출
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}

