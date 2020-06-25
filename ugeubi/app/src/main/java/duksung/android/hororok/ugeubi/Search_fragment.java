package duksung.android.hororok.ugeubi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duksung.android.hororok.ugeubi.search.Search_Result;

public class Search_fragment extends Fragment {


    ImageButton search_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_search, container, false);

        search_btn = rootView.findViewById(R.id.search_btn);


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search_Result.class);

                startActivity(intent);
            }
        });

        return rootView;
    }
}