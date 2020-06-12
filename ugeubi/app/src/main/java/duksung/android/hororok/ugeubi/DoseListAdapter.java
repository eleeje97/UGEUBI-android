package duksung.android.hororok.ugeubi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoseListAdapter extends RecyclerView.Adapter<DoseListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DoseData> doseDataList = new ArrayList<>();

    public DoseListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dose_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DoseData doseData = doseDataList.get(position);

        holder.dose_time.setText(doseData.time);
        holder.dose_name.setText(doseData.dose_name);
        if(doseData.isTaken) {
            holder.dose_background.setBackgroundResource(R.drawable.bg_ugeubi_dose_taken);
            holder.dose_image.setImageResource(R.drawable.ugeubi_dose_taken);
            holder.dose_time.setTextColor(Color.parseColor("#cbcbcb"));
            holder.dose_name.setTextColor(Color.parseColor("#2f2d33"));
            holder.dose_num.setText("복용했습니다");
        } else {
            holder.dose_background.setBackgroundResource(R.drawable.bg_ugeubi_dose);
            holder.dose_image.setImageResource(R.drawable.ugeubi_dose);
            holder.dose_time.setTextColor(Color.parseColor("#ffffff"));
            holder.dose_name.setTextColor(Color.parseColor("#ffffff"));
            holder.dose_num.setText(doseData.dose_num);
        }

        holder.dose_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이템 클릭 리스너
                if (doseData.isTaken) {
                    doseData.isTaken = false;
                } else {
                    doseData.isTaken = true;
                }
                notifyItemChanged(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return doseDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dose_time;
        public ImageView dose_image;
        public TextView dose_name;
        public TextView dose_num;
        public LinearLayout dose_background;

        public ViewHolder(View itemView) {
            super(itemView);
            dose_time = itemView.findViewById(R.id.dose_time);
            dose_image = itemView.findViewById(R.id.dose_image);
            dose_name = itemView.findViewById(R.id.dose_name);
            dose_num = itemView.findViewById(R.id.dose_num);
            dose_background = itemView.findViewById(R.id.dose_background);
        }
    }

    public void addItem(String time, String dose_name, String dose_num, boolean isTaken) {
        DoseData doseData;
        doseData = new DoseData();
        doseData.time = time;
        doseData.dose_name = dose_name;
        doseData.dose_num = dose_num;
        doseData.isTaken = isTaken;

        doseDataList.add(doseData);
    }

    public void remove(int position){
        doseDataList.remove(position);
    }

}
