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
        public TextView alarm_time;
        public ImageView dose_image;
        public TextView alarm_day;
        public TextView dday;
        public TextView dose_name;

        public ViewHolder(View itemView) {
            super(itemView);
            alarm_time = itemView.findViewById(R.id.alarm_time);
            dose_image = itemView.findViewById(R.id.dose_icon);
            alarm_day = itemView.findViewById(R.id.alarm_day);
            dday = itemView.findViewById(R.id.dday);
            dose_name = itemView.findViewById(R.id.alarm_content);
        }

        // set
        @SuppressLint("ResourceType")
        public void onBind(Alarm_data data){
            alarm_time.setText(data.getAlarm_time());
            alarm_day.setText(data.getAlarm_date());
            dday.setText(data.getDday());
            dose_name.setText(data.getDose_name());


        }


    }
}
