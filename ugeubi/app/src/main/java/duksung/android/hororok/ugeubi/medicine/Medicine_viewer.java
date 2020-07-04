package duksung.android.hororok.ugeubi.medicine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import duksung.android.hororok.ugeubi.R;

public class Medicine_viewer extends LinearLayout {

        // 알약 아이콘을 랜덤으로 보여주기 위해 사용
        int[] pill_icons = {R.drawable.medicine_icon_pill1, R.drawable.pill_icon2,R.drawable.icon_pill3};

        // 이미지뷰, 텍스트뷰
        ImageView period, medicine_icon;
        TextView medicine_name, medicine_content;


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

        }

public void setItem(MedicineDTO singerItem){

        medicine_icon.setImageResource(singerItem.getImage());
        medicine_name.setText(singerItem.getName());
        medicine_content.setText(singerItem.getContent());

        }
        }