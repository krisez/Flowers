package cn.krisez.flowers.ui.justfrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.bmob.newim.BmobIM;
import cn.krisez.flowers.R;
import cn.krisez.flowers.ui.base.BaseFragment;

/**
 * Created by Krisez on 2018-03-26.
 */

public class SettingFrag extends BaseFragment {

    @Override
    protected void initOperation(View view) {
        view.findViewById(R.id.set_clear_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
                BmobIM.getInstance().clearAllConversation();
                Toast.makeText(getActivity(), "清除完成", Toast.LENGTH_SHORT).show();
                cancelRefresh();
            }
        });
    }

    @Override
    protected View addView() {
        return View.inflate(getContext(),R.layout.fragment_setting,null);
    }

    @Override
    public void onRefresh() {
        cancelRefresh();
    }
}
