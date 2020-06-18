package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Find_id_result extends Activity {



    TextView coment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_result);


        /*
        int userid_length = 4;
        // 사용자의 아이디이름을 받아와 색상 변경
        coment = findViewById(R.id.coment_id);
        String user_id_txt = coment.getText().toString();

        SpannableString spannableString = new SpannableString(user_id_txt);

        int start_txt = user_id_txt.indexOf(' ');
        int end_txt = start_txt;

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#45ba8e")),, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        coment.setText(spannableString);
        */
    }
}
