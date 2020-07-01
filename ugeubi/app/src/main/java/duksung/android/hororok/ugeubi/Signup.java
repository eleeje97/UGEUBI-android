package duksung.android.hororok.ugeubi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.retrofit.Check_id_data;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.Sign_up_DTO;
import duksung.android.hororok.ugeubi.retrofit.Sign_up_email_data;
import duksung.android.hororok.ugeubi.retrofit.authenticationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends Activity {

    /** Retrofit **/
    private RetrofitInterface apiService;


    // id 중복확인 및 이메일 인증 확인
    boolean availale_id;
    boolean authenticate_email;
    boolean checked_pw;
    boolean checked_name;

    // 이메일 정규식
    private String emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z]{2,})$";


    /** 카운트 다운 구현 **/
    TextView timer_txt, password_txt;
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)


    /** 회원가입 기능 구현 **/
    Button signup_btn;
    Button authorize_btn,authorize_btn2, authorize_btn_4;
    TextView user_email_cf, user_id, user_email2, user_password, user_password_confirm,user_name;
    FrameLayout frameLayout3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn = findViewById(R.id.btn_signin);
        timer_txt = findViewById(R.id.signup_timer_txt); // 타이머 시간 텍스트
        authorize_btn = findViewById(R.id.authorize_btn); // 아이디 중복확인 버튼
        authorize_btn2 = findViewById(R.id.authorize_btn2); // 인증번호 요청 버튼
        authorize_btn_4 = findViewById(R.id.authorize_btn4); // 확인 버튼
        user_email_cf = findViewById(R.id.user_email_cf); // 인증번호


        user_password = findViewById(R.id.user_password);
        user_password_confirm = findViewById(R.id.user_password_confirm);
        password_txt = findViewById(R.id.pw_txt);


        user_name = findViewById(R.id.user_name); // 이름
        user_id = findViewById(R.id.signup_user_id);  //userId
        user_email2 = findViewById(R.id.user_email2); // useremail
        frameLayout3 = findViewById(R.id.framelayout3);

        /** Retrofit **/
        // 서비스 객체 호출
        apiService = RetrofitClient.getService();


        /** 아이디 중복 확인 버튼을 눌렀을 때  **/
        authorize_btn.setOnClickListener(v -> {

            // id가 입력되었다면
            if(user_id.getText().length() != 0) {
                /** id가 입력 되었다면 api호출 **/
                getCheck_id(user_id.getText().toString());
            }
            else {
                Toast.makeText(getApplication(), "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
        });



        /** password 확인 **/
        user_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if(!user_password.getText().toString().equals(user_password_confirm.getText().toString())){
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

        /** user_name 입력 값 확인  **/
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

        /** id 입력 값 변경 **/
        user_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                availale_id = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }});



        /** 인증번호 요청 버튼을 눌렀을 때  **/
        authorize_btn2.setOnClickListener(v -> {

            if(user_email2.getText().length() != 0){

                // 올바른 이메일 양식인지 체크
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(user_email2.getText().toString()).matches()){
                    // 인증번호 입력 박스 visible
                    if(authorize_btn2.getText().equals("인증요청")) {
                        frameLayout3.setVisibility(View.VISIBLE);
                        authorize_btn2.setText("재요청");
                        authorize_btn_4.setClickable(true);
                    }

                    // 재요청시
                    else if(authorize_btn2.getText().equals("재요청")){
                        countDownTimer.cancel();
                        frameLayout3.setVisibility(View.VISIBLE);
                        authorize_btn_4.setClickable(true);
                    }

                    //  카운트 다운 시작
                    countDownTimer();

                    // email 전송
                    sendEmail(new Sign_up_email_data(user_email2.getText().toString()));

                }
                else{
                    Toast.makeText(getApplication(), "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }

            // 이메일이 입력되지 않았다면
            else{
                Toast.makeText(getApplication(), "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
        });

        /** 인증 확인 버튼 Click **/
        // authorize_btn_4 == 확인 버튼
        authorize_btn_4.setOnClickListener(v -> {
            authenticateNum(user_email_cf.getText().toString(), user_email2.getText().toString());
        });



        /** email 입력 값 변경 **/
        user_email2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                authenticate_email = false;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {}});


        /** 가입하기 버튼 Click **/
        signup_btn.setOnClickListener(v -> {

            // 아이디 중복확인 및 메일 인증이 모두 완료 되었다면 (true)
            // 중복확인 ok, 메일인증 ok, 비밀번호 일치 ok, 이름 입력 ok
            if(availale_id && authenticate_email && checked_pw && checked_name) {

                // 회원가입 api 호출
                signup(user_email2.getText().toString(), user_id.getText().toString()
                        , user_password.getText().toString(), user_name.getText().toString());

            }

            // 아이디 중복확인이 완료 되지 않은 경우
            else if(!availale_id && authenticate_email && checked_pw && checked_name){
                Toast.makeText(getApplicationContext(), "아이디 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
            }

            // 이메일 인증이 완료되지 않은 경우
            else if(availale_id && !authenticate_email && checked_pw && checked_name){
                Toast.makeText(getApplicationContext(), "이메일 인증요청을 해주세요!", Toast.LENGTH_SHORT).show();
            }

            // 비밀번호가 일치하지 않는 경우
            else if(availale_id && authenticate_email && !checked_pw && checked_name){
                Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
            }

            // 이름이 입력되지 않았을 경우
            else if(availale_id && authenticate_email && checked_pw && !checked_name){
                Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }

            // 아이디 중복확인 및 이메일 인증이 완료되지 않았을 경우
            else if(!availale_id && !authenticate_email && checked_pw && checked_name) {

                    Toast.makeText(getApplicationContext(), "중복확인 및 인증요청을 확인해주세요!", Toast.LENGTH_SHORT).show();
                }

            //
            else{
                Toast.makeText(getApplicationContext(), "입력란을 확인해주세요!", Toast.LENGTH_SHORT).show();
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
                authorize_btn_4.setClickable(false);

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


    /** email 인증 요청 API**/
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


    /** 인증번호 확인 API **/
    public void authenticateNum(String authenticateNumber, String email){

        //authenticationDTO 객체 생성
        authenticationDTO authenticationDTO = new authenticationDTO(authenticateNumber, email);

        apiService.authenticate_num(authenticationDTO).enqueue(new Callback<authenticationDTO>() {

            @Override
            public void onResponse(Call<authenticationDTO> call, Response<authenticationDTO> response) {

                if(response.isSuccessful()){
//                    Log.i("info", "통신 성공(num), code : " + response.code());
//                    Log.i("info", "responsebody : " + response.body().getAuthenticateNumber());

                    if(response.code() == 200){
                    frameLayout3.setVisibility(View.GONE);
                    authenticate_email = true;
                    Toast.makeText(getApplicationContext(),"이메일 인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"입력하신 인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<authenticationDTO> call, Throwable t) {
                Log.e("error", "통신 실패(authentication)" + t.getMessage());
            }
        });
    }




    /** 회원 가입 API 호출  **/
    public void signup(String email, String id, String password, String name){
        Sign_up_DTO sign_up_dto = new Sign_up_DTO(email, id, password, name);

        apiService.signup(sign_up_dto).enqueue(new Callback<Sign_up_DTO>() {

            // 통신 성공 => 로그인 페이지로 이동
            @Override
            public void onResponse(Call<Sign_up_DTO> call, Response<Sign_up_DTO> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Sign_up_DTO> call, Throwable t) {
                Log.e("error", "통신 실패(sign_up)" + t.getMessage());

            }
        });
    }
}

