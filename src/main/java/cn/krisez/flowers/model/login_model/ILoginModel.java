package cn.krisez.flowers.model.login_model;


import android.app.Activity;
import android.support.v4.app.Fragment;

import cn.krisez.flowers.presenter.login_presenter.LoginListener;
import cn.krisez.flowers.presenter.login_presenter.LoginPresenter;

/**
 * Created by Krisez on 2018-01-29.
 */

public interface ILoginModel {
    void getUser(String account, String mm, LoginListener listener);
    void register(String account, String mm,String phone,String sex, LoginListener listener);
    void qq(Fragment fragment, LoginListener listener);
}
