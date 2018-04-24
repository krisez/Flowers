package cn.krisez.flowers.model.help_model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.presenter.Listener;

/**
 * Created by Krisez on 2018-03-18.
 */

public class HelpModel implements IHelpModel {
    @Override
    public void submit(Apply apply, final Listener listener) {
        apply.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null)
                listener.success();
                else listener.failed(e.getMessage());
            }
        });
    }
}
