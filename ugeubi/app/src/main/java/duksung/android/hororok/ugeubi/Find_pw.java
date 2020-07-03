package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.retrofit.FInd.AuthenticationPwDTO;
import duksung.android.hororok.ugeubi.retrofit.FInd.FindPwDTO;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.Sign_up_email_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Find_pw extends Activity {

    // Retrofit
    private RetrofitInterface apiService;

    // 비밀번호 찾기와 관련된 필드
    TextView new_pw, new_pw_cf;
    Button login_btn, authorize_btn, authorize_btn4;
    FrameLayout frame3;

    // 아이디, 이메일 값
    EditText userid, email, tempPw;

    // 인증번호 요청에 대한 카운트 다운
    TextView timer_txt;
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);


        // Retrofit
        apiService = RetrofitClient.getService();


        // 레이아웃 연결
        login_btn = findViewById(R.id.find_pw_login_btn); // 로그인 버튼
        authorize_btn = findViewById(R.id.authorize_btn); // 이메일로 인증번호 요청 버튼
        frame3 = findViewById(R.id.finde_pw_framelayout3); // 임시 비밀번호 입력 레이아웃
        authorize_btn4 = findViewById(R.id.find_pw_authorize_btn4); // 임시 비밀번호 확인 버튼
        new_pw = findViewById(R.id.new_pw); // 새로운 비밀번호 입력
        new_pw_cf = findViewById(R.id.new_pw_cf); // 새로운 비밀번호 확인
        userid = findViewById(R.id.user_id);
        email = findViewById(R.id.user_email);
        tempPw = findViewById(R.id.temp_pw); // 임시 비밀번호 입력

        // 카운트 다운
        timer_txt = findViewById(R.id.find_pw_timer);

        // 이메일 인증번호 요청 버튼을 클릭했을 때
        authorize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 사용자의 이름과 이메일을 받아서 sendEmail()호출

                sendEmail(userid.getText().toString(), email.getText().toString());
            }
        });


        // 인증번호 확인 버튼
        authorize_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendtemporaryPassword(userid.getText().toString(),
                                      email.getText().toString(),
                                      tempPw.getText().toString());


            }
        });


        // 로그인 버튼을 눌렀을 때
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 사용자가 입력한 이메일이 존재하지 않는다면
                //Toast.makeText(getApplication(), "존재하지 않는 이메일 입니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 카운트다운메소드
     **/
    public void countDownTimer() { //카운트 다운 메소드

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                // Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    timer_txt.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    timer_txt.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }


            @Override
            public void onFinish() { // 타이머 시간이 다 되었다면
                Toast.makeText(getApplicationContext(), "인증시간 만료", Toast.LENGTH_SHORT).show();

            }

        }.start();


    }


    /**
     * 비밀번호 찾기 중 이메일 인증 API 호출
     **/
    public void sendEmail(String userId, String email) {

        FindPwDTO findPwDTO = new FindPwDTO(userId, email);
        apiService.find_pw_email(findPwDTO).enqueue(new Callback<FindPwDTO>() {
            @Override
            public void onResponse(Call<FindPwDTO> call, Response<FindPwDTO> response) {

                Log.i("info", "code : " + response.code());

                if (response.isSuccessful()) {
                    Log.i("info", "통신 성공(email), code : " + response.code());
                    frame3.setVisibility(View.VISIBLE);


                } else if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "일치하는 계정이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FindPwDTO> call, Throwable t) {
                Log.e("error", "통신 실패(email)" + t.getMessage());
            }
        });
    }


    public void sendtemporaryPassword(String userId, String email, String temporaryPassword){

        AuthenticationPwDTO authenticationPwDTO = new AuthenticationPwDTO(userId, email, temporaryPassword);
        apiService.authenticate_num_pw(authenticationPwDTO).enqueue(new Callback<AuthenticationPwDTO>() {
            @Override
            public void onResponse(Call<AuthenticationPwDTO> call, Response<AuthenticationPwDTO> response) {
                if(response.isSuccessful()){
                    Log.i("info", "통신 성공(find_pw_temporarypw), code : " + response.code());
                    frame3.setVisibility(View.GONE);
                    new_pw.setVisibility(View.VISIBLE);
                    new_pw_cf.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(), "임시비밀번호가 확인되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "임시비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationPwDTO> call, Throwable t) {
                Log.e("error", "통신 실패(email)" + t.getMessage());
            }
        });
    }
}
