package cn.krisez.flowers.im.ui;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.Config;
import cn.krisez.flowers.im.adapter.NewFriendAdapter;
import cn.krisez.flowers.im.bean.AgreeAddFriendMessage;
import cn.krisez.flowers.im.db.NewFriend;
import cn.krisez.flowers.im.db.NewFriendManager;
import cn.krisez.flowers.im.model.UserModel;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.widget.DividerDecoration;

/**
 * Created by Krisez on 2018-03-24.
 */

public class NewFriendActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<NewFriend> mNewFriendList;
    private NewFriendAdapter mAdapter;


    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_new_firned, null);
    }

    @Override
    protected void initSon() {
        mNewFriendList = new ArrayList<>();
        mAdapter = new NewFriendAdapter(this, mNewFriendList);
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
                final NewFriend newFriend = mNewFriendList.get(position);
                new AlertDialog.Builder(NewFriendActivity.this)
                        .setTitle("添加"+ newFriend.getName()+ "为好友~")
                        .setPositiveButton("取消", null)
                        .setNegativeButton("同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO 1、添加好友
                                agreeAdd(newFriend, new SaveListener<Object>() {
                                    @Override
                                    public void done(Object o, BmobException e) {
                                        if (e == null) {
                                            showError("已添加好友！");
                                        } else {
                                            showError("添加好友失败");
                                        }
                                    }
                                });
                            }
                        }).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                NewFriendManager.getInstance(NewFriendActivity.this).deleteNewFriend(mNewFriendList.get(position));
                mAdapter.removeData(position);
            }
        });

        NewFriendManager.getInstance(this).updateBatchStatus();

    }

    @Override
    protected String getTitles() {
        return "新朋友";
    }

    @Override
    public void onResume() {
        super.onResume();
        query();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 查询本地会话
     */
    public void query() {
        mNewFriendList.clear();
        mNewFriendList.addAll(NewFriendManager.getInstance(this).getAllNewFriend());
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * TODO 好友管理：9.10、添加到好友表中再发送同意添加好友的消息
     *
     * @param add
     * @param listener
     */
    private void agreeAdd(final NewFriend add, final SaveListener<Object> listener) {
        User user = new User();
        user.setObjectId(add.getUid());
        UserModel.getInstance()
                .agreeAddFriend(user, new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            //TODO 2、发送同意添加好友的消息
                            sendAgreeAddFriendMessage(add, listener);
                        } else {
                            Logger.e(e.getMessage());
                            listener.done(null, e);
                        }
                    }
                });
    }

    /**
     * 发送同意添加好友的消息
     */
    //TODO 好友管理：9.8、发送同意添加好友
    private void sendAgreeAddFriendMessage(final NewFriend add, final SaveListener<Object> listener) {
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(), add.getName(), add.getAvatar());
        //TODO 会话：4.1、创建一个暂态会话入口，发送同意好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送同意好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        //而AgreeAddFriendMessage的isTransient设置为false，表明我希望在对方的会话数据库中保存该类型的消息
        AgreeAddFriendMessage msg = new AgreeAddFriendMessage();
        final User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("我通过了你的好友验证请求，我们可以开始 聊天了!");//这句话是直接存储到对方的消息表中的
        Map<String, Object> map = new HashMap<>();
        map.put("msg", currentUser.getUsername() + "同意添加你为好友");//显示在通知栏上面的内容
        map.put("uid", add.getUid());//发送者的uid-方便请求添加的发送方找到该条添加好友的请求
        map.put("time", add.getTime());//添加好友的请求时间
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    //TODO 3、修改本地的好友请求记录
                    NewFriendManager.getInstance(NewFriendActivity.this).updateNewFriend(add, Config.STATUS_VERIFIED);
                    listener.done(msg, e);
                } else {//发送失败
                    Logger.e(e.getMessage());
                    listener.done(msg, e);
                }
            }
        });
    }
}
