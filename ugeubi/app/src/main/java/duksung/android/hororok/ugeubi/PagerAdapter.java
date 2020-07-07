package duksung.android.hororok.ugeubi;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import duksung.android.hororok.ugeubi.alarm.Alarm_fragment;
import duksung.android.hororok.ugeubi.medicine.Medicine_kit_fragment;
import duksung.android.hororok.ugeubi.search.Search_fragment;
import duksung.android.hororok.ugeubi.ugeubi.Ugeubi_fragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    TabLayout tabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, TabLayout tabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabs = tabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Alarm_fragment alarm_fragment = new Alarm_fragment();
                return alarm_fragment;
            case 1:
                Medicine_kit_fragment medicine_kit_fragment = new Medicine_kit_fragment();
                return medicine_kit_fragment;
            case 2:
                Ugeubi_fragment ugeubi_fragment = new Ugeubi_fragment();
                return ugeubi_fragment;
            case 3:
                Search_fragment search_fragment = new Search_fragment();
                return search_fragment;
            case 4:
                Setting_fragment setting_fragment = new Setting_fragment();
                return setting_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
