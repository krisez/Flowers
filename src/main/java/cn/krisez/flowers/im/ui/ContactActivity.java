package cn.krisez.flowers.im.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.promeg.pinyinhelper.Pinyin;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.adapter.ContactsAdapter;
import cn.krisez.flowers.im.adapter.SearchAdapter;
import cn.krisez.flowers.im.bean.Friend;
import cn.krisez.flowers.im.db.NewFriend;
import cn.krisez.flowers.im.event.RefreshEvent;
import cn.krisez.flowers.im.model.UserModel;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.widget.DividerDecoration;

/**
 * Created by Krisez on 2018-03-25.
 */

public class ContactActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ContactsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Friend> mFriendList;

    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_contact, null);
    }

    @Override
    protected void initSon() {
        mFriendList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this,mFriendList);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mRecyclerView = findViewById(R.id.rc_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        query();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Friend friend = mFriendList.get(position);
                User user = friend.getFriendUser();
                BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getNickname(), user.getHeadUrl());
                //TODO 会话：4.1、创建一个常态会话入口，好友聊天
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                //conversationEntrance.setConversationTitle(user.getNickname());
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", conversationEntrance);
                Intent intent = new Intent(ContactActivity.this,ChatActivity.class);
                intent.putExtra("contacts",bundle);
                intent.putExtra("nickname",user.getNickname());
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(ContactActivity.this)
                        .setTitle("删除好友")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO 【好友管理】删除指定好友
                                UserModel.getInstance().deleteFriend(mFriendList.get(position),
                                        new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    showError("好友删除成功");
                                                    mAdapter.removeData(position);
                                                } else {
                                                    showError("好友删除失败：" + e.getErrorCode() + ",s =" + e.getMessage());
                                                }
                                            }
                                        });
                            }
                        })
                        .setPositiveButton("取消",null)
                        .show();
            }
        });

        findViewById(R.id.contacts_new_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, NewFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected String getTitles() {
        return "好友";
    }


    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        query();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        //重新刷新列表
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 查询本地会话
     */
    public void query() {
        //TODO 【好友管理】获取好友列表
        UserModel.getInstance().queryFriends(
                new FindListener<Friend>() {
                    @Override
                    public void done(List<Friend> list, BmobException e) {
                        if (e == null) {
                            /*List<Friend> friends = new ArrayList<>();
                            friends.clear();
                            //添加首字母
                            for (int i = 0; i < list.size(); i++) {
                                Friend friend = list.get(i);
                                String username = friend.getFriendUser().getUsername();
                                if (username != null) {
                                    String pinyin = Pinyin.toPinyin(username.charAt(0));
                                    friend.setPinyin(pinyin.substring(0, 1).toUpperCase());
                                    friends.add(friend);
                                }
                            }*/
                            mFriendList.clear();
                            mFriendList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            mFriendList.clear();
                            mAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        );
    }
}
