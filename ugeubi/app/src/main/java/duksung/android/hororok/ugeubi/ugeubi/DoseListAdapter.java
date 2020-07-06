package duksung.android.hororok.ugeubi.ugeubi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.ugeubi.TakingHistoryDTO;

import static android.content.Context.MODE_PRIVATE;

public class DoseListAdapter extends RecyclerView.Adapter<DoseListAdapter.ViewHolder> {

    // 레트로핏
    RetrofitInterface apiService;


    public final String PREFERENCE = "ugeubi.preference";


    private Context context;
    private ArrayList<TakingHistoryDTO> doseDataList = new ArrayList<>();

    public DoseListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dose_item, parent, false);

        // 레트로핏
        apiService = RetrofitClient.getService();




        return new ViewHolder(view);
    }


    // 복용 여부
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TakingHistoryDTO takingHistoryDTO = doseDataList.get(position);

        holder.dose_time.setText(takingHistoryDTO.takingTime);
        holder.dose_name.setText(takingHistoryDTO.medicineName);
        if(takingHistoryDTO.taking_history_is_taken) {
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
            if (takingHistoryDTO.getTakingNumber() == 0) {
                holder.dose_num.setText("약을 복용하세요");
            } else {
                holder.dose_num.setText(takingHistoryDTO.takingNumber + "알 복용하세요");
            }

        }

        holder.dose_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이템 클릭 리스너
                if (takingHistoryDTO.taking_history_is_taken) {
                    takingHistoryDTO.taking_history_is_taken = false;
                } else {
                    takingHistoryDTO.taking_history_is_taken = true;
                }
                Collections.sort(doseDataList);
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

    public void addItem(TakingHistoryDTO takingHistoryDTO) {
        doseDataList.add(takingHistoryDTO);
        Collections.sort(doseDataList);
    }

    public void remove(int position){
        doseDataList.remove(position);
    }

    public void clear() {
        doseDataList.clear();
        notifyDataSetChanged();
    }

}
