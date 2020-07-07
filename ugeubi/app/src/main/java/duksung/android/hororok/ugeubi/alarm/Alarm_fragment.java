package duksung.android.hororok.ugeubi.alarm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.alarm.NotificationDTO;
import duksung.android.hororok.ugeubi.retrofit.alarm.NotificationListDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;

public class Alarm_fragment extends Fragment {

    // 리사이클러 뷰와 어댑터
    RecyclerView recyclerView;
    Alarm_adapter alarm_adapter;

    // default 알람
    LinearLayout default_alarm;

    // retrofit
    RetrofitInterface apiService;

    public final String PREFERENCE = "ugeubi.preference";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_alarm, container, false);

        // default_alarm
        default_alarm = rootView.findViewById(R.id.alarm_default);

        // recycle
        recyclerView = rootView.findViewById(R.id.alarmlist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        alarm_adapter = new Alarm_adapter();


        recyclerView.setAdapter(alarm_adapter);






        apiService = RetrofitClient.getService();
        getNotifications();




        return rootView;
    }



    /** 약 목록 조회 API 호출 **/
    public void getNotifications(){

        // token
        SharedPreferences pref = getActivity().getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.getNotifications(accessToken).enqueue(new Callback<NotificationListDTO>() {
            @Override
            public void onResponse(Call<NotificationListDTO> call, Response<NotificationListDTO> response) {
                Log.i("Notification", "code: " + response.code());
                if(response.isSuccessful()) {
                    Log.i("Notification", "통신성공");
                    ArrayList<NotificationDTO> apiResponse = response.body().getNotificationsList();

                    Log.e("Notification", "size: " + apiResponse.size());
                    for (NotificationDTO notificationDTO : apiResponse) {
                        Log.e("Notification", "medicine_name: " + notificationDTO.getMedicineName());
                        Log.e("Notification", "notificatioin_date: " + notificationDTO.getNotificationDate());
                        Log.e("Notification", "notification_type: " + notificationDTO.getNotificationType());

                        // 알림날짜
                        String notification_date = notificationDTO.getNotificationDate();
                        String[] str = notification_date.split("-");
                        notification_date = str[0] + "년 " + str[1] + "월 " + str[2] + "일";

                        // 약 복용시간, 알림 내용
                        String notification_content;
                        String notification_time;
                        if (notificationDTO.getNotificationType().equals("TAKING_TIME")) {
                            notification_time = notificationDTO.getNotificationTime();
                            notification_time = notification_time.substring(0,5);
                            notification_content = notificationDTO.getMedicineName() + " 복용시간입니다.";
                        } else {
                            notification_time = "";
                            notification_content = notificationDTO.getMedicineName() + "의 유통기한을 확인해주세요!";
                        }

                        // 알림 경과날짜
                        String passedDay = "";
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date today = new Date();
                            Date notificationDate = format.parse(notificationDTO.getNotificationDate());

                            long calDate = today.getTime() - notificationDate.getTime();
                            long calDateDays = calDate / ( 24*60*60*1000);
                            calDateDays = Math.abs(calDateDays);

                            if(calDateDays == 0) {
                                passedDay = "오늘";
                            } else {
                                passedDay = calDateDays + "일 전";
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        alarm_adapter.addItem(new Alarm_data(notification_date, notification_time, notification_content, passedDay));
                        alarm_adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(alarm_adapter);
                    }


                    if(apiResponse.size() > 0){
                        default_alarm.setVisibility(View.GONE);
                    }
                    else{
                        default_alarm.setVisibility(View.VISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<NotificationListDTO> call, Throwable t) {
                Log.e("error", "통신실패" + t.getCause());
            }
        });

    }


}
