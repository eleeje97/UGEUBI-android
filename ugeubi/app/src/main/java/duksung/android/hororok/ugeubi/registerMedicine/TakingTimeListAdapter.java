package duksung.android.hororok.ugeubi.registerMedicine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import duksung.android.hororok.ugeubi.R;

public class TakingTimeListAdapter extends RecyclerView.Adapter<TakingTimeListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<TakingTimeData> takingTimeDataList = new ArrayList<>();

    public TakingTimeListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.taking_time_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TakingTimeData takingTimeData = takingTimeDataList.get(position);

        holder.taking_time.setText(takingTimeData.time);
    }

    @Override
    public int getItemCount() {
        return takingTimeDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taking_time;

        public ViewHolder(View itemView) {
            super(itemView);
            taking_time = itemView.findViewById(R.id.taking_time);
        }
    }

    public void addItem(String time) {
        TakingTimeData takingTimeData;
        takingTimeData = new TakingTimeData();
        takingTimeData.time = time;

        takingTimeDataList.add(takingTimeData);
        notifyItemChanged(takingTimeDataList.size()-1);
    }

    public void changeTime(int position, int hour, int minute) {
        takingTimeDataList.get(position).time = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        notifyDataSetChanged();
    }

    public void remove(int position){
        takingTimeDataList.remove(position);
    }

    public void clear() {
        takingTimeDataList.clear();
    }

}

