package cn.krisez.flowers.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.Apply;

/**
 * Created by Krisez on 2018-03-18.
 */

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.MyViewHolder> {
    private List<Apply> mDates;
    private LayoutInflater layoutInflater;
    private Context mContex;

    public ApplyAdapter(Context context, List<Apply> dates) {
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
        View view = layoutInflater.inflate(R.layout.item_apply, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Apply apply = mDates.get(position);
        holder.type.setText(apply.getType());
        holder.date.setText(apply.getCreatedAt());
        //处理判断得到人数
        holder.remark.setText(apply.getWhere());
        holder.describe.setText(apply.getDescribe());
       /* if (holder.tv3.getText().equals("寻求中")){
            holder.tv3.setTextColor(Color.RED);
        }else if(holder.tv3.getText().equals("处理中")){
            holder.tv3.setTextColor(Color.YELLOW);
        }else holder.tv3.setTextColor(Color.GREEN);*/

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
    public void addData(int position,Apply apply) {
        mDates.add(position,apply);
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
        private TextView type;
        private TextView date;
        private TextView describe;
        private TextView remark;

        MyViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.apply_item_type);
            date = itemView.findViewById(R.id.apply_item_date);
            describe = itemView.findViewById(R.id.apply_item_describe);
            remark = itemView.findViewById(R.id.apply_item_remark);
        }
    }
}
