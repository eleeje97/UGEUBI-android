package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import duksung.android.hororok.ugeubi.retrofit.Check_id_data;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.Sign_up_data;
import duksung.android.hororok.ugeubi.retrofit.Sign_up_email_data;
import duksung.android.hororok.ugeubi.retrofit.Sign_up_email_num;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Signup extends Activity {

    /** Retrofit **/
    private RetrofitInterface apiService;
    // 중복된 id 확인
    boolean availale_id;

    /** 카운트 다운 구현 **/
    TextView timer_txt;
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)


    /** 회원가입 기능 구현 **/
    Button signup_btn;
    Button authorize_btn,authorize_btn2, authorize_btn_4;
    TextView user_email_cf, user_id, user_email2;
    FrameLayout frameLayout3;


    // Test
    String id = "uguebi";
    int num= 1234;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn = findViewById(R.id.btn_signin);
        timer_txt = findViewById(R.id.signup_timer_txt); // 타이머 시간 텍스트
        authorize_btn = findViewById(R.id.authorize_btn); // 아이디 중복확인 버튼
        authorize_btn2 = findViewById(R.id.authorize_btn2); // 인증번호 요청 버튼
        authorize_btn_4 = findViewById(R.id.authorize_btn4); // 확인 버튼
        user_email_cf = findViewById(R.id.user_email_cf);


        user_id = findViewById(R.id.signup_user_id);  //userId
        user_email2 = findViewById(R.id.user_email2); // useremail
        frameLayout3 = findViewById(R.id.framelayout3);

        /** Retrofit **/
        // 서비스 객체 호출
        apiService = RetrofitClient.getService();


        /** 아이디 중복 확인 버튼을 눌렀을 때  **/
        authorize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // id를 입력하지 않았다면
                if(user_id.getText() == null){
                    Toast.makeText(getApplication(), "id를 입력해주세요!",Toast.LENGTH_SHORT).show();
                }

                // id를 입력하였다면
                else{
                    //call get method
                    getCheck_id(user_id.getText().toString());

                }
            }
        });



        /** 인증번호 요청 버튼을 눌렀을 때  **/
        authorize_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sign_up_email_data email_data;

                // 인증번호 입력 박스 visible
                if(authorize_btn2.getText().equals("인증요청")) {
                    frameLayout3.setVisibility(View.VISIBLE);
                    authorize_btn2.setText("재요청");
                }

                // 재요청시
                else if(authorize_btn2.getText().equals("재요청")){
                    countDownTimer.cancel();
                }

                //  카운트 다운 시작
                countDownTimer();

                // email 전송
                sendEmail(new Sign_up_email_data(user_email2.getText().toString()));
            }
        });

        /** 인증 확인 버튼을 누르면 인증 요청 번호 입력 란이 사라진다. **/
        authorize_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authenticateNum(user_email2.getText().toString(), user_email_cf.getText().toString());

            }
        });


        /** 가입 버튼을 누르면 로그인 페이지로 이동한다. **/
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }


    /** 카운트 다운 메소드 **/
    public void countDownTimer() {

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



    /** userId 중복 확인을 위한 GET **/
    public void getCheck_id(String userId) {

        Log.i("info>> ", "getCheck_id 호출됨");
        apiService.check_id(userId).enqueue(new Callback<Check_id_data>() {

            @Override
            public void onResponse(Call<Check_id_data> call, Response<Check_id_data> response) {

                if(response.isSuccessful()){
                    Log.i("info", "통신 성공, code >> " + response.code());
                    Check_id_data data = response.body();
                    availale_id = data.getAvailable().booleanValue();
                    Log.i("info", "userId : " + data.getUserId());
                    Log.i("info", "available : " + data.getAvailable());


                    // 중복된 아이디 없다면
                    if(availale_id){
                        Toast.makeText(getApplication(),"사용가능한 아이디 입니다.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.i("info" , "else문" + availale_id);
                        Toast.makeText(getApplication(),"이미 등록된 아이디 입니다.",Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Check_id_data> call, Throwable t) {
                Log.e("error", "통신 실패(check_id)" + t.getMessage());
            }
        });


    }


    /** email 인증 요청 **/
    public void sendEmail(Sign_up_email_data email){
        apiService.sendnum(email).enqueue(new Callback<Sign_up_email_data>() {
            @Override
            public void onResponse(Call<Sign_up_email_data> call, Response<Sign_up_email_data> response) {

                Log.i("info", "code : " + response.code());
                if(response.isSuccessful()){
                    Log.i("info", "통신 성공(email), code : " + response.code());

                }
            }

            @Override
            public void onFailure(Call<Sign_up_email_data> call, Throwable t) {
                Log.e("error", "통신 실패(email)" + t.getMessage());
            }
        });
    }


    /** 인증번호 확인을 위한 POST **/
    public void authenticateNum(String email, String authenticateNumber){

        Log.i("info", "확인 버튼 클릭됨" );
        Log.i("info", "email : "+email + "\n authenticatieNumber : " + authenticateNumber);
        apiService.authenticate_num(email, authenticateNumber).enqueue(new Callback<Sign_up_email_num>() {
            @Override
            public void onResponse(Call<Sign_up_email_num> call, Response<Sign_up_email_num> response) {


                Log.i("info", "code : " + response.code());
                Log.i("info", "errorbody : " + response.errorBody());
                Log.i("info", "errorbody : " + new Gson().toJson(response.errorBody()));
                Log.i("info", "body : " + new Gson().toJson(response.body()));
                //Log.i("info", "message : " + response.message());


                if(response.isSuccessful()){
                    Log.i("info", "통신 성공(num), code : " + response.code());



                    /***
                     * Sign_up_email_num data = response.body();
                     *
                     *                     if(user_email_cf.getText().toString().equals(data.getAuthenticateNumber())){
                     *                         frameLayout3.setVisibility(View.GONE);
                     *                         Toast.makeText(getApplicationContext(),"인증 완료", Toast.LENGTH_SHORT).show();
                     *                     }
                     *                     else{
                     *                         Toast.makeText(getApplicationContext(),"입력하신 인증번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                     *                     }
                     */


                }

            }

            @Override
            public void onFailure(Call<Sign_up_email_num> call, Throwable t) {
                Log.e("error", "통신 실패(email)" + t.getMessage());
            }
        });
    }

}

