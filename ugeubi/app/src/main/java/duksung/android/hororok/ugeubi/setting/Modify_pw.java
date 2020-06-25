package duksung.android.hororok.ugeubi.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.R;

public class Modify_pw extends Activity {


    Button back_btn, confirm_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);


        back_btn = findViewById(R.id.mf_pw_btn_back);
        confirm_btn = findViewById(R.id.confirm_btn);


        // 뒤로 가기 버튼이 눌렸을 때
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // 확인 버튼이 눌렸을 때
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** 바뀐 내용 저장하는 기능 추가 하기 **/
                onBackPressed();
            }
        });
    }



    // back
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
