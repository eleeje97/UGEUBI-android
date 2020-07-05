package duksung.android.hororok.ugeubi.medicine;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineItemDTO;

// 어댑터
public class MedicineAdapter extends BaseAdapter {
    ArrayList<MedicineItemDTO> items = new ArrayList<MedicineItemDTO>();
    @Override
    public int getCount() {
        return items.size();
    }


    // 아이템 등록
    public void addItem(MedicineItemDTO data){
        items.add(data);
    }

    @Override
    public MedicineItemDTO getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 이부분 수정함
        Medicine_viewer medicineViewer = new Medicine_viewer(viewGroup.getContext());
        medicineViewer.setItem(items.get(i));
        return medicineViewer;
    }
}
