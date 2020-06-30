package duksung.android.hororok.ugeubi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duksung.android.hororok.ugeubi.search.Search_Result;

public class Search_fragment extends Fragment {


    ImageButton search_btn;

    ToggleButton dur_none, dur_option1, dur_option2, dur_option3, dur_option4, dur_option5, dur_option6, dur_option7, dur_option8;

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