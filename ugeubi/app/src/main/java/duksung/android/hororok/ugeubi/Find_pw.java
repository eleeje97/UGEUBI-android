package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class Find_pw extends Activity {


    TextView new_pw, new_pw_cf;
    Button login_btn, authorize_btn, authorize_btn4;
    FrameLayout frame3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);


        // 레이아웃 연결
        login_btn = findViewById(R.id.find_pw_login_btn); // 로그인 버튼
        authorize_btn = findViewById(R.id.authorize_btn); // 이메일로 인증번호 요청 버튼
        frame3 = findViewById(R.id.finde_pw_framelayout3); // 인증번호 입력 레이아웃
        authorize_btn4 = findViewById(R.id.find_pw_authorize_btn4); // 인증번호 확인 버튼

        new_pw = findViewById(R.id.new_pw); // 새로운 비밀번호 입력
        new_pw_cf = findViewById(R.id.new_pw_cf); // 새로운 비밀번호 확인



        // 이메일 인증번호 요청 버튼을 클릭했을 때
        authorize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자의 이메일로 인증번호 전송하는 코드

                // 인증번호 입력 박스 visible
                frame3.setVisibility(View.VISIBLE);
                authorize_btn.setText("재요청");
            }
        });



        // 인증번호 확인 버튼
        authorize_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 올바른 인증번호가 입력되었다면
                new_pw.setVisibility(View.VISIBLE);
                new_pw_cf.setVisibility(View.VISIBLE);


                // 올바른 인증번호가 입력되지 않았다면
                //new_pw.setVisibility(View.GONE);
                //new_pw_cf.setVisibility(View.GONE);
            }
        });


        // 로그인 버튼을 눌렀을 때
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 사용자가 입력한 이메일이 존재하지 않는다면
                //Toast.makeText(getApplication(), "존재하지 않는 이메일 입니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Find_pw_result.class);
                startActivity(intent);
            }
        });
    }
}
