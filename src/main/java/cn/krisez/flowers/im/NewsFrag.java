package cn.krisez.flowers.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.im.adapter.ConversationAdapter;
import cn.krisez.flowers.im.bean.Conversation;
import cn.krisez.flowers.im.bean.PrivateConversation;
import cn.krisez.flowers.im.event.RefreshEvent;
import cn.krisez.flowers.im.ui.ChatActivity;
import cn.krisez.flowers.im.ui.SearchFriendActivity;
import cn.krisez.flowers.ui.base.BaseFragment;

/**
 * Created by Krisez on 2018-03-23.
 */

public class NewsFrag extends BaseFragment {
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    ConversationAdapter mAdapter;
    LinearLayoutManager layoutManager;
    List<Conversation> mList;

    @Override
    protected void initOperation(View view) {
        mList = new ArrayList<>();
        mAdapter = new ConversationAdapter(getActivity(), mList);
        rc_view.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        onRefresh();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mList.get(position).onClick(getContext());
                Log.d("NewsFrag", "onItemClick:" + mList.get(position).getNickname());
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //TODO 会话：4.5、删除会话，以下两种方式均可以删除会话
                //BmobIM.getInstance().deleteConversation(conversation.getConversationId());
                //BmobIM.getInstance().deleteConversation(mList.get(position).getcId());
                mList.get(position).onLongClick(getContext());
                mAdapter.removeData(position);
            }
        });

        view.findViewById(R.id.frag_news_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected View addView() {
        View view = View.inflate(getContext(), R.layout.frag_news, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onRefresh() {
        //TODO 会话：4.2、查询全部会话
       query();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
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
     * 查询本地会话
     */
    public void query() {
        mList.addAll(getConversations());
        mAdapter.notifyDataSetChanged();
        cancelRefresh();
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     *
     * @return
     */
    private List<Conversation> getConversations() {
        mList.clear();
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        //TODO 会话：4.2、查询全部会话
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size() > 0) {
            for (BmobIMConversation item : list) {
                switch (item.getConversationType()) {
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        //因为新增`新朋友`这种会话类型
        onRefresh();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        //重新刷新列表
        onRefresh();
    }

    /**
     * 注册消息接收事件
     *
     * @param event 1、与用户相关的由开发者自己维护，SDK内部只存储用户信息
     *              2、开发者获取到信息后，可调用SDK内部提供的方法更新会话
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        //重新获取本地消息并刷新列表
        onRefresh();
    }
}
