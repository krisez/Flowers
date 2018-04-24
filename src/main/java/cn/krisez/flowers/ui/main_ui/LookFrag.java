package cn.krisez.flowers.ui.main_ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.LookPagerAdapter;
import cn.krisez.flowers.ui.main_ui.lookfrag.DealFragment;
import cn.krisez.flowers.ui.main_ui.lookfrag.DoDealFragment;
import cn.krisez.flowers.ui.main_ui.lookfrag.NoDealFragment;

/**
 * Created by Krisez on 2017/8/8.
 */

public class LookFrag extends Fragment {

    private DoDealFragment mAskFragment;
    private NoDealFragment mDynamicsFragment;
    private DealFragment mAlreadyFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("LookFrag", "onAttach:");
        mAskFragment = new DoDealFragment();
        mDynamicsFragment = new NoDealFragment();
        mAlreadyFragment = new DealFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_look, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        List<String> itemTitles = Arrays.asList("未处理", "正处理", "已完结");
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(itemTitles.get(i)));
        }
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.news_viewPager);
        final List<Fragment> list = new ArrayList<>();
        list.add(mDynamicsFragment);
        list.add(mAskFragment);
        list.add(mAlreadyFragment);

        viewPager.setAdapter(new LookPagerAdapter(getChildFragmentManager(), list, itemTitles));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //    private static int load = 0;//0,1,2  0无操作   1动态   2咨询
        switch (resultCode){
           /* case HelperActivity.ASK_CODE:
                mAskFragment.onActivityResult(requestCode,resultCode,data);
                Log.d("LookFrag", "onActivityResult:" + resultCode);
                break;
            case DynamicsActivity.DYNAMICS_CODE:
                mDynamicsFragment.onActivityResult(requestCode,resultCode,data);
                Log.d("LookFrag", "onActivityResult:" +resultCode);
                break;*/
        }
    }
}
