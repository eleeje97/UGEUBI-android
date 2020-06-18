package duksung.android.hororok.ugeubi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;


public class Find_pw extends Activity {

    Button find_pw_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);


        find_pw_btn = findViewById(R.id.find_pw_btn);

        find_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find_pw_result.class);
                startActivity(intent);
            }
        });
    }
}
