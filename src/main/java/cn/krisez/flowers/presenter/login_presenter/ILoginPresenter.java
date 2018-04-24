package cn.krisez.flowers.presenter.login_presenter;


import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by Krisez on 2018-01-29.
 */

public interface ILoginPresenter {
    void login();
    void register(String phone,String sex);
    void qq(Fragment fragment);
}
