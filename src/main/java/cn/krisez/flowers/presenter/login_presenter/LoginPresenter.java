package cn.krisez.flowers.presenter.login_presenter;


import android.app.Activity;
import android.support.v4.app.Fragment;

import cn.krisez.flowers.App;
import cn.krisez.flowers.model.login_model.ILoginModel;
import cn.krisez.flowers.model.login_model.LoginModel;
import cn.krisez.flowers.ui.login_ui.ILoginView;

/**
 * Created by Krisez on 2018-01-29.
 */

public class LoginPresenter implements ILoginPresenter,LoginListener {
    private ILoginView mView;
    private ILoginModel mModel;

    public LoginPresenter(ILoginView view) {
        mView = view;
        mModel = new LoginModel();
    }

    @Override
    public void login() {
        mModel.getUser(mView.getAccount(),mView.getMM(),this);
    }

    @Override
    public void register(String phone, String sex) {
        mModel.register(mView.getAccount(),mView.getMM(),phone,sex,this);
    }

    @Override
    public void qq(Fragment fragment) {
        mModel.qq(fragment,this);
    }

    @Override
    public void success() {
        mView.dismissProgress();
        mView.right();
    }

    @Override
    public void failed(String s) {
        mView.dismissProgress();
        mView.showError(s);
    }
}
