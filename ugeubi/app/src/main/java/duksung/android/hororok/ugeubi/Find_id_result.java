package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.retrofit.FindIdDTO;

public class Find_id_result extends Activity {



    TextView coment;

    // userid 저장
    String userid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_result);


        coment = findViewById(R.id.coment_id);

        // 사용자 아이디 찾기 결과값을 받아온다.
        Intent intent = getIntent();



        if(intent.getStringExtra("userId") != null){
            userid = intent.getStringExtra("userId");
            coment.setText(userid);
        }

        else{
            coment.setText("일치하는 계정이 존재하지 않습니다. \n 다시 시도해주세요!");
        }

        String str = "고객님의 아이디는 \n" + userid + "입니다.";


        // userId 문자열만 색 변경 작업
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);

        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#45ba8e")), 9, str.length()-4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        coment.setText(spannableStringBuilder);


    }
}
