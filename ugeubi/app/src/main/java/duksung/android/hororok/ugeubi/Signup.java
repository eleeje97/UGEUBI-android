package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Signup extends Activity {


    Button signup_btn;
    Button authorize_btn,authorize_btn2, authorize_btn_4;
    TextView user_email_cf, user_id;

    FrameLayout frameLayout3;

    String id = "uguebi";
    int num= 1234;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn = findViewById(R.id.btn_signin);

        authorize_btn = findViewById(R.id.authorize_btn); // 아이디 중복확인
        authorize_btn2 = findViewById(R.id.authorize_btn2); // 인증번호요청
        authorize_btn_4 = findViewById(R.id.authorize_btn4); // 확인

        user_email_cf = findViewById(R.id.user_email_cf);
        user_id = findViewById(R.id.signup_user_id);

        frameLayout3 = findViewById(R.id.framelayout3);

        // 아이디 중복 확인
        authorize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 아이디가 중복되었을 경우
                // 중복 처리 안돼 짜증나
                if(user_id.getText().equals("ugeubi")) {
                    Toast.makeText(getApplication(), "아이디가 중복 되었습니다.", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplication(), "사용할 수 있는 아이디 입니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // 인증번호 요청
        authorize_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout3.setVisibility(View.VISIBLE);
                authorize_btn2.setText("재요청");

            }
        });


        // 확인
        authorize_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout3.setVisibility(View.GONE);
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}
