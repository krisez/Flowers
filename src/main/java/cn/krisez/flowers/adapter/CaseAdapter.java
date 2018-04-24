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
import cn.krisez.flowers.entity.DCase;

/**
 * Created by Krisez on 2017/7/27.
 */

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CViewHolder> {

    private List<DCase> mDates;
    private LayoutInflater layoutInflater;
    private Context mContex;

    public CaseAdapter(Context context, List<DCase> dates) {
        this.mDates = dates;
        this.mContex = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private OnItemClickListener onItemClickListener;//定义

    //获取方法类
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_case, parent, false);
        return new CaseAdapter.CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CViewHolder holder, int position) {
        DCase c = mDates.get(position);
        holder.tv1.setText(c.getCategory());
        holder.tv2.setText(c.getContent());
        holder.tv3.setText(c.getTitle());
        holder.tv4.setText("评论" + c.getCount());
        holder.tv5.setText(c.getTime());
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
    public void addData(int position,DCase dCase) {
        mDates.add(position,dCase);
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

    class CViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        public CViewHolder(View itemView) {
            super(itemView);

            tv1 = (TextView) itemView.findViewById(R.id.case_category);
            tv2 = (TextView) itemView.findViewById(R.id.case_content);
            tv3 = (TextView) itemView.findViewById(R.id.case_title);
            tv4 = (TextView) itemView.findViewById(R.id.case_dis_count);
            tv5 = (TextView) itemView.findViewById(R.id.case_time);
        }
    }

}
