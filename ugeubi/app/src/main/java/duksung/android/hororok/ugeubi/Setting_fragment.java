package duksung.android.hororok.ugeubi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

            }
        });
        return rootView;
    }
}
