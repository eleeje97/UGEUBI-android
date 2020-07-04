package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.retrofit.Login.LoginDTO;
import duksung.android.hororok.ugeubi.retrofit.Login.LoginResultDTO;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    RetrofitInterface apiService;
    public final String PREFERENCE = "ugeubi.preference";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        apiService = RetrofitClient.getService();


        // 자동로그인
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String userId = pref.getString("userId","");
        String userPassword = pref.getString("userPassword", "");

        Log.e("Auto-login", "userid: " + userId);
        Log.e("Auto-login", "userPassword: " + userPassword);

        if((userId.length() > 0) && (userPassword.length() > 0)) {
            login(userId, userPassword);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new SplashHandler(), 1000);
        }

    }


    private class SplashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), Login.class));
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        // splash 화면에서 백버튼 누를 때, 동작 안하도록
    }


    /** 로그인 API 호출 **/
    public void login(String id, String password) {
        LoginDTO loginDTO = new LoginDTO(id, password);

        apiService.login(loginDTO).enqueue(new Callback<LoginResultDTO>() {
            @Override
            public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                Log.e("login", "code: " + response.code());

                if (response.isSuccessful()) {
                    Log.e("login", "body: " + response.body());
                    Log.e("login", "accessToken: " + response.body().tokens.getAccessToken());

                    // 사용자 accessToken 저장
                    SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("accessToken", response.body().tokens.getAccessToken());
                    editor.putString("userId", id);
                    editor.putString("userPassword", password);
                    editor.putString("userName", response.body().user.getUserName());
                    editor.putString("userEmail", response.body().user.getEmail());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    // 현재 액티비티 종료
                    finish();

                } else {
                    Log.e("login", "errorbody: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<LoginResultDTO> call, Throwable t) {
                Log.e("login", "통신실패! " + t.getMessage());
            }
        });
    }

}
