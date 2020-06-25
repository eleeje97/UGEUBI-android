package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Signup extends Activity {


    // 카운트 다운
    TextView timer_txt;
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    Button signup_btn;
    Button authorize_btn,authorize_btn2, authorize_btn_4;
    TextView user_email_cf, user_id;

    FrameLayout frameLayout3;

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
        user_id = findViewById(R.id.signup_user_id);

        frameLayout3 = findViewById(R.id.framelayout3);

        // 아이디 중복 확인
        authorize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 아이디가 중복되었을 경우
                // 중복 처리 안돼 짜증나
                if(user_id.getText().equals("ugeubi")) {
                    Toast.makeText(getApplication(), "아이디가 중복 되었습니다.", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplication(), "사용할 수 있는 아이디 입니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // 인증번호 요청
        authorize_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
            }
        });


        // 확인
        authorize_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout3.setVisibility(View.GONE);
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

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
}
