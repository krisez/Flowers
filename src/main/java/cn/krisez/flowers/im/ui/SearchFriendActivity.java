package cn.krisez.flowers.im.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.adapter.SearchAdapter;
import cn.krisez.flowers.im.presenter.IPresenter;
import cn.krisez.flowers.im.presenter.Presenter;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.widget.DividerDecoration;

/**
 * Created by Krisez on 2018-03-24.
 */

public class SearchFriendActivity extends BaseActivity implements ISearchView{

    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private SearchAdapter mAdapter;
    private List<User> mUserList;

    private IPresenter mPresenter;

    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_search_friend,null);
    }

    @Override
    protected void initSon() {
        mPresenter = new Presenter(this);
        mUserList = new ArrayList<>();
        mAdapter = new SearchAdapter(this,mUserList);
        mEditText = findViewById(R.id.et_find_name);
        mRefreshLayout = findViewById(R.id.search_friend_swipe);
        mRecyclerView = findViewById(R.id.search_friend_rc);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(this,DividerDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchFriendActivity.this,UserInfoActivity.class);
                intent.putExtra("user",mUserList.get(position));
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                mPresenter.search();
            }
        });

    }

    @Override
    protected String getTitles() {
        return "添加好友";
    }

    @Override
    public String getUser() {
        return mEditText.getText().toString();
    }

    @Override
    public void addItem(List<User> users) {
        mUserList.clear();
        mUserList.addAll(users);
        mAdapter.notifyDataSetChanged();
        missProgress();
    }

    @Override
    public void refresh() {

    }

    @Override
    public void wrong(String s) {
        showError(s);
        missProgress();
        if(mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }
    }
}
