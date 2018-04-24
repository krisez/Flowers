package cn.krisez.flowers.im.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.bean.Conversation;
import cn.krisez.flowers.im.bean.Friend;
import cn.krisez.flowers.im.model.UserModel;
import cn.krisez.flowers.im.model.i.QueryUserListener;
import cn.krisez.flowers.utils.TimeUtil;
import cn.krisez.flowers.widget.CircleImageView;

/**
 * Created by Krisez on 2018-03-26.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder>{
    private List<Conversation> mDates;
    private LayoutInflater layoutInflater;
    private Context mContex;

    public ConversationAdapter(Context context, List<Conversation> dates) {
        this.mDates = dates;
        this.mContex = context;
        layoutInflater = LayoutInflater.from(context);
    }
    private OnItemClickListener onItemClickListener;//定义

    //获取方法类
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ConversationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_conversation, parent, false);
        return new ConversationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ConversationAdapter.MyViewHolder holder, int position) {
        final Conversation conversation = mDates.get(position);
        Glide.with(mContex).load(conversation.getAvatar()).into(holder.avatar);
        UserModel.getInstance().queryUserInfo(conversation.getcId(), new QueryUserListener() {
            @Override
            public void done(User s, BmobException e) {
                conversation.setNickName(s.getNickname());
                holder.nickname.setText(conversation.getNickname());
            }
        });
        holder.content.setText(conversation.getLastMessageContent());
        holder.time.setText(TimeUtil.getChatTime(false,conversation.getLastMessageTime()));
        long unread = conversation.getUnReadCount();
        if(unread>0){
            holder.count.setVisibility(View.VISIBLE);
            holder.count.setText(String.valueOf(unread));
        }else{
            holder.count.setVisibility(View.GONE);
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    int layoutPos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, layoutPos);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, layoutPos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDates.size();
    }

    /**
     * @param position
     * 添加
     */
    public void addData(int position,Conversation conversation) {
        mDates.add(position,conversation);
        notifyItemInserted(position);
    }

    /**
     * @param position
     * 删除
     */
    public void removeData(int position) {
        mDates.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView nickname;
        private TextView content;
        private TextView time;
        private TextView count;

        MyViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_recent_avatar);
            nickname = itemView.findViewById(R.id.tv_recent_name);
            content = itemView.findViewById(R.id.tv_recent_msg);
            time = itemView.findViewById(R.id.tv_recent_time);
            count = itemView.findViewById(R.id.tv_recent_unread);

        }
    }
}
