package cn.krisez.flowers.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Krisez on 2018-03-18.
 */

public class MScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener;

    public MScrollView(Context context) {
        super(context);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(this.onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(t, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

}
