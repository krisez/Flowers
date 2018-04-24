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

import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.im.bean.Friend;
import cn.krisez.flowers.widget.CircleImageView;

/**
 * Created by Krisez on 2018-03-18.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private List<Friend> mDates;
    private LayoutInflater layoutInflater;
    private Context mContex;

    public ContactsAdapter(Context context, List<Friend> dates) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_search_friend, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Friend friend = mDates.get(position);
        Glide.with(mContex).load(friend.getFriendUser().getHeadUrl()).into(holder.avatar);
        holder.nickname.setText(friend.getFriendUser().getNickname());

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
    public void addData(int position,Friend friend) {
        mDates.add(position,friend);
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

        MyViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.item_search_avatar);
            nickname = itemView.findViewById(R.id.item_search_nickname);

        }
    }
}
