package cn.krisez.flowers.ui.ask;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.MyAskAdapter;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.Send2S;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.ui.base.ShowFragment;
import cn.krisez.flowers.widget.DividerDecoration;

public class MyAskActivity extends BaseActivity {

    private MyAskAdapter mAdapter;
    private List<Send2S> mSend2S;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_my_ask, null);
    }

    @Override
    protected void initSon() {
        showProgress();
        mSend2S = new ArrayList<>();
        mAdapter = new MyAskAdapter(this, mSend2S);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ask_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(mAdapter);

        initData();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyAskActivity.this, ShowFragment.class);
                intent.putExtra("ask",mSend2S.get(position));
                intent.putExtra("class","ask");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSend2S.clear();
                initData();
            }
        });
    }

    private void initData() {
        BmobQuery<Send2S> query = new BmobQuery<>();
        query.addWhereEqualTo("mUser", App.getUser().getObjectId());
        query.findObjects(new FindListener<Send2S>() {
            @Override
            public void done(List<Send2S> list, BmobException e) {
                if (e == null) {
                    mSend2S.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    missProgress();
                } else {
                    showError(e.getMessage());
                    missProgress();
                }

            }
        });
    }

    @Override
    protected String getTitles() {
        return "申请查询";
    }
}
