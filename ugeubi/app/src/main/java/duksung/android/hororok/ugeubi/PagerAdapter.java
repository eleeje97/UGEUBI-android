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
//                setIconUnclicked();
//                Log.e("TabPosition", tabs.getSelectedTabPosition()+"");
//                tabs.getTabAt(tabs.getSelectedTabPosition()).setIcon(R.drawable.reminder_icon_clicked);
                return alarm_fragment;
            case 1:
                Medicine_kit_fragment medicine_kit_fragment = new Medicine_kit_fragment();
//                setIconUnclicked();
//                Log.e("TabPosition", tabs.getSelectedTabPosition()+"");
//                tabs.getTabAt(tabs.getSelectedTabPosition()).setIcon(R.drawable.medicine_kit_icon_clicked);
                return medicine_kit_fragment;
            case 2:
                Ugeubi_fragment ugeubi_fragment = new Ugeubi_fragment();
//                setIconUnclicked();
//                Log.e("TabPosition", tabs.getSelectedTabPosition()+"");
//                tabs.getTabAt(tabs.getSelectedTabPosition()).setIcon(R.drawable.main_icon_clicked);
                return ugeubi_fragment;
            case 3:
                Search_fragment search_fragment = new Search_fragment();
//                setIconUnclicked();
//                Log.e("TabPosition", tabs.getSelectedTabPosition()+"");
//                tabs.getTabAt(tabs.getSelectedTabPosition()).setIcon(R.drawable.search_icon_clicked);
                return search_fragment;
            case 4:
                Setting_fragment setting_fragment = new Setting_fragment();
//                setIconUnclicked();
//                Log.e("TabPosition", tabs.getSelectedTabPosition()+"");
//                tabs.getTabAt(tabs.getSelectedTabPosition()).setIcon(R.drawable.settings_icon_clicked);
                return setting_fragment;
            default:
                return null;
        }
    }


    public void setIconUnclicked() {
        tabs.getTabAt(0).setIcon(R.drawable.reminder);
        tabs.getTabAt(1).setIcon(R.drawable.medicine_kit_icon);
        tabs.getTabAt(2).setIcon(R.drawable.main_icon);
        tabs.getTabAt(3).setIcon(R.drawable.search_icon);
        tabs.getTabAt(4).setIcon(R.drawable.settings);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
