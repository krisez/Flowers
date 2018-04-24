package cn.krisez.flowers.ui.my_helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.ApplyAdapter;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.presenter.sos_presenter.ISosPresenter;
import cn.krisez.flowers.presenter.sos_presenter.SosPresenter;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.ui.describe_ui.DescribeActivity;
import cn.krisez.flowers.widget.DividerDecoration;

public class SosActivity extends BaseActivity implements ISosView {

    private ApplyAdapter mAdapter;
    private List<Apply> mApplyList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ISosPresenter mPresenter;

    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_sos, null);
    }

    @Override
    protected void initSon() {
        showProgress();
        mPresenter = new SosPresenter(this);
        mApplyList = new ArrayList<>();
        mPresenter.getList();
        mAdapter = new ApplyAdapter(this, mApplyList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sos_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SosActivity.this, DescribeActivity.class);
                intent.putExtra("apply", mApplyList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                if (mApplyList.get(position).isOver()) {
                    new AlertDialog.Builder(SosActivity.this)
                            .setTitle("请做出您的决定")
                            .setItems(new String[]{"接受该建议", "提交司法局"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String s = "";
                                    if (i == 0) {
                                        s = "援助建议";
                                    } else {
                                        s = "司法局";
                                    }
                                    mPresenter.goWhere(mApplyList.get(position).getObjectId(), s);
                                    showProgress();
                                    mApplyList.get(position).setWhere(s);
                                    mAdapter.notifyItemChanged(position);
                                }
                            })
                            .setNegativeButton("取消", null).show();
                } else showError("还未完结");
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.sos_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getList();
            }
        });
    }

    @Override
    protected String getTitles() {
        return "我的求助";
    }

    @Override
    public String getAuthorId() {
        return App.getUser().getObjectId();
    }

    @Override
    public void setList(List<Apply> applies) {
        mApplyList.removeAll(mApplyList);
        mApplyList.addAll(applies);
        mAdapter.notifyDataSetChanged();
        cancel();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void cancel() {
        missProgress();
    }
}
