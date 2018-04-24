package cn.krisez.flowers.ui.login_ui;

/**
 * Created by Krisez on 2018-01-27.
 */

public interface ILoginView {
    String getAccount();
    String getMM();
    void showError(String s);
    void showProgress();
    void dismissProgress();
    void right();
}
