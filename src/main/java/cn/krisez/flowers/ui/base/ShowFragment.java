package cn.krisez.flowers.ui.base;

import android.view.View;

import cn.krisez.flowers.R;
import cn.krisez.flowers.manager.FManager;
import cn.krisez.flowers.ui.justfrag.AboutAppFrag;
import cn.krisez.flowers.ui.justfrag.AboutRuleFrag;
import cn.krisez.flowers.ui.justfrag.AboutSendSFFrag;
import cn.krisez.flowers.ui.justfrag.AuthenticationFragment;
import cn.krisez.flowers.ui.justfrag.SettingFrag;

/**
 * 仅仅显示fragment
 */
public class ShowFragment extends BaseActivity {

    private String s;

    @Override
    protected View setView() {
        View view = View.inflate(ShowFragment.this, R.layout.activity_about, null);
        return view;
    }

    @Override
    protected void initSon() {
        switch (s) {
            case "rule":
                FManager.addFg(getSupportFragmentManager(), new AboutRuleFrag(), R.id.about_frame);
                break;
            case "app":
                FManager.addFg(getSupportFragmentManager(), new AboutAppFrag(), R.id.about_frame, "app");
                break;
            case "authentication":
                FManager.addFg(getSupportFragmentManager(), new AuthenticationFragment(), R.id.about_frame, "authentication");
                break;
            case "setting":
                FManager.addFg(getSupportFragmentManager(), new SettingFrag(), R.id.about_frame, "setting");
                break;
            case "ask":
                FManager.addFg(getSupportFragmentManager(), new AboutSendSFFrag(), R.id.about_frame, "ask");
                break;
        }
    }

    @Override
    protected String getTitles() {
        s = getIntent().getStringExtra("class");
        switch (s) {
            case "rule":
                return "用户须知";
            case "app":
                return "关于";
            case "authentication":
                return "认证";
            case "setting":
                return "设置";
            case "ask":
                return "查询";
        }
        return "";
    }
}
