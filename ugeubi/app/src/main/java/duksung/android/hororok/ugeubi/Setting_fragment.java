package duksung.android.hororok.ugeubi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duksung.android.hororok.ugeubi.setting.Modify_pw;

public class Setting_fragment extends Fragment {

    LinearLayout mf_pw, logout_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_setting, container, false);

        mf_pw = rootView.findViewById(R.id.change_password_btn);
        logout_btn = rootView.findViewById(R.id.logout_btn);

        // 비밀번호 변경 버튼을 눌렀을 때
        mf_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Modify_pw.class);
                startActivity(intent);
            }
        });


        // 로그아웃 버튼을 눌렀을 때
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
                        startActivity(intent);
                    }
                }).show();

            }
        });
        return rootView;
    }
}
