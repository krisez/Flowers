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
import cn.krisez.flowers.entity.Send2S;

public class MyAskAdapter extends RecyclerView.Adapter<MyAskAdapter.MyHolder> {

    private List<Send2S> mDates;
    private LayoutInflater layoutInflater;
    private Context mContex;

    public MyAskAdapter(Context context,List<Send2S> list){
        this.mDates = list;
        this.mContex = context;
        layoutInflater = LayoutInflater.from(context);
    }

    private OnItemClickListener onItemClickListener;//定义

    //获取方法类
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_ask_query, parent, false);
        return new MyAskAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        Send2S send2S = mDates.get(position);
        holder.content.setText(send2S.getDescribe());
        holder.addr.setText(send2S.getRoom());
        holder.time.setText(send2S.getCreatedAt());
        holder.progress.setText(send2S.getProgress());

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

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView content;
        private TextView addr;
        private TextView time;
        private TextView progress;
        MyHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.my_ask_content);
            addr = itemView.findViewById(R.id.my_ask_addr);
            time = itemView.findViewById(R.id.my_ask_time);
            progress = itemView.findViewById(R.id.my_ask_gress);
        }
    }
}
