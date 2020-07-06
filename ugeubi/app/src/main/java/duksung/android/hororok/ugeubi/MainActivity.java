package duksung.android.hororok.ugeubi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import duksung.android.hororok.ugeubi.alarm.Alarm_fragment;
import duksung.android.hororok.ugeubi.medicine.Medicine_kit_fragment;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.search.Search_fragment;
import duksung.android.hororok.ugeubi.ugeubi.Ugeubi_fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Activity mainActivity;

    TabLayout tabs;
    private NonSwipeViewPager mViewPager;

    RetrofitInterface apiService;
    public final String PREFERENCE = "ugeubi.preference";

    TabLayout.Tab alarm_btn;
    TabLayout.Tab medicine_kit_btn;
    TabLayout.Tab main_btn;
    TabLayout.Tab search_btn;
    TabLayout.Tab settings_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = MainActivity.this;


        apiService = RetrofitClient.getService();

        mViewPager = findViewById(R.id.container);
        tabs = findViewById(R.id.tabs);

        // 메인 아이콘만 클릭 표시
        alarm_btn = tabs.newTab().setIcon(R.drawable.reminder);
        medicine_kit_btn = tabs.newTab().setIcon(R.drawable.medicine_kit_icon);
        main_btn = tabs.newTab().setIcon(R.drawable.main_icon_clicked);
        search_btn = tabs.newTab().setIcon(R.drawable.search_icon);
        settings_btn = tabs.newTab().setIcon(R.drawable.settings);

        // 탭 레이아웃에 각각의 탭 버튼 추가
        tabs.addTab(alarm_btn);
        tabs.addTab(medicine_kit_btn);
        tabs.addTab(main_btn);
        tabs.addTab(search_btn);
        tabs.addTab(settings_btn);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabs.getTabCount(), tabs);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                setIconUnclicked();

                switch (tab.getPosition()) {
                    case 0:
                        alarm_btn.setIcon(R.drawable.reminder_icon_clicked);
                        break;
                    case 1:
                        medicine_kit_btn.setIcon(R.drawable.medicine_kit_icon_clicked);
                        break;
                    case 2:
                        main_btn.setIcon(R.drawable.main_icon_clicked);
                        break;
                    case 3:
                        search_btn.setIcon(R.drawable.search_icon_clicked);
                        break;
                    case 4:
                        settings_btn.setIcon(R.drawable.settings_icon_clicked);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // 초기 탭 설정 (메인 탭)
        Intent intent = getIntent();
        int tabPosition = intent.getIntExtra("tabPosition", 2);
        mViewPager.setCurrentItem(tabPosition);


        try {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("IDService", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // token 등록
                            Log.d("IDService", token);
                            registerDeviceToken(token);
                        }
                    });


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void setIconUnclicked() {
        alarm_btn.setIcon(R.drawable.reminder);
        medicine_kit_btn.setIcon(R.drawable.medicine_kit_icon);
        main_btn.setIcon(R.drawable.main_icon);
        search_btn.setIcon(R.drawable.search_icon);
        settings_btn.setIcon(R.drawable.settings);
    }


    /** 디바이스 토큰 등록 api 호출 **/
    public void registerDeviceToken(String deviceToken) {
        // accessToken
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");

        apiService.registerDeviceToken(accessToken, deviceToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("fcm", "code: " + response.code());

                if (response.isSuccessful()) {
                    Log.e("fcm", "body: " + response.body());

                } else {
                    Log.e("fcm", "errorbody: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("fcm", "통신실패! " + t.getMessage());
            }
        });
    }



}
