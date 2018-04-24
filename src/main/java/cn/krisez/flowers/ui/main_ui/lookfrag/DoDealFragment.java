package cn.krisez.flowers.ui.main_ui.lookfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.ApplyAdapter;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.ui.describe_ui.DescribeActivity;
import cn.krisez.flowers.widget.DividerDecoration;

/**
 * Created by Krisez on 2017/8/8.
 * 正处理
 */

public class DoDealFragment extends LookBaseFragment {

    private RecyclerView recyclerView;
    private List<Apply> mApplies;
    private ApplyAdapter adapter;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mApplies = new ArrayList<>();
        initList();

        adapter = new ApplyAdapter(getActivity(), mApplies);
        recyclerView = (RecyclerView) v.findViewById(R.id.case_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerDecoration(getActivity(), DividerDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DescribeActivity.class);
                intent.putExtra("apply", mApplies.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    protected void initList() {
        BmobQuery<Apply> query = new BmobQuery<>();
        query.addWhereEqualTo("deal",true);
        query.addWhereEqualTo("over",false);
        query.include("author");
        query.setLimit(20);
        query.findObjects(new FindListener<Apply>() {
            @Override
            public void done(List<Apply> list, BmobException e) {
                mApplies.removeAll(mApplies);
                mApplies.addAll(list);
                adapter.notifyDataSetChanged();
                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
