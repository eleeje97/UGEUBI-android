package duksung.android.hororok.ugeubi.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.Login;
import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.FInd.NewPwDTO;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Modify_pw extends Activity {

    // Retrofit
    private RetrofitInterface apiService;

    Button back_btn, confirm_btn;
    EditText new_pw, new_pw_cf, origin_pw;
    TextView password_txt;


    // 비밀번호 체크
    boolean checked_pw;
    boolean checked_origin_pw;

    // 이전 비밀번호 체크를 위한 작업
    public final String PREFERENCE = "ugeubi.preference";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);


        // Retrofit
        apiService = RetrofitClient.getService();


        // 뒤로가기 버튼 및 확인 버튼
        back_btn = findViewById(R.id.mf_pw_btn_back);
        confirm_btn = findViewById(R.id.confirm_btn);

        // 비밀번호 입력
        new_pw = findViewById(R.id.new_pw);
        new_pw_cf = findViewById(R.id.new_pw_cf);
        origin_pw = findViewById(R.id.origin_pw);
        password_txt = findViewById(R.id.pw_txt);

        // 비밀번호 체크
        checked_pw = false;
        checked_origin_pw = false;

        // 사용자 비밀번호 변경
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String userId = pref.getString("userId","");
        String userPassword = pref.getString("userPassword", "");


        // 뒤로 가기 버튼이 눌렸을 때
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        /** 비밀번호 입력 내용 같은지 확인 **/
        new_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if(!new_pw.getText().toString().equals(new_pw_cf.getText().toString())){
                    password_txt.setTextColor(Color.RED);
                    password_txt.setText("입력하신 비밀번호가 일치하지 않습니다.");
                    checked_pw = false;
                }
                else{
                    password_txt.setTextColor(R.color.mainColor);
                    password_txt.setText("입력하신 비밀번호가 일치합니다.");
                    checked_pw = true;
                }
            }
        });


        new_pw_cf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if(!new_pw.getText().toString().equals(new_pw_cf.getText().toString())){
                    password_txt.setTextColor(Color.RED);
                    password_txt.setText("입력하신 비밀번호가 일치하지 않습니다.");
                    checked_pw = false;
                }
                else{
                    password_txt.setTextColor(R.color.mainColor);
                    password_txt.setText("입력하신 비밀번호가 일치합니다.");
                    checked_pw = true;
                }
            }
        });





        // 확인 버튼이 눌렸을 때
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이전 비밀번호가 정확하게 입력되었는지 확인
                if(checked_origin_pw && origin_pw.getText().length() !=0){

                    // 새로운 비밀번호가 모두 일치하는지 확인
                    if(checked_pw && new_pw.getText().length() !=0 && new_pw_cf.getText().length() !=0 ){
                        setNewPassword(userId, new_pw.getText().toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"비밀번호가 올바르게 입력되었는지 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"이전 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        /** 이전 비밀번호 입력 체크 **/
        origin_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(origin_pw.getText().toString().equals(userPassword)){
                        checked_origin_pw = true;
                    }
                    else{
                        checked_origin_pw = false;
                    }
            }
        });

    }

    // back btn
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


    public void setNewPassword(String userId, String password){

        NewPwDTO newPwDTO = new NewPwDTO(userId, password);
        apiService.setNewPW(newPwDTO).enqueue(new Callback<NewPwDTO>() {
            @Override
            public void onResponse(Call<NewPwDTO> call, Response<NewPwDTO> response) {
                if(response.isSuccessful()){

                    // 비밀번호 변경이 되었다면 다시 로그인으로?
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "비밀번호 변경이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "입력하신 정보를 확인해주세요!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewPwDTO> call, Throwable t) {

            }
        });
    }
}
