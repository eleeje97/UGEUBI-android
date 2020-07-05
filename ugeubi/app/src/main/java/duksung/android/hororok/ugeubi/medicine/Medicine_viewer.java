package duksung.android.hororok.ugeubi.medicine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineItemDTO;

public class Medicine_viewer extends LinearLayout {

        // 알약 아이콘을 랜덤으로 보여주기 위해 사용
        int[] pill_icons = {R.drawable.icon_pill1, R.drawable.icon_pill2,R.drawable.icon_pill3};
        Random random = new Random();

        // 이미지뷰, 텍스트뷰
        ImageView period, medicine_icon;
        TextView medicine_name, medicine_content, medicine_validTerm;


        public Medicine_viewer(Context context) {
                super(context);
                init(context);
        }

        public Medicine_viewer(Context context, @Nullable AttributeSet attrs) {
                super(context, attrs);
                init(context);
        }

        // 초기 설정
        public void init(Context context){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.medicine_item,this,true);

                // layout 연결
                period = findViewById(R.id.period);
                medicine_icon = findViewById(R.id.medicine_icon);
                medicine_name = findViewById(R.id.medicine_name);
                medicine_content = findViewById(R.id.medicine_content);
                medicine_validTerm = findViewById(R.id.medicine_validTerm);

        }

        public void setItem(MedicineItemDTO singerItem) {
                // 유통기한 임박 아이콘
                if(singerItem.isImminent()) {
                        period.setVisibility(VISIBLE);
                } else {
                        period.setVisibility(INVISIBLE);
                }


                // 약 아이콘 - 알약(물약,처방약 포함) / 가루약 / 연고
                if(singerItem.getMedicineType().equals("POWDER")) {
                        medicine_icon.setImageResource(R.drawable.icon_powder);
                } else if (singerItem.getMedicineType().equals("CREAM")) {
                        medicine_icon.setImageResource(R.drawable.icon_cream);
                } else {
                        int num = random.nextInt(3);
                        medicine_icon.setImageResource(pill_icons[num]);
                }


                // 약 이름
                medicine_name.setText(singerItem.getMedicineName());

                // 메모
                String memo = singerItem.getMemo();
                if (memo.length() > 10) {
                        memo = memo.substring(0,10) + "...";
                }
                medicine_content.setText(memo);


                // 유통기한
                medicine_validTerm.setText(singerItem.getMedicineValidTerm());

        }

}