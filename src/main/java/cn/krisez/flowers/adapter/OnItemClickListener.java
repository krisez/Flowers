package cn.krisez.flowers.adapter;

import android.view.View;

/**
 * Created by Krisez on 2018-01-27.
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
