package cn.krisez.flowers.ui.login_ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.krisez.flowers.R;
import cn.krisez.flowers.ui.base.BaseActivity;

/**
 * Created by Krisez on 2018-01-27.
 */

public class LoginActivity extends BaseActivity{

    public static final int LOGIN_RESULT = 101;

    private ProgressBar mProgressBar;

    @Override
    protected View setView() {
        return View.inflate(this,R.layout.activity_login,null);
    }

    @Override
    protected void initSon() {
        initView();
    }

    @Override
    protected String getTitles() {
        return "登录";
    }

    private void initView() {
        mProgressBar = findViewById(R.id.login_pro);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.login_tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.login_viewpager);

        List<String> itemTitles = Arrays.asList(
                getResources().getString(R.string.sign_in),
                getResources().getString(R.string.sign_up));
        for (int i = 0; i < 2; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(itemTitles.get(i)));
        }
        tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#67b5f4"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new LoginFrag());
        fragmentList.add(new RegisFrag());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, itemTitles));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void dismissProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void success(String account){
        Intent intent = new Intent();
        intent.putExtra("account",account);
        setResult(LOGIN_RESULT,intent);
        finish();
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;
        private List<String> mItemTitle;
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> itemTitle) {
            super(fm);
            this.mFragmentList = fragmentList;
            this.mItemTitle = itemTitle;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mItemTitle.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mItemTitle.get(position);
        }
    }
}
