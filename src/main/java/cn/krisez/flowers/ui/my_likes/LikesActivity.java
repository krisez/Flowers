package cn.krisez.flowers.ui.my_likes;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.ApplyAdapter;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.presenter.sos_presenter.ISosPresenter;
import cn.krisez.flowers.presenter.sos_presenter.SosPresenter;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.ui.describe_ui.DescribeActivity;
import cn.krisez.flowers.ui.my_helper.ISosView;
import cn.krisez.flowers.widget.DividerDecoration;

/**
 * Created by Krisez on 2018-03-23.
 */

public class LikesActivity extends BaseActivity{

    private ApplyAdapter mAdapter;
    private List<Apply> mApplyList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_sos,null);
    }

    @Override
    protected void initSon() {
        showProgress();
        mApplyList = new ArrayList<>();
        getList();
        mAdapter = new ApplyAdapter(this, mApplyList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sos_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(LikesActivity.this, DescribeActivity.class);
                intent.putExtra("apply", mApplyList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mSwipeRefreshLayout = findViewById(R.id.sos_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });
    }

    private void getList(){
        BmobQuery<Apply> query = new BmobQuery<>();
        query.include("author");
        query.addWhereRelatedTo("likePost",new BmobPointer(App.getUser()));
        query.findObjects(new FindListener<Apply>() {
            @Override
            public void done(List<Apply> list, BmobException e) {
                if(e==null){
                    setList(list);
                }else showError(e.getMessage());
            }
        });
    }

    @Override
    protected String getTitles() {
        return "我的收藏";
    }

    private void setList(List<Apply> applies) {
        mApplyList.removeAll(mApplyList);
        mApplyList.addAll(applies);
        mAdapter.notifyDataSetChanged();
        cancel();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void cancel() {
        missProgress();
    }
}
