package cn.krisez.flowers.im.ui;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.bean.AddFriendMessage;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.widget.CircleImageView;

/**
 * Created by Krisez on 2018-03-24.
 */

public class UserInfoActivity extends BaseActivity{

    //查询的用户
    User user;
    //本地保存的用户信息，该客户端的用户
    BmobIMUserInfo info;

    @Override
    protected View setView() {
        return View.inflate(this,R.layout.activity_user_info,null);
    }
    @Override
    protected void initSon() {
        user = (User) getIntent().getSerializableExtra("user");
        CircleImageView circleImageView = findViewById(R.id.user_info_avatar);
        TextView username = findViewById(R.id.user_info_username);
        TextView nickname = findViewById(R.id.user_info_nickname);
        Glide.with(this).load(user.getHeadUrl()).into(circleImageView);
        username.setText(user.getUsername());
        nickname.setText(user.getNickname());
        findViewById(R.id.user_info_add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAddFriendMessage();
            }
        });

        //构造聊天方的用户信息:传入用户id、用户名和用户头像三个参数
        info = new BmobIMUserInfo(user.getObjectId(), user.getNickname(), user.getHeadUrl());
    }

    @Override
    protected String getTitles() {
        return "个人资料";
    }

    /**
     * 发送添加好友的请求
     */
    //TODO 好友管理：9.7、发送添加好友请求
    private void sendAddFriendMessage() {
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        //TODO 这里只是举个例子，其实可以不需要传发送者的信息过去
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getNickname());//发送者姓名
        map.put("avatar", currentUser.getHeadUrl());//发送者的头像
        map.put("uid", currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    showError("好友请求发送成功，等待验证");
                } else {//发送失败
                    showError("发送失败:" + e.getMessage());
                }
            }
        });
    }
}
