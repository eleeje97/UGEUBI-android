package duksung.android.hororok.ugeubi.ugeubi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.ugeubi.TakingHistoryDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    updateIsTaken(takingHistoryDTO.getTaking_history_id(), false);
                    //takingHistoryDTO.taking_history_is_taken = false;
                } else {
                    updateIsTaken(takingHistoryDTO.getTaking_history_id(), true);
                    //takingHistoryDTO.taking_history_is_taken = true;
                }

                getTakingHistory(takingHistoryDTO.getTaking_history_date());
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



    /** 먹은 약 체크 API **/
    public void updateIsTaken(int taking_history_id, boolean isTaken) {
        Log.e("ugeubi", "여기 들어옴!");
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");

        TakingHistoryDTO takingHistoryDTO = new TakingHistoryDTO(taking_history_id,isTaken);
        apiService.updateIsTaken(accessToken, takingHistoryDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.e("ugeubi", "code : " + response.code());

                if (response.isSuccessful()) {
                    Log.i("info", "통신성공(먹은약)");

                } else {
                    Log.e("ugeubi", "errorbody() : " +response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("error", "통신실패(먹은약)"+ t.getMessage() + t.getCause());
            }

        });
    }


    /** 복용약 기록 가져오는 API **/
    public void getTakingHistory(String date){

        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.getTakingHistory(accessToken,date).enqueue(new Callback <List<TakingHistoryDTO>>() {
            @Override
            public void onResponse(Call<List<TakingHistoryDTO>> call, Response<List<TakingHistoryDTO>> response) {

                Log.e("ugeubi", "code : " + response.code());

                if (response.isSuccessful()) {
                    Log.i("ugeubi", "통신성공");
                    List<TakingHistoryDTO> apiResponse = response.body();

                    Log.e("ugeubi", "size() : " +response.body().size());
                    Log.e("ugeubi", "body() : " +response.body());

                    clear();

                    for (TakingHistoryDTO takingHistoryDTO : apiResponse) {
                        Log.i("ugeubi", "이름: " + takingHistoryDTO.getMedicineName());
                        Log.i("ugeubi", "몇시: " + takingHistoryDTO.getTakingTime());
                        addItem(takingHistoryDTO);
                        notifyDataSetChanged();

                    }
                } else {
                    Log.e("ugeubi", "errorbody() : " +response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<TakingHistoryDTO>> call, Throwable t) {
                Log.e("ugeubi", "통신실패"+ t.getMessage() + t.getCause());
            }

        });
    }

}
