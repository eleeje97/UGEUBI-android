package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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



        find_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                find_id(user_name.getText().toString(), user_email.getText().toString());
                //Intent intent = new Intent(getApplicationContext(), Find_id_result.class);
                //startActivity(intent);
            }
        });
    }


    /** findId API 호출 **/
    public void find_id(String userName, String email){

        apiService.find_id(userName, email).enqueue(new Callback<FindIdDTO>() {
            @Override
            public void onResponse(Call<FindIdDTO> call, Response<FindIdDTO> response) {

                Log.i("info", "Code : "+response.code());
                if(response.isSuccessful()){

                    Log.i("info", "아이디 : "+response.body().userId);
                }
            }

            @Override
            public void onFailure(Call<FindIdDTO> call, Throwable t) {
                Log.e("error", "통신실패(find_id)" + t.getMessage());

            }
        });
    }
}
