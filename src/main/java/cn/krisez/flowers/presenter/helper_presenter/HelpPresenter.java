package cn.krisez.flowers.presenter.helper_presenter;

import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.model.help_model.HelpModel;
import cn.krisez.flowers.model.help_model.IHelpModel;
import cn.krisez.flowers.presenter.Listener;
import cn.krisez.flowers.ui.helper_ui.IHelperView;

/**
 * Created by Krisez on 2018-03-18.
 */

public class HelpPresenter implements IHelpPresenter, Listener {
    private IHelperView mView;
    private IHelpModel mModel;

    public HelpPresenter(IHelperView view) {
        this.mView = view;
        this.mModel = new HelpModel();
    }

    @Override
    public void submit() {
        Apply apply = new Apply();
        apply.setAuthor(App.getUser());//申请者
        apply.setDescribe(mView.getDescribe());//详情
        apply.setType(mView.getType());//类型
        apply.setPerson(mView.getName());//真名
        apply.setDeal(false);//处理
        apply.setOver(false);//完结
        apply.setWhere("待处理");
        mModel.submit(apply, this);
    }

    @Override
    public void success() {
        mView.over();
    }

    @Override
    public void failed(String s) {
        mView.failed(s);
    }
}
