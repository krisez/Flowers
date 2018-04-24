package cn.krisez.flowers.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.entity.Comment;

/**
 * Created by Krisez on 2018-03-18.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<Comment> mDates;
    private LayoutInflater layoutInflater;
    private Context mContex;

    public CommentAdapter(Context context, List<Comment> dates) {
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
        return new MyViewHolder(layoutInflater.inflate(R.layout.item_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Comment comment = mDates.get(position);
        Glide.with(mContex).load(comment.getAuthor().getHeadUrl()).into(holder.mImageView);
        holder.mNickaname.setText(comment.getAuthor().getNickname());
        holder.mDate.setText(comment.getCreatedAt());
        holder.mComment.setText(comment.getContent());
        holder.mCount.setText("" + comment.getCount());
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
    public void addData(int position,Comment comment) {
        mDates.add(position,comment);
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
        private ImageView mImageView;
        private TextView mNickaname;
        private TextView mDate;
        private TextView mComment;
        private TextView mCount;
        MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_comment_head);
            mNickaname = itemView.findViewById(R.id.item_comment_nickname);
            mDate = itemView.findViewById(R.id.item_comment_date);
            mComment = itemView.findViewById(R.id.item_comment_comment);
            mCount = itemView.findViewById(R.id.item_comment_agree);
        }
    }
}
