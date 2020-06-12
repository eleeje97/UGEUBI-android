package duksung.android.hororok.ugeubi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Ugeubi_fragment extends Fragment {
    private RecyclerView dose_list;
    private DoseListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_ugeubi, container, false);

        dose_list = rootView.findViewById(R.id.dose_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dose_list.setLayoutManager(layoutManager);


        adapter = new DoseListAdapter(getContext());
        dose_list.setAdapter(adapter);


        adapter.addItem("7:00", "비타민 D", "2알 복용하세요", false);
        adapter.addItem("8:00", "타이레놀", "2알 복용하세요", false);

        adapter.addItem("7:00", "비타민 D", "2알 복용하세요", true);
        adapter.addItem("8:00", "타이레놀", "2알 복용하세요", true);


        return rootView;
    }


}
