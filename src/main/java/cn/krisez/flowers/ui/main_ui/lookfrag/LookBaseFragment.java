package cn.krisez.flowers.ui.main_ui.lookfrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.krisez.flowers.R;

/**
 * Created by Krisez on 2018-03-18.
 */

public abstract class LookBaseFragment extends Fragment {
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_look_wcl, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initList();
            }
        });
        return view;
    }
    protected abstract void initList();
}
