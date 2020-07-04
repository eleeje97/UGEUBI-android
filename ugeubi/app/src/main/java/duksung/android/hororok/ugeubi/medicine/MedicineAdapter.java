package duksung.android.hororok.ugeubi.medicine;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineDTO;

// 어댑터
public class MedicineAdapter extends BaseAdapter {
    ArrayList<MedicineDTO> items = new ArrayList<MedicineDTO>();
    @Override
    public int getCount() {
        return items.size();
    }


    // 아이템 등록
    public void addItem(MedicineDTO data){
        items.add(data);
    }

    @Override
    public MedicineDTO getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 이부분 수정함
        Medicine_viewer medicineViewer = new Medicine_viewer(view.getContext());
        medicineViewer.setItem(items.get(i));
        return medicineViewer;
    }
}
