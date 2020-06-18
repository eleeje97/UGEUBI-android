package duksung.android.hororok.ugeubi.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.medicine.Medicine_data;

public class    Alarm_fragment extends Fragment {

    // 리사이클러 뷰와 어댑터
    RecyclerView recyclerView;
    Alarm_adapter alarm_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_alarm, container, false);

        recyclerView = rootView.findViewById(R.id.alarmlist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        alarm_adapter = new Alarm_adapter();
        recyclerView.setAdapter(alarm_adapter);

        alarm_adapter.addItem(new Alarm_data("2020년06월18일","18:00AM","타이레놀 복용시간 입니다.","당일"));

        alarm_adapter.addItem(new Alarm_data("2020년06월16일","19:00AM","비타민D 복용시간 입니다.","2일전"));


        return rootView;
    }




}
