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
    private View.OnClickListener onClickListener;

    public TakingTimeListAdapter(Context context, View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
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
        holder.taking_time_background.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return takingTimeDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taking_time;
        public LinearLayout taking_time_background;

        public ViewHolder(View itemView) {
            super(itemView);
            taking_time = itemView.findViewById(R.id.taking_time);
            taking_time_background = itemView.findViewById(R.id.taking_time_background);
        }
    }

    public void addItem(String time) {
        TakingTimeData takingTimeData;
        takingTimeData = new TakingTimeData();
        takingTimeData.time = time;

        takingTimeDataList.add(takingTimeData);
        notifyItemChanged(takingTimeDataList.size()-1);
    }

    public void remove(int position){
        takingTimeDataList.remove(position);
    }

    public void clear() {
        takingTimeDataList.clear();
    }

}

