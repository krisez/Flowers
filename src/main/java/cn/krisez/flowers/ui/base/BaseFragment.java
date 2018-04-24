package cn.krisez.flowers.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.krisez.flowers.App;
import cn.krisez.flowers.R;

/**
 * Created by Krisez on 2018-03-23.
 */

public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_base_swipe);
        mProgressBar = view.findViewById(R.id.fragment_base_progress);
        mSwipeRefreshLayout.addView(addView());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initOperation(view);
    }

    protected abstract void initOperation(View view);

    protected abstract View addView();


    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void missProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void showToast(String s) {
        Toast.makeText(App.getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void cancelRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
