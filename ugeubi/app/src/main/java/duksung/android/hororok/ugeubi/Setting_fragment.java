package duksung.android.hororok.ugeubi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duksung.android.hororok.ugeubi.setting.DeveloperInfo;
import duksung.android.hororok.ugeubi.setting.Modify_pw;
import duksung.android.hororok.ugeubi.setting.OpenSourceLicense;

import static android.content.Context.MODE_PRIVATE;

public class Setting_fragment extends Fragment {

    LinearLayout mf_pw, logout_btn, developer_info,opensource_license;

    TextView userName_id, user_Email;

    public final String PREFERENCE = "ugeubi.preference";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_setting, container, false);


        // 레이아웃 연결
        mf_pw = rootView.findViewById(R.id.change_password_btn);
        logout_btn = rootView.findViewById(R.id.logout_btn);
        userName_id = rootView.findViewById(R.id.username_id);
        user_Email = rootView.findViewById(R.id.useremail);
        developer_info = rootView.findViewById(R.id.developer_info);
        opensource_license = rootView.findViewById(R.id.opensource_license);


        // 회원 정보 가져오기
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String userName = pref.getString("userName","");
        String userId = pref.getString("userId","");
        String userEmail = pref.getString("userEmail", "");

        /** 회원 정보 보여주기 **/
        userName_id.setText(userName + " (" + userId + ")");
        user_Email.setText(userEmail);



        /** 비밀번호 변경 버튼 **/
        mf_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Modify_pw.class);
                startActivity(intent);
            }
        });


        /** 로그아웃 **/
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 다이얼로그 창 생성
                final AlertDialog.Builder logoutDialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);
                logoutDialog.setMessage("로그아웃 하시겠습니까?").setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutDialog.setCancelable(true);
                    }
                }).setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), Login.class);
                        Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("accessToken", "");
                        editor.putString("userId", "");
                        editor.putString("userPassword", "");
                        editor.putString("userName", "");
                        editor.putString("userEmail", "");
                        editor.apply();


                        startActivity(intent);

                        getActivity().finish();
                    }
                }).show();

            }
        });


        /** 개발자 정보 **/
        developer_info.setOnClickListener(v -> {
            //Intent developerIntent = new Intent(getContext(), DeveloperInfo.class);
            //startActivity(developerIntent);
            // 다이얼로그 바디
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog);


            String strChange = "<font color=\"#45ba8e\"><b> > </b></font>";
            String strServer = "<b> Server </b> <br>" +
                    "권수연  kwonsye56@gmail.com <br>" +
                    "정인정 \n" + "<br> <br>" ;
            String strAndroid = "<b> Android </b> <br>" +
                     "송현주  songthdo427@gmail.com <br>" +
                     "이다은  eleeje97@gmail.com\n" +
                    "<br> <br>" ;

            String strDesign = " <b> Design </b> <br>" +
                    "장지은 jieunregina@naver.com <br>" +
                    "정다인 dain515@naver.com\n";



            // 메세지
            alert_confirm.setMessage(Html.fromHtml(strChange + strServer
                                                    + strChange + strAndroid + strChange + strDesign));
            // 확인 버튼 리스너
            alert_confirm.setPositiveButton("확인", null);

            // 다이얼로그 생성
            AlertDialog alert = alert_confirm.create();

            alert.setOnShowListener(dialog -> {


                // 버튼 색 메인으로 바꾸기
                 alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#45ba8e"));
            });

            // 아이콘
            alert.setIcon(R.drawable.logo_splash);
            // 다이얼로그 타이틀
            alert.setTitle("개발자 정보");
            // 다이얼로그 보기
            alert.show();

        });


        /** 오픈 소스 라이센스 **/
        opensource_license.setOnClickListener(v -> {
            Intent openSourceIntent = new Intent(getContext(), OpenSourceLicense.class);
            startActivity(openSourceIntent);
        });
        return rootView;


    }


}
