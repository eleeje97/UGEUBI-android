package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.retrofit.LoginDTO;
import duksung.android.hororok.ugeubi.retrofit.LoginResultDTO;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity {


    EditText userId, userPassword;

    TextView find_id, find_pw, signup;
    Button login_btn;

    RetrofitInterface apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.user_id);
        userPassword = findViewById(R.id.user_password);


        find_id = findViewById(R.id.find_id_txt);
        find_pw = findViewById(R.id.find_pw_txt);
        signup = findViewById(R.id.signup_txt);

        login_btn = findViewById(R.id.btn_login);

        apiService = RetrofitClient.getService();


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(userId.getText().toString(), userPassword.getText().toString());

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
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class );
                startActivity(intent);
            }
        });
    }


    /** 로그인 API 호출 **/
    public void login(String id, String password) {
        LoginDTO loginDTO = new LoginDTO(id, password);
        Log.e("login", id + ", " + password);

        apiService.login(loginDTO).enqueue(new Callback<LoginResultDTO>() {
            @Override
            public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                Log.e("login", "code: " + response.code());

                if (response.isSuccessful()) {
                    Log.e("login", "body: " + response.body());
                    Log.e("login", "accessToken: " + response.body().tokens.getAccessToken());

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(),"아이디와 비밀번호를 다시 확인해주세요.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResultDTO> call, Throwable t) {
                Log.e("login", "통신실패! " + t.getMessage());
            }
        });
    }

}
