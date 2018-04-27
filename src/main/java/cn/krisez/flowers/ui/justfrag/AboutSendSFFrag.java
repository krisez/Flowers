package cn.krisez.flowers.ui.justfrag;

import android.view.View;
import android.widget.TextView;

import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.Send2S;
import cn.krisez.flowers.ui.base.BaseFragment;

public class AboutSendSFFrag extends BaseFragment {

    @Override
    protected void initOperation(View view) {
        Send2S s = (Send2S) getActivity().getIntent().getSerializableExtra("ask");
        TextView content = view.findViewById(R.id.my_ask_nr);
        TextView progress = view.findViewById(R.id.my_ask_jd);
        content.setText(s.toString());
        progress.setText(s.getProgress());
    }

    @Override
    protected View addView() {
        return View.inflate(getContext(), R.layout.frag_my_ask_query,null);
    }

    @Override
    public void onRefresh() {
        cancelRefresh();
    }
}
