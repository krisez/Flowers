package cn.krisez.flowers.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Krisez on 2017/8/8.
 */

public class LookPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mItemTitle;
    private int number;

    public LookPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public LookPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList, List<String> mItemTitle) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mItemTitle = mItemTitle;
        this.number = mItemTitle.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return number;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItemTitle.get(position);

    }
}
