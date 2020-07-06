package duksung.android.hororok.ugeubi.alarm;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import duksung.android.hororok.ugeubi.R;

public class Alarm_adapter extends RecyclerView.Adapter<Alarm_adapter.ViewHolder> {



    // adapter에 들어갈 list 생성
    private ArrayList<Alarm_data> alarmList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // item list를 inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // item을 하나한 보여주는 함수
        holder.onBind(alarmList.get(position));
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    // add
    public void addItem(Alarm_data data){
        alarmList.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_time;
        public ImageView dose_image;
        public TextView notification_date;
        public TextView passedDay;
        public TextView notification_content;

        public ViewHolder(View itemView) {
            super(itemView);
            notification_time = itemView.findViewById(R.id.alarm_time);
            dose_image = itemView.findViewById(R.id.dose_icon);
            notification_date = itemView.findViewById(R.id.alarm_day);
            passedDay = itemView.findViewById(R.id.dday);
            notification_content = itemView.findViewById(R.id.alarm_content);
        }

        // set
        public void onBind(Alarm_data data){
            if (data.getNotification_time().equals("")) {
                notification_time.setVisibility(View.GONE);
            } else {
                notification_time.setText(data.getNotification_time());
            }
            notification_date.setText(data.getNotification_date());
            passedDay.setText(data.getPassedDay());
            notification_content.setText(data.getMedicine_content());
        }


    }
}
