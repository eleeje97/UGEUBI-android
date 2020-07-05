package duksung.android.hororok.ugeubi.alarm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.alarm.NotificationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Alarm_fragment extends Fragment {

    // 리사이클러 뷰와 어댑터
    RecyclerView recyclerView;
    Alarm_adapter alarm_adapter;

    // retrofit
    RetrofitInterface apiService;

    public final String PREFERENCE = "ugeubi.preference";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_alarm, container, false);

        recyclerView = rootView.findViewById(R.id.alarmlist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        alarm_adapter = new Alarm_adapter();
        recyclerView.setAdapter(alarm_adapter);


        apiService = RetrofitClient.getService();
        getNotifications();

        // Test
        alarm_adapter.addItem(new Alarm_data("2020년06월18일","18:00AM","타이레놀 복용시간 입니다.","당일"));
        alarm_adapter.addItem(new Alarm_data("2020년06월16일","19:00AM","비타민D 복용시간 입니다.","2일전"));


        return rootView;
    }



    /** 약 목록 조회 API 호출 **/
    public void getNotifications(){

        // token
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.getNotifications(accessToken).enqueue(new Callback<List<NotificationDTO>>() {
            @Override
            public void onResponse(Call<List<NotificationDTO>> call, Response<List<NotificationDTO>> response) {
                Log.i("Notification", "code: " + response.code());
                if(response.isSuccessful()) {
                    Log.i("Notification", "통신성공");
                    List<NotificationDTO> apiResponse = response.body();

                    for (NotificationDTO notificationDTO : apiResponse) {
                        Log.e("Notification", "medicine_name: " + notificationDTO.medicine_name);
                        Log.e("Notification", "notificatioin_date: " + notificationDTO.notification_date);
                        Log.e("Notification", "notification_type: " + notificationDTO.notification_type);

                        if (notificationDTO.notification_type.equals("taking_time")) {

                        } else {

                        }

                        // 알람날짜, 약 시간 - Format 설정
                        String notification_date = notificationDTO.notification_date;
                        String notification_time = "time"; //notificationDTO.notification_time;
                        String notification_content = notificationDTO.medicine_name;
                        String passedDay = "passedDay";

                        //alarm_adapter.addItem(new Alarm_data("2020년06월18일","18:00AM","타이레놀 복용시간 입니다.","당일"));
                        alarm_adapter.addItem(new Alarm_data(notification_date, notification_time, notification_content, passedDay));
                        alarm_adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(alarm_adapter);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<NotificationDTO>> call, Throwable t) {
                Log.e("error", "통신실패" + t.getCause());
            }
        });

    }


}
