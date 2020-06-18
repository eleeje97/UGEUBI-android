package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Login extends Activity {



    TextView find_id, find_pw, signin;
    Button login_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        find_id = findViewById(R.id.find_id_txt);
        find_pw = findViewById(R.id.find_pw_txt);
        signin = findViewById(R.id.signin_txt);


        login_btn = findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent);
            }
        });

        // 아이디 찾기 클릭시
        find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find_id.class );
                startActivity(intent);
            }
        });

        // 패스워드 찾기 클릭시
        find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find_pw.class );
                startActivity(intent);
            }
        });


        // 회원가입 클릭시
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class );
                startActivity(intent);
            }
        });
    }
}
