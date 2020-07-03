package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.retrofit.FindIdDTO;
import duksung.android.hororok.ugeubi.retrofit.Login.UserDTO;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Find_id extends Activity {


    // Retrofit
    private RetrofitInterface apiService;


    // layout 구성요소
    Button find_id_btn;
    EditText user_name, user_email;

    // flag
    boolean checked_name, checked_email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);


        // Retrofit
        apiService = RetrofitClient.getService();


        // layout 연결
        find_id_btn = findViewById(R.id.find_id_btn);
        user_name = findViewById(R.id.user_name); // 사용자 이름
        user_email = findViewById(R.id.user_email); // 사용자 이메일


        // user_name과 user_email flag
        checked_name = false;
        checked_email = false;



        find_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checked_email && checked_name) {
                    find_id(user_name.getText().toString(), user_email.getText().toString());
                }

                else if(!checked_email && checked_name){
                    Toast.makeText(getApplicationContext(),"이메일을 다시 확인해주세요!", Toast.LENGTH_SHORT).show();
                }

                else if(checked_email && !checked_name){
                    Toast.makeText(getApplicationContext(),"이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"모든 입력란를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        /** 이름과 이메일 값이 입력되지 않거나 이메일 형식이 올바르지 않는 경우 **/
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(user_name.getText().length() != 0){
                    checked_name = true;
                }
                else{
                    checked_name = false;
                }
            }
        });


        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // 길이가 0이 아니면서
                if(user_email.getText().length() != 0){

                    // 이메일 양식을 만족하는지
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(user_email.getText().toString()).matches()){
                        checked_email = true;
                    }
                    else{
                        checked_email = false;
                    }
                }
                else{
                    checked_email = false;

                }
            }
        });
    }








    /** findId API 호출 **/
    public void find_id(String userName, String email){

        apiService.find_id(userName, email).enqueue(new Callback<FindIdDTO>() {
            @Override
            public void onResponse(Call<FindIdDTO> call, Response<FindIdDTO> response) {


                if(response.isSuccessful()){

                    Log.i("info", response.body().userId);
                    Log.i("info", response.body().getUserId());


                    Intent intent = new Intent(getApplicationContext(), Find_id_result.class);
                    intent.putExtra("userId", response.body().getUserId());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<FindIdDTO> call, Throwable t) {
                Log.e("error", "통신실패(find_id)" + t.getMessage());

            }
        });
    }
}
