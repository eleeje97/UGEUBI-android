package duksung.android.hororok.ugeubi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
import duksung.android.hororok.ugeubi.retrofit.FInd.NewPwDTO;
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
    TextView new_pw, new_pw_cf,password_txt;
    Button login_btn, authorize_btn, authorize_btn4;
    FrameLayout frame3;

    // flag
    boolean checked_pw = false;
    boolean checked_email = false;
    boolean checked_id = false;


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
        password_txt = findViewById(R.id.pw_txt);

        // 카운트 다운
        timer_txt = findViewById(R.id.find_pw_timer);





        /** 아이디 입력 값 확인 **/
        userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(userid.getText().length() !=0){
                    checked_id = true;
                }
            }
        });


        /**  이메일 입력 값 확인 **/
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 길이가 0이 아니면서
                if(email.getText().length() != 0){

                    // 이메일 양식을 만족하는지
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
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



        /** 이메일 인증번호 요청 버튼을 클릭했을 때 **/
        authorize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().length() != 0){

                    // 올바른 이메일 양식인지 체크
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        // 인증번호 입력 박스 visible
                        if (authorize_btn.getText().equals("인증요청")) {
                            authorize_btn.setText("재요청");
                        }

                        // 재요청시
                        else if (authorize_btn.getText().equals("재요청")) {

                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                                countDownTimer = null;
                            }
                        }

                // 아이디와 이메일 모두 올바르게 입력되었다면
                if(checked_email && checked_id) {
                    sendEmail(userid.getText().toString(), email.getText().toString());
                }
                else if(!checked_id && checked_email){
                    Toast.makeText(getApplicationContext(), "입력된 아이디를 확인하세요!", Toast.LENGTH_SHORT).show();
                }
                else if(checked_id && !checked_email){
                    Toast.makeText(getApplicationContext(), "입력된 이메일을 확인하세요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "아이디와 이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                }

            }}}
        });


        // 임시 비밀 번호 확인 버튼
        authorize_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checked_email && checked_id) {
                    sendtemporaryPassword(userid.getText().toString(),
                            email.getText().toString(),
                            tempPw.getText().toString());
                }
            }
        });


        // 로그인 버튼을 눌렀을 때 =>> 새로운 비밀번호 입력 후 요청 작업
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNewPassword(userid.getText().toString() ,new_pw.getText().toString());
            }
        });

        /** 변경한 비밀번호가 일치하는지를 확인 **/
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
                countDownTimer.cancel();
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


                    //  카운트 다운 시작
                    countDownTimer();
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


    /** 새로운 비밀번호 등록 **/
    public void setNewPassword(String userId, String password){

        NewPwDTO newPwDTO = new NewPwDTO(userId, password);
        apiService.setNewPW(newPwDTO).enqueue(new Callback<NewPwDTO>() {
            @Override
            public void onResponse(Call<NewPwDTO> call, Response<NewPwDTO> response) {
                if(response.isSuccessful()){

                    Intent intent = new Intent(getApplicationContext(),Login.class);
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
